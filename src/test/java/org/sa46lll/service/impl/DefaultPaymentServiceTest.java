package org.sa46lll.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sa46lll.domain.PaymentEvent;
import org.sa46lll.gateway.PaymentGateway;
import org.sa46lll.repository.PaymentRepository;
import org.sa46lll.service.dto.PaymentInfo;
import org.springframework.context.ApplicationEventPublisher;


class DefaultPaymentServiceTest {

    private DefaultPaymentService sut;
    private PaymentGateway paymentGateway;
    private PaymentRepository paymentRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        paymentRepository = mock(PaymentRepository.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        sut = new DefaultPaymentService(paymentGateway, paymentRepository, applicationEventPublisher);
    }

    @Test
    void 결제가_성공하면_결제정보가_저장된다() {
        when(paymentGateway.pay(any())).thenReturn(true);

        sut.processPayment(
                new PaymentInfo("existingOrderId", 1000.0, "1234-1234-1234-1234")
        );

        verify(paymentRepository).save(any());
        verify(applicationEventPublisher, times(1)).publishEvent(any(PaymentEvent.class));
    }

    @Test
    void 결제가_실패하면_예외가_발생한다() {
        when(paymentGateway.pay(any())).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> sut.processPayment(any(PaymentInfo.class))
        );
        verify(paymentRepository, never()).save(any());
        verify(applicationEventPublisher, never()).publishEvent(any(PaymentEvent.class));
    }
}
