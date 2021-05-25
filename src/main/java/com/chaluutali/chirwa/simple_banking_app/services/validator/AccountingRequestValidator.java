package com.chaluutali.chirwa.simple_banking_app.services.validator;

import com.chaluutali.chirwa.simple_banking_app.dto.Account;
import com.chaluutali.chirwa.simple_banking_app.enums.AccountType;
import com.chaluutali.chirwa.simple_banking_app.enums.TransactionType;
import com.chaluutali.chirwa.simple_banking_app.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AccountingRequestValidator {

    @Autowired
    private final AccountRepository accountRepository;

    public Account checkIfAccountExists(final String accountNumber) {
        return validateAccountExistence(accountNumber);
    }

    public Account validate(final String accountNumber, final double amount, final TransactionType transactionType) {
       return validateBusinessLogic(accountNumber, amount, transactionType);
    }

    private Account validateBusinessLogic(final String accountNumber, final double amount, final TransactionType transactionType){
        final Account account = validateAccountExistence(accountNumber);
       if(account.getAccountType().equals(AccountType.SAVINGS.name())
                && transactionType.equals(TransactionType.WITHDRAW)
                && account.getAccountBalance() < 1000.00){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }else if(account.getAccountType().equals(AccountType.CURRENT.name())
                && transactionType.equals(TransactionType.WITHDRAW)
                && (account.getAccountBalance() + 100000.00) < amount){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }else {
            return account;
        }
    }

    private Account validateAccountExistence( final String accountNumber){
        Account account= accountRepository.findByAccountNumber(accountNumber);
        if (account == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found");
        }
        return account;
    }
}
