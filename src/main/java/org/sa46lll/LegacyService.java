package org.sa46lll;

public class LegacyService {

    public boolean processOrder(String orderId) {
        ExternalOrderService orderService = new ExternalOrderService();
        PaymentService paymentService = new PaymentService();
        Logger logger = new Logger();

        if (orderId == null || orderId.isEmpty()) {
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
