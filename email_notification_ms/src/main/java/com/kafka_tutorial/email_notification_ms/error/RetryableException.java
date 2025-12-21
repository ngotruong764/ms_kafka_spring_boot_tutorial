package com.kafka_tutorial.email_notification_ms.error;

public class RetryableException extends RuntimeException {
    public RetryableException() {
        super();
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }
}
