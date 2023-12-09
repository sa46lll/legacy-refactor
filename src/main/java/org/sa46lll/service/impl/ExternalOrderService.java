package org.sa46lll.service.impl;

import org.sa46lll.domain.Order;
import org.sa46lll.service.OrderService;

public class ExternalOrderService implements OrderService {

    @Override
    public Order getOrder(String orderId) {
        // 이게 디비라면..
        // 외부 서비스에서 주문 정보를 가져오는 복잡한 로직
        return new Order(orderId, 100.0);
    }
}
