package org.sa46lll;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.sa46lll.domain.PaymentEvent;
import org.sa46lll.domain.PaymentEventHandler;
import org.sa46lll.utils.ILogger;
import org.sa46lll.utils.enums.LogLevel;

class PaymentEventHandlerTest {

    private PaymentEventHandler sut;
    private ILogger logger = mock(ILogger.class);

    @BeforeEach
    void setUp() {
        logger = mock(ILogger.class);
        sut = new PaymentEventHandler(logger);
    }

    @Test
    void 이벤트_핸들러가_호출되면_결제_로그가_출력된다() {
        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);

        sut.handlePaymentEvent(
                new PaymentEvent("existingOrderId", 1000.0, "1234-1234-1234-1234")
        );

        verify(logger).log(logCaptor.capture(), any(LogLevel.class));
        String log = logCaptor.getValue();
        assertTrue(log.contains("Pay processed"));
    }
}
