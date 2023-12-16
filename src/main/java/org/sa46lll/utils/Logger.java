package org.sa46lll.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.sa46lll.utils.enums.LogLevel;

public class Logger implements ILogger {

    private static Logger instance = new Logger();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message, LogLevel logLevel) {
        // 로깅 로직
        String formattedMessage = getFormattedMessage(message, logLevel);
        System.out.println(formattedMessage);
    }

    private String getFormattedMessage(String message, LogLevel logLevel) {
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("[%s] %s - %s", timestamp, logLevel.toString(), message);
    }
}
