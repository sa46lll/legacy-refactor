package org.sa46lll.domain;

import java.time.LocalDateTime;
import org.sa46lll.service.dto.PaymentInfo;

public class Payment {

    private final String orderId;
    private final double amount;
    private final String creditCardNumber;
    private final LocalDateTime paymentDateTime;

    public Payment(String orderId, double amount, String creditCardNumber) {
        this.orderId = orderId;
        this.amount = amount;
        this.creditCardNumber = creditCardNumber;
        this.paymentDateTime = LocalDateTime.now();
    }

    public static Payment from(PaymentInfo paymentInfo) {
        return new Payment(
                paymentInfo.orderId(),
                paymentInfo.amount(),
                paymentInfo.creditCardNumber()
        );
    }
}
