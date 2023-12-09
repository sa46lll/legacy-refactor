package org.sa46lll;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void orderId_가_null이면_주문과_결제가_되지_않는다() {
        sut.processOrder(null);

        verify(orderService, times(0)).getOrder(anyString());
        verify(paymentService, times(0)).makePayment(anyDouble());
    }

    @Test
    void orderId_가_빈_문자열이면_주문과_결제가_되지_않는다() {
        sut.processOrder("");

        verify(orderService, times(0)).getOrder(anyString());
        verify(paymentService, times(0)).makePayment(anyDouble());
    }

    @Test
    void orderId_가_공백이면_주문과_결제가_되지_않는다() {
        sut.processOrder(" ");

        verify(orderService, times(0)).getOrder(anyString());
        verify(paymentService, times(0)).makePayment(anyDouble());
    }
}
