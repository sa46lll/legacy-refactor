package org.sa46lll.service;

import org.sa46lll.domain.Order;
import org.sa46lll.service.dto.OrderDto;

public interface OrderService {

    Order getOrder(OrderDto orderDto);
}
