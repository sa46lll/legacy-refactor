package org.sa46lll.service.impl;

import org.sa46lll.domain.Payment;
import org.sa46lll.domain.PaymentEvent;
import org.sa46lll.exception.PaymentFailedException;
import org.sa46lll.gateway.PaymentGateway;
import org.sa46lll.infrastructure.PaymentRepository;
import org.sa46lll.service.PaymentService;
import org.sa46lll.service.dto.PaymentInfo;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultPaymentService implements PaymentService {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DefaultPaymentService(PaymentGateway paymentGateway,
                                 PaymentRepository paymentRepository,
                                 ApplicationEventPublisher applicationEventPublisher) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void processPayment(PaymentInfo paymentInfo) {
        if (!paymentGateway.pay(paymentInfo)) {
            throw new PaymentFailedException(paymentInfo.orderId());
        }

        paymentRepository.save(
                Payment.from(paymentInfo)
        );

        publishEvent(paymentInfo);
    }

    private void publishEvent(PaymentInfo paymentInfo) {
        applicationEventPublisher.publishEvent(
                new PaymentEvent(
                        paymentInfo.orderId(),
                        paymentInfo.amount(),
                        paymentInfo.creditCardNumber())
        );
    }
}
