package com.securepay.SecurePay.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ExceptionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date timestamp;
    private String exceptionType;
    @Column(length = 1000)
    private String message;

    private String stackTrace;

    public ExceptionLog() {
    }

    public ExceptionLog(Date timestamp, String exceptionType, String message, String stackTrace) {
        this.timestamp = timestamp;
        this.exceptionType = exceptionType;
        this.message = message;
        this.stackTrace = stackTrace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
