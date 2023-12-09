package org.sa46lll.exception;

public class PaymentFailedException extends RuntimeException {

    public PaymentFailedException(String orderId) {
        super("Payment failed for order: " + orderId);
    }
}
