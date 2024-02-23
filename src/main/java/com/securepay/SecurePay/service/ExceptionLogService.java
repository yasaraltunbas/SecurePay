package com.securepay.SecurePay.service;

import com.securepay.SecurePay.entity.ExceptionLog;
import com.securepay.SecurePay.repository.ExceptionLogRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExceptionLogService {

    private final ExceptionLogRepository exceptionLogRepository;

    @Autowired
    public ExceptionLogService(ExceptionLogRepository exceptionLogRepository) {
        this.exceptionLogRepository = exceptionLogRepository;
    }

    public void logException(Exception exception) {
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setTimestamp(new Date());
        exceptionLog.setExceptionType(exception.getClass().getName());
        exceptionLog.setMessage(exception.getMessage());
        String stackTrace = ExceptionUtils.getStackTrace(exception);
        if (stackTrace.length() > 250) {
            stackTrace = stackTrace.substring(0, 250);
        }
        exceptionLog.setStackTrace(stackTrace);
        exceptionLogRepository.save(exceptionLog);
    }
}
