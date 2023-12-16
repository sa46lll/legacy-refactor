package org.sa46lll.domain;

import org.sa46lll.utils.ILogger;
import org.sa46lll.utils.enums.LogLevel;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    private final ILogger logger;

    public PaymentEventHandler(ILogger logger) {
        this.logger = logger;
    }

    @EventListener
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        logger.log("Pay processed: " + paymentEvent.orderId(), LogLevel.INFO);
    }
}
