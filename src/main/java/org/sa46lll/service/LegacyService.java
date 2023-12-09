package org.sa46lll.service;

import org.sa46lll.domain.Order;
import org.sa46lll.infrastructure.Logger;

public class LegacyService {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public LegacyService(OrderService externalOrderService, PaymentService paymentService) {
        this.orderService = externalOrderService;
        this.paymentService = paymentService;
    }

    public boolean processOrder(String orderId) {
        Logger logger = new Logger();

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
