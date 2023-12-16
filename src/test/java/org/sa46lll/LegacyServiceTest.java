package org.sa46lll;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.sa46lll.service.OrderService;
import org.sa46lll.service.PaymentService;
import org.sa46lll.service.LegacyService;
import org.sa46lll.service.dto.OrderRequest;

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
}
