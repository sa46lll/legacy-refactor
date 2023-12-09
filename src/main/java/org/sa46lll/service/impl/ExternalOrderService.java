package org.sa46lll.service.impl;

import java.util.Optional;
import org.sa46lll.domain.Order;
import org.sa46lll.service.OrderService;

public class ExternalOrderService implements OrderService {

    @Override
    public Optional<Order> getOrder(String orderId) {
        // 이게 디비라면..
        // 외부 서비스에서 주문 정보를 가져오는 복잡한 로직
        Order order = new Order(orderId, 1000.0);
        return Optional.of(order);
    }
}
