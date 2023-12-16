package org.sa46lll.service;

import org.sa46lll.infrastructure.Logger;
import org.sa46lll.infrastructure.enums.LogLevel;
import org.sa46lll.service.dto.OrderDto;
import org.sa46lll.service.dto.OrderRequest;
import org.sa46lll.service.dto.PaymentInfo;

public class LegacyService {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public LegacyService(OrderService orderService,
                         PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public void processOrder(OrderRequest orderRequest) {
        String orderId = orderRequest.orderId();
        Logger logger = Logger.getInstance();

        orderService.order(
                new OrderDto(
                        orderRequest.orderId(),
                        orderRequest.amount())
        );

        paymentService.processPayment(
                PaymentInfo.from(orderRequest)
        );

        logger.log("Order processed: " + orderId, LogLevel.INFO);
    }
}
