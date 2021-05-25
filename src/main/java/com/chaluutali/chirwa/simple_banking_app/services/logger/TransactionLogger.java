package com.chaluutali.chirwa.simple_banking_app.services.logger;

import com.chaluutali.chirwa.simple_banking_app.dto.logger.TransactionLog;
import com.chaluutali.chirwa.simple_banking_app.repositories.TransactionLoggerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionLogger {

    @Autowired
    private final TransactionLoggerRepository transactionLoggerRepository;

    public void logTransactionEvent(TransactionLog transactionLog){
        transactionLoggerRepository.save(transactionLog);
    }

}
