package com.sellerbuyer.sellerbuyer.service.implementation;

import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import com.sellerbuyer.sellerbuyer.entity.Product;
import com.sellerbuyer.sellerbuyer.entity.PurchaseProductInformation;
import com.sellerbuyer.sellerbuyer.entity.PurchaseTransaction;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.enumClasses.PaymentMethod;
import com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus;
import com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction;
import com.sellerbuyer.sellerbuyer.exception.CustomException;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseProductInformationDto;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseTransactionDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.PurchaseRequestDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.RefundRequestDto;
import com.sellerbuyer.sellerbuyer.payloads.responseDto.RefundResponseDto;
import com.sellerbuyer.sellerbuyer.repository.ProductRepository;
import com.sellerbuyer.sellerbuyer.repository.PurchaseTransactionRepository;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.service.EmailService;
import com.sellerbuyer.sellerbuyer.service.PurchaseTransactionService;
import com.sellerbuyer.sellerbuyer.util.DtoConverter;
import com.sellerbuyer.sellerbuyer.util.RazorpayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus.COMPLETED;
import static com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction.PURCHASE;
import static com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction.REFUND;

@Service
public class PurchaseTransactionServiceImplementation implements PurchaseTransactionService {

    private final PurchaseTransactionRepository
            purchaseTransactionRepository;

    private final DtoConverter dtoConverter;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public PurchaseTransactionServiceImplementation(
            PurchaseTransactionRepository purchaseTransactionRepository,
            DtoConverter dtoConverter,
            ProductRepository productRepository,
            UserRepository userRepository,
            EmailService emailService
    ) {
        this.purchaseTransactionRepository =
                purchaseTransactionRepository;
        this.dtoConverter = dtoConverter;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public PurchaseTransactionDto createPurchase(PurchaseTransactionDto purchaseTransactionDto) throws RazorpayException {

        PurchaseTransaction purchaseTransaction =
                this.dtoConverter.convertToEntity(purchaseTransactionDto, PurchaseTransaction.class);

        if ((!purchaseTransaction.getTransactionStatus().equals(COMPLETED))
                && purchaseTransaction.getOriginalTransactionId() == null) {
            purchaseTransaction.setPurchaseProductInformationList(new ArrayList<>());

            for (PurchaseProductInformationDto item : purchaseTransactionDto.getPurchaseProductInformationList()) {
                Product product =
                        this.productRepository.findById(item.getProduct().getProductId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Products not found"));

                long remainingStock =
                        product.getStockCount() - item.getQuantity();
                if (remainingStock < 0) {
                    throw new CustomException(HttpStatus.INSUFFICIENT_STORAGE, "Product Out Of Stock - " + product.getProductId());
                }
                product.setStockCount(remainingStock);

                this.productRepository.save(product);

                purchaseTransaction.addProductInformation(this.dtoConverter.convertToEntity(item, PurchaseProductInformation.class));
            }

            purchaseTransaction.setOrderId(RazorpayUtil.createOrder(purchaseTransaction.getAmount()).get("id"));
            purchaseTransaction.setPurchasedById(getCurrentBuyer());
            return this.dtoConverter.convertToDto(this.purchaseTransactionRepository.save(purchaseTransaction), PurchaseTransactionDto.class);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "Please Provide transactionId as well if " +
                            "transaction is completed");
        }
    }

    @Override
    public PurchaseTransactionDto updateTransaction(PurchaseRequestDto purchaseRequestDto) throws RazorpayException {

        //get the purchase info from purchase transaction table
        PurchaseTransaction purchaseTransaction =
                this.purchaseTransactionRepository.findByOrderId(
                                purchaseRequestDto.getOrderId())
                        .orElseThrow(() -> new CustomException(
                                HttpStatus.NOT_FOUND, "No Order " +
                                "found With this order id"
                        ));

        //verify payment and update purchaseTransaction
        PurchaseTransaction updatedPurchaseTransaction =
                verifyPaymentAndUpdateDB(purchaseTransaction,
                        purchaseRequestDto);

        //update the quantity of each product
        updateTheStockOfEachProduct(updatedPurchaseTransaction.getPurchaseProductInformationList());

        PurchaseTransactionDto purchaseTransactionDto =
                this.dtoConverter.convertToDto(this.purchaseTransactionRepository.save(updatedPurchaseTransaction), PurchaseTransactionDto.class);

        this.emailService.sendSimpleMessage(
                purchaseTransaction.getPurchasedById().getEmail(),
                "Confirm Purchase", "Your Purchase has been " +
                        "confirmed. \n OrderId: " + purchaseTransactionDto.getOrderId() + "\n Payment " +
                        "Id: " + purchaseTransactionDto.getOriginalTransactionId());
        return purchaseTransactionDto;
    }

    private void updateTheStockOfEachProduct(List<PurchaseProductInformation> purchaseProductInformationList) {
        for (PurchaseProductInformation item : purchaseProductInformationList) {

            Product product =
                    this.productRepository.findById(item.getProduct().getProductId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Products not found"));

            long remainingStock =
                    product.getStockCount() - item.getQuantity();
            if (remainingStock < 0) {
                throw new CustomException(HttpStatus.INSUFFICIENT_STORAGE, "Product Out Of Stock" + product.getProductId());
            }
            product.setStockCount(remainingStock);

            this.productRepository.save(product);

        }
    }

    private PurchaseTransaction verifyPaymentAndUpdateDB(PurchaseTransaction purchaseTransaction, PurchaseRequestDto purchaseRequestDto) throws RazorpayException {
        Payment paymentInformation =
                RazorpayUtil.getPaymentInformation(purchaseRequestDto.getTransactionId());

        if (!paymentInformation.has("error")) {
            if (paymentInformation.get("status").toString().equals("captured")) {
                if ((int) paymentInformation.get("amount") == purchaseTransaction.getAmount() * 100) {
                    purchaseTransaction.setTransactionStatus(COMPLETED);
                    purchaseTransaction.setOriginalTransactionId(purchaseRequestDto.getTransactionId());
                    purchaseTransaction.setPaymentMethod(PaymentMethod.valueOf(paymentInformation.get("method").toString().toUpperCase()));
                }
            }
        } else {
            purchaseTransaction.setTransactionStatus(TransactionStatus.FAILURE);

            purchaseTransaction.setOriginalTransactionId(purchaseRequestDto.getTransactionId());
        }
        return purchaseTransaction;
    }

    public Page<PurchaseTransactionDto> findPurchases(Pageable pageable, TransactionStatus status, TypeOfTransaction type) {
        return this.purchaseTransactionRepository.findByPurchasedByIdAndTransactionStatusAndTransactionType(pageable, getCurrentBuyer(), status, type).map((purchase) -> this.dtoConverter.convertToEntity(purchase, PurchaseTransactionDto.class));
    }

    private User getCurrentBuyer() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userRepository.findByUserName(userName);
    }

    public RefundResponseDto refundThePurchase(RefundRequestDto refundRequestDto) throws RazorpayException {
        PurchaseTransaction purchaseTransaction =
                this.purchaseTransactionRepository.findByPurchasedByIdAndTransactionStatusAndTransactionTypeAndOrderId(
                        getCurrentBuyer(), COMPLETED, PURCHASE,
                        refundRequestDto.getOrderId()).orElseThrow(
                        () -> new CustomException(HttpStatus.NOT_FOUND,
                                "No Purchase Found with this " +
                                        "orderId and paymentId")
                );

        if (Objects.equals(purchaseTransaction.getOriginalTransactionId(), refundRequestDto.getOriginalTransactionId())) {
            Refund refund = refundAmountAndUpdateDb(
                    purchaseTransaction);

            return new RefundResponseDto(refund.get("payment_id"),
                    refund.get("id"),
                    purchaseTransaction.getAmount(),
                    refund.get("status"));

        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "Incorrect Transaction Id");
        }
    }

    private Refund refundAmountAndUpdateDb(PurchaseTransaction purchaseTransaction) throws RazorpayException {
        Refund refund = RazorpayUtil.refundTheAmount(
                purchaseTransaction.getOriginalTransactionId(),
                purchaseTransaction.getAmount()
        );

        purchaseTransaction.setTransactionType(REFUND);
        purchaseTransaction.setRefundId(refund.get("id"));
        purchaseTransaction.setTransactionStatus(COMPLETED);
        this.purchaseTransactionRepository.save(purchaseTransaction);

        return refund;

    }


}
