package com.sellerbuyer.sellerbuyer.service.implementation;

import com.sellerbuyer.sellerbuyer.entity.PurchaseProductInformation;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.repository.PurchaseProductInformationRepository;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.service.PurchaseProductInformationService;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseProductInformationDto;
import com.sellerbuyer.sellerbuyer.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseProductInformationServiceImplementation
        implements PurchaseProductInformationService
{
    private final PurchaseProductInformationRepository purchaseProductInformationRepository;
    private final DtoConverter dtoConverter;
    private final UserRepository userRepository;

    @Autowired
    public PurchaseProductInformationServiceImplementation(PurchaseProductInformationRepository purchaseProductInformationRepository,
                                                           DtoConverter dtoConverter,UserRepository userRepository) {
        this.purchaseProductInformationRepository = purchaseProductInformationRepository;
        this.dtoConverter = dtoConverter;
        this.userRepository = userRepository;
    }

    @Override
    public Page<PurchaseProductInformationDto> getPurchasesBySeller(Pageable pageable) {
        Page<PurchaseProductInformation> page = purchaseProductInformationRepository
                .findPurchasesBySellerId(getCurrentSeller().getUuid(),pageable);
        return page.map((purchaseProductInformation) -> this.dtoConverter
                .convertToDto(purchaseProductInformation,PurchaseProductInformationDto.class));
    }

    private User getCurrentSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userRepository.findByUserName(userName);
    }
}
