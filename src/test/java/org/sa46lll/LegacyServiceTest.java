package org.sa46lll;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sa46lll.exception.PaymentFailedException;
import org.sa46lll.service.OrderService;
import org.sa46lll.service.PaymentService;
import org.sa46lll.service.LegacyService;
import org.sa46lll.service.dto.OrderRequest;
import org.sa46lll.service.dto.PaymentInfo;

class LegacyServiceTest {

    private LegacyService sut;
    private OrderService orderService;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        paymentService = mock(PaymentService.class);
        sut = new LegacyService(orderService, paymentService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void orderId가_비어있으면_주문이_되지_않는다(String orderId) {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderRequest(orderId, 1000.0, "1234-1234-1234-1234")
        );
    }

    @Test
    @Disabled
    void 결제가_실패하면_주문이_되지_않는다() {
        String orderId = "existingOrderId";
        OrderRequest orderRequest = new OrderRequest(orderId, 1000.0, "1234-1234-1234-1234");
//        when(orderService.getOrder(orderId)).thenReturn(Optional.of(new Order(orderId, 1000.0)));
//        when(paymentService.processPayment(anyDouble())).thenReturn(false);

        assertThrows(PaymentFailedException.class,
                () -> sut.processOrder(orderRequest)
        );
    }

    @Test
    @Disabled
    void 결제가_완료되면_주문이_완료된다() {
        String orderId = "existingOrderId";
//        when(orderService.getOrder(orderId)).thenReturn(Optional.of(new Order(orderId, 1000.0)));
//        when(paymentService.processPayment(anyDouble())).thenReturn(true);

        sut.processOrder((new OrderRequest(orderId, 1000.0, "1234-1234-1234-1234")));

        verify(orderService, times(1)).getOrder(anyString());
        verify(paymentService, times(1)).processPayment(any(PaymentInfo.class));
    }
}
