package org.sa46lll.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sa46lll.domain.Order;
import org.sa46lll.exception.OrderNotFoundException;
import org.sa46lll.repository.OrderRepository;
import org.sa46lll.service.OrderService;

class ExternalOrderServiceTest {

    private OrderService sut;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        sut = new ExternalOrderService(orderRepository);
    }

    @Test
    void 존재하지_않는_주문을_요청하면_실패한다() {
        String orderId = "nonExistingOrderId";

        when(orderRepository.save(orderId, 1000.0)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> sut.getOrder(orderId)
        );
    }

    @Test
    void 존재하는_주문을_요청하면_성공한다() {
        String orderId = "existingOrderId";
        when(orderRepository.save(orderId, 1000.0))
                .thenReturn(Optional.of(new Order(orderId, 1000.0)));

        sut.getOrder(orderId);

        verify(orderRepository).save(orderId, 1000.0);
    }
}
