package org.sa46lll.infrastructure;

import org.sa46lll.domain.Payment;

public interface PaymentRepository {

    void save(Payment payment);
}
