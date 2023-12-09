package org.sa46lll.service;

import org.sa46lll.domain.Order;
import org.sa46lll.exception.OrderNotFoundException;
import org.sa46lll.exception.PaymentFailedException;
import org.sa46lll.infrastructure.Logger;
import org.sa46lll.service.dto.OrderRequest;

public class LegacyService {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final Logger logger;

    public LegacyService(OrderService orderService, PaymentService paymentService, Logger logger) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.logger = logger;
    }

    public boolean processOrder(OrderRequest orderRequest) {
        String orderId = orderRequest.orderId();

        Order order = orderService.getOrder(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!paymentService.makePayment(order.getTotal())) {
            throw new PaymentFailedException(orderId);
        }

        // 다른 복잡한 로직들...
        logger.log("Order processed: " + orderId);
        return true;
    }
}
