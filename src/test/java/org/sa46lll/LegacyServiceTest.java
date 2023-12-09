package org.sa46lll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sa46lll.domain.Order;
import org.sa46lll.infrastructure.Logger;
import org.sa46lll.service.OrderService;
import org.sa46lll.service.PaymentService;
import org.sa46lll.service.LegacyService;
import org.sa46lll.service.dto.OrderRequest;

class LegacyServiceTest {

    private LegacyService sut;
    private OrderService orderService;
    private PaymentService paymentService;
    private Logger logger;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        paymentService = mock(PaymentService.class);
        logger = mock(Logger.class);
        sut = new LegacyService(orderService, paymentService, logger);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " "})
    void orderId가_비어있으면_주문이_되지_않는다(String text) {
        assertThrows(IllegalArgumentException.class, () -> new OrderRequest(text));
    }

    @Test
    void 존재하지_않는_주문을_요청하면_결제가_되지_않는다() {
        String orderId = "nonExistingOrderId";
        when(orderService.getOrder(orderId)).thenReturn(null);

        boolean result = sut.processOrder((new OrderRequest(orderId)));

        assertFalse(result);
        verify(orderService, times(1)).getOrder(orderId);
        verify(paymentService, never()).makePayment(anyDouble());
    }

    @Test
    void 결제가_실패하면_주문이_되지_않는다() {
        String orderId = "existingOrderId";
        when(orderService.getOrder(orderId)).thenReturn(new Order(orderId, 1000.0));
        when(paymentService.makePayment(anyDouble())).thenReturn(false);

        boolean result = sut.processOrder((new OrderRequest(orderId)));

        assertFalse(result);
        verify(orderService, times(1)).getOrder(anyString());
        verify(paymentService, times(1)).makePayment(anyDouble());
    }

    @Test
    void 결제가_완료되면_주문이_완료된다() {
        String orderId = "existingOrderId";
        when(orderService.getOrder(orderId)).thenReturn(new Order(orderId, 1000.0));
        when(paymentService.makePayment(anyDouble())).thenReturn(true);

        boolean result = sut.processOrder((new OrderRequest(orderId)));

        assertTrue(result);
        verify(orderService, times(1)).getOrder(anyString());
        verify(paymentService, times(1)).makePayment(anyDouble());
    }
}
