package org.sa46lll.infrastructure.impl;

import org.sa46lll.infrastructure.Logger;

public class DefaultLogger implements Logger {

    public void log(String message) {
        // 로깅 로직
        System.out.println("Log: " + message);
    }
}
