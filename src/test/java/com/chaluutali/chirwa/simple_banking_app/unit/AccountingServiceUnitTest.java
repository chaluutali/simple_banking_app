package com.chaluutali.chirwa.simple_banking_app.unit;

import com.chaluutali.chirwa.simple_banking_app.dto.Account;
import com.chaluutali.chirwa.simple_banking_app.dto.TransactionRequest;
import com.chaluutali.chirwa.simple_banking_app.dto.TransferRequest;
import com.chaluutali.chirwa.simple_banking_app.enums.AccountType;
import com.chaluutali.chirwa.simple_banking_app.enums.TransactionType;
import com.chaluutali.chirwa.simple_banking_app.repositories.AccountRepository;
import com.chaluutali.chirwa.simple_banking_app.services.AccountingService;
import com.chaluutali.chirwa.simple_banking_app.services.logger.TransactionLogger;
import com.chaluutali.chirwa.simple_banking_app.services.validator.AccountingRequestValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountingServiceUnitTest {

    @InjectMocks
    private AccountingService accountingService;

    @MockBean
    @Mock
    private AccountRepository accountRepository;

    @MockBean
    @Mock
    private TransactionLogger transactionLogger;

    @MockBean
    @Mock
    private AccountingRequestValidator accountingRequestValidator;

    @Test
    public void valid_accountingServices_test(){
        //
        // Given
        //
        final TransactionRequest transactionRequest = new TransactionRequest("0987654321", 200);
        final TransferRequest transferRequest = new TransferRequest("0987654321","0987654322",200);
        final Account account = new Account();
        account.setAccountID(UUID.randomUUID());
        account.setAccountType(AccountType.SAVINGS.name());
        account.setAccountNumber("0987654321");
        account.setAccountHolderName("Chaluutali");
        account.setPassword("1234");
        account.setAccountBalance(4000);
        final Account accountTo = new Account();
        accountTo.setAccountID(UUID.randomUUID());
        accountTo.setAccountType(AccountType.CURRENT.name());
        accountTo.setAccountNumber("0987654322");
        accountTo.setAccountHolderName("Chaluutali");
        accountTo.setPassword("1234");
        accountTo.setAccountBalance(4500);
        //
        // When
        //
        when(accountingRequestValidator.validate(transactionRequest.getAccountNumber(),transactionRequest.getAmount(), TransactionType.WITHDRAW)).thenReturn(account);
        when(accountingRequestValidator.validate(transactionRequest.getAccountNumber(),transactionRequest.getAmount(), TransactionType.DEPOSIT)).thenReturn(account);
        when(accountingRequestValidator.checkIfAccountExists(any())).thenReturn(accountTo);
        when(accountRepository.save(any())).thenReturn(null);
        doNothing().when(transactionLogger).logTransactionEvent(any());
        //
        // Tests
        //
        final ResponseEntity<?> withdrawResult = accountingService.withdraw(transactionRequest);
        final ResponseEntity<?> depositResult = accountingService.deposit(transactionRequest);
        final ResponseEntity<?> transferResult = accountingService.transfer(transferRequest);

        //
        // Assertions
        //
        Assertions.assertTrue(Objects.requireNonNull(withdrawResult.getBody()).toString().contains("3800"));
        Assertions.assertTrue(Objects.requireNonNull(depositResult.getBody()).toString().contains("4000"));
        Assertions.assertTrue(Objects.requireNonNull(transferResult.getBody()).toString().contains("4700"));
    }

}
