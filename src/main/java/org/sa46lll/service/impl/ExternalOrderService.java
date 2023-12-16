package org.sa46lll.service.impl;

import org.sa46lll.domain.Order;
import org.sa46lll.exception.OrderNotFoundException;
import org.sa46lll.repository.OrderRepository;
import org.sa46lll.service.OrderService;

public class ExternalOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public ExternalOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(String orderId) {
        return orderRepository.save(orderId, 1000.0)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
