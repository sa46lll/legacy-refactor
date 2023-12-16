package org.sa46lll.service;

import org.sa46lll.domain.Order;
import org.sa46lll.infrastructure.Logger;
import org.sa46lll.infrastructure.enums.LogLevel;
import org.sa46lll.service.dto.OrderRequest;

public class LegacyService {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public LegacyService(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public void processOrder(OrderRequest orderRequest) {
        String orderId = orderRequest.orderId();
        Logger logger = Logger.getInstance();

        Order order = orderService.getOrder(orderId);
        paymentService.makePayment(order.getTotal());

        logger.log("Order processed: " + orderId, LogLevel.INFO);
    }
}
