package org.sa46lll.service.impl;

import org.sa46lll.service.PaymentService;

public class DefaultPaymentService implements PaymentService {

    @Override
    public boolean makePayment(double amount) {
        // 결제를 처리하는 복잡한 로직
        return amount > 0;
    }
}
