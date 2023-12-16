package org.sa46lll.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sa46lll.domain.Order;
import org.sa46lll.domain.OrderEvent;
import org.sa46lll.exception.OrderNotFoundException;
import org.sa46lll.repository.OrderRepository;
import org.sa46lll.service.OrderService;
import org.sa46lll.service.dto.OrderDto;
import org.springframework.context.ApplicationEventPublisher;

class ExternalOrderServiceTest {

    private OrderService sut;
    private OrderRepository orderRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        sut = new ExternalOrderService(orderRepository, applicationEventPublisher);
    }

    @Test
    void 존재하지_않는_주문을_요청하면_실패한다() {
        String orderId = "nonExistingOrderId";

        when(orderRepository.save(any(Order.class)))
                .thenReturn(
                        Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> sut.getOrder(
                        new OrderDto(orderId, 1000.0)));

        verify(applicationEventPublisher, never()).publishEvent(any(OrderEvent.class));
    }

    @Test
    void 존재하는_주문을_요청하면_성공한다() {
        String orderId = "existingOrderId";
        when(orderRepository.save(any(Order.class)))
                .thenReturn(
                        Optional.of(new Order(orderId, 1000.0)));

        sut.getOrder(new OrderDto(orderId, 1000.0));

        verify(orderRepository, times(1)).save(any());
        verify(applicationEventPublisher, times(1)).publishEvent(any(OrderEvent.class));
    }
}
