package org.sa46lll.service;

import org.sa46lll.domain.Order;
import org.sa46lll.infrastructure.Logger;

public class LegacyService {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final Logger logger;

    public LegacyService(OrderService externalOrderService, PaymentService paymentService, Logger logger) {
        this.orderService = externalOrderService;
        this.paymentService = paymentService;
        this.logger = logger;
    }

    public boolean processOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            logger.log("Order ID is invalid.");
            return false;
        }

        Order order = orderService.getOrder(orderId);
        if (order == null) {
            logger.log("Order not found for ID: " + orderId);
            return false;
        }

        if (!paymentService.makePayment(order.getTotal())) {
            logger.log("Payment failed for order: " + orderId);
            return false;
        }

        // 다른 복잡한 로직들...
        logger.log("Order processed: " + orderId);
        return true;
    }
}
