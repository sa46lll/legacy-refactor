package org.sa46lll.service.impl;

import org.sa46lll.domain.Payment;
import org.sa46lll.exception.PaymentFailedException;
import org.sa46lll.gateway.PaymentGateway;
import org.sa46lll.repository.PaymentRepository;
import org.sa46lll.service.PaymentService;
import org.sa46lll.service.dto.PaymentInfo;

public class DefaultPaymentService implements PaymentService {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;

    public DefaultPaymentService(PaymentGateway paymentGateway, PaymentRepository paymentRepository) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void processPayment(PaymentInfo paymentInfo) {
        if (!paymentGateway.pay(paymentInfo)) {
            throw new PaymentFailedException(paymentInfo.orderId());
        }

        paymentRepository.save(
                Payment.from(paymentInfo)
        );
    }
}
