package org.sa46lll.infrastructure;

import java.util.Optional;
import org.sa46lll.domain.Order;

public interface OrderRepository {

    Optional<Order> save(Order order);
}
