package org.sa46lll.service.dto;

public record PaymentInfo(
        String orderId,
        double amount,
        String creditCardNumber
) {

    public static PaymentInfo from(OrderRequest orderRequest) {
        return new PaymentInfo(
                orderRequest.orderId(),
                orderRequest.amount(),
                orderRequest.creditCardNumber()
        );
    }
}
