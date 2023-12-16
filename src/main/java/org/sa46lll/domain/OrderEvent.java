package org.sa46lll.domain;

public record OrderEvent(
        String orderId,
        double amount
) {

}
