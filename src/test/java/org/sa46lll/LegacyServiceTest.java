package org.sa46lll;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sa46lll.service.OrderService;
import org.sa46lll.service.PaymentService;
import org.sa46lll.service.LegacyService;

class LegacyServiceTest {

    private OrderService orderService;
    private PaymentService paymentService;
    private LegacyService sut;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        paymentService = mock(PaymentService.class);
        sut = new LegacyService(orderService, paymentService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void orderId가_비어있으면_주문이_되지_않는다(String text) {
        sut.processOrder(text);

        verify(orderService, times(0)).getOrder(anyString());
        verify(paymentService, times(0)).makePayment(anyDouble());
    }
}
