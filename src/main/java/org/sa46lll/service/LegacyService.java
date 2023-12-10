package org.sa46lll.service;

import org.sa46lll.domain.Order;
import org.sa46lll.exception.OrderNotFoundException;
import org.sa46lll.exception.PaymentFailedException;
import org.sa46lll.infrastructure.Logger;
import org.sa46lll.service.dto.OrderRequest;

public class LegacyService {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public LegacyService(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public boolean processOrder(OrderRequest orderRequest) {
        String orderId = orderRequest.orderId();
        Logger logger = Logger.getInstance();

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
