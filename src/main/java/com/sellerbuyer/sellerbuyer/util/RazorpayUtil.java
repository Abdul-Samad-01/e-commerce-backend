package com.sellerbuyer.sellerbuyer.util;

import com.razorpay.*;
import org.json.JSONObject;

public class RazorpayUtil {

    private static final RazorpayClient client;

    static {
        try {
            client = new RazorpayClient("rzp_test_ZC6ooPiUUScGpO",
                    "0QcDrYiNaxN5eL9ZPro61W3I");
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    public static Order createOrder(Double amount) throws RazorpayException {

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");

        return client.Orders.create(orderRequest);
    }

    public static Payment getPaymentInformation(String paymentId)
            throws RazorpayException {
        return client.Payments.fetch(paymentId);
    }

    public static Refund refundTheAmount(String paymentId,
                                         double amount
    ) throws RazorpayException {

        JSONObject refundRequest = new JSONObject();

        refundRequest.put("amount", amount * 100);

        refundRequest.put("speed", "optimum");

        return client.Payments.refund(paymentId, refundRequest);
    }

}
