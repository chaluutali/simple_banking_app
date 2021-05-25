package com.chaluutali.chirwa.simple_banking_app.services;

import com.chaluutali.chirwa.simple_banking_app.dto.*;
import com.chaluutali.chirwa.simple_banking_app.dto.logger.TransactionLog;
import com.chaluutali.chirwa.simple_banking_app.enums.TransactionType;
import com.chaluutali.chirwa.simple_banking_app.repositories.AccountRepository;
import com.chaluutali.chirwa.simple_banking_app.services.logger.TransactionLogger;
import com.chaluutali.chirwa.simple_banking_app.services.validator.AccountingRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountingService {

    @Autowired
    private final AccountingRequestValidator accountingRequestValidator;

    @Autowired
    private final TransactionLogger transactionLogger;

    @Autowired
    private final AccountRepository accountRepository;

    public ResponseEntity<?> withdraw(final TransactionRequest transactionRequest) {
        final Account account = accountingRequestValidator.validate(transactionRequest.getAccountNumber(), transactionRequest.getAmount(), TransactionType.WITHDRAW);
        account.setAccountBalance(account.getAccountBalance() - transactionRequest.getAmount());
        accountRepository.save(account);
        logTransaction(account,TransactionType.WITHDRAW);
        return processTransactionResult(account);
    }

    public ResponseEntity<?> deposit(final TransactionRequest transactionRequest) {
        final Account account = accountingRequestValidator.validate(transactionRequest.getAccountNumber(), transactionRequest.getAmount(), TransactionType.DEPOSIT);
        account.setAccountBalance(account.getAccountBalance() + transactionRequest.getAmount());
        accountRepository.save(account);
        logTransaction(account,TransactionType.DEPOSIT);
        return processTransactionResult(account);
    }

    public ResponseEntity<?> transfer(final TransferRequest transferRequest) {
        final Account accountFrom = accountingRequestValidator.validate(transferRequest.getAccountFrom(), transferRequest.getAmount(), TransactionType.DEPOSIT);
        final Account accountTo = accountingRequestValidator.checkIfAccountExists(transferRequest.getAccountTo());
        accountFrom.setAccountBalance(accountFrom.getAccountBalance() - transferRequest.getAmount());
        accountTo.setAccountBalance(accountTo.getAccountBalance() + transferRequest.getAmount());
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        logTransaction(accountFrom,TransactionType.TRANSFER);
        logTransaction(accountTo,TransactionType.TRANSFER);
        return processTransferResult(accountTo);
    }

    private void logTransaction(final Account account, final TransactionType transactionType){
        transactionLogger.logTransactionEvent(TransactionLog.builder()
                .auditID(UUID.randomUUID())
                .auditorTimestamp(Instant.now())
                .auditAction(transactionType.name())
                .accountNumber(account.getAccountNumber())
                .build());
    }

    private ResponseEntity<?> processTransactionResult(final Account account){
        return new ResponseEntity<>(TransactionResponse.builder()
                .amount(account.getAccountBalance())
                .build(), HttpStatus.OK);
    }

    private ResponseEntity<?> processTransferResult(final Account account){
        return new ResponseEntity<>(TransferResponse.builder()
                .accountTo(account.getAccountNumber())
                .isSuccess(true)
                .amount(account.getAccountBalance())
                .build(), HttpStatus.OK);
    }

}
