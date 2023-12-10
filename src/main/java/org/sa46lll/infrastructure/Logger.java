package org.sa46lll.infrastructure;

public class Logger {

    private static Logger instance = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        // 로깅 로직
        System.out.println("Log: " + message);
    }
}
