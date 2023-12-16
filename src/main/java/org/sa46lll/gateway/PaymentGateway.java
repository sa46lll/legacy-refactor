package org.sa46lll.gateway;

import org.sa46lll.service.dto.PaymentInfo;

public interface PaymentGateway {

    boolean pay(PaymentInfo amount);
}
