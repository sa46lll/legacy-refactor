package org.sa46lll.service;

import java.util.Optional;
import org.sa46lll.domain.Order;

public interface OrderService {

    Optional<Order> getOrder(String orderId);
}
