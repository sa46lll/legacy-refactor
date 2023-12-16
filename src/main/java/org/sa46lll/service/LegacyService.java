package org.sa46lll.service;

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
        orderService.order(
                new OrderDto(
                        orderRequest.orderId(),
                        orderRequest.amount())
        );

        paymentService.processPayment(
                PaymentInfo.from(orderRequest)
        );
    }
}
