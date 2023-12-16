package org.sa46lll.service.impl;

import org.sa46lll.domain.Order;
import org.sa46lll.domain.OrderEvent;
import org.sa46lll.exception.OrderNotFoundException;
import org.sa46lll.repository.OrderRepository;
import org.sa46lll.service.OrderService;
import org.sa46lll.service.dto.OrderDto;
import org.springframework.context.ApplicationEventPublisher;

public class ExternalOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ExternalOrderService(OrderRepository orderRepository,
                                ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void order(OrderDto orderDto) {
        Order order = orderRepository.save(
                new Order(
                        orderDto.orderId(),
                        orderDto.amount())
        ).orElseThrow(() -> new OrderNotFoundException(orderDto.orderId()));

        publishEvent(order);
    }

    private void publishEvent(Order order) {
        applicationEventPublisher.publishEvent(
                new OrderEvent(order.getId(), order.getTotal())
        );
    }
}
