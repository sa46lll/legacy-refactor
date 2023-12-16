package org.sa46lll.repository;

import java.util.Optional;
import org.sa46lll.domain.Order;

public interface OrderRepository {

    Optional<Order> save(String orderId, double price);
}
