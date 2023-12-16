package org.sa46lll.domain;

public record PaymentEvent(
        String orderId,
        double amount,
        String creditCardNumber
) {

}
