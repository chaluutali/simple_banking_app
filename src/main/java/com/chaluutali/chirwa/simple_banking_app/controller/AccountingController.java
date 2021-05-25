package com.chaluutali.chirwa.simple_banking_app.controller;

import com.chaluutali.chirwa.simple_banking_app.dto.security.AuthRequest;
import com.chaluutali.chirwa.simple_banking_app.dto.TransactionRequest;
import com.chaluutali.chirwa.simple_banking_app.dto.TransferRequest;
import com.chaluutali.chirwa.simple_banking_app.services.security.AuthService;
import com.chaluutali.chirwa.simple_banking_app.services.AccountingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AccountingController {

    @Autowired
    private final AuthService authService;

    @Autowired
    private final AccountingService accountingService;

    @PreAuthorize("hasRole('ROLE_ACCOUNT_HOLDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/withdraw", headers="Accept=application/json", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> withdraw(@RequestBody final TransactionRequest transactionRequest)  {
        return accountingService.withdraw(transactionRequest);
    }

    @PreAuthorize("hasRole('ROLE_ACCOUNT_HOLDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/deposit", headers="Accept=application/json", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> deposit(@RequestBody final TransactionRequest transactionRequest)  {
        return accountingService.deposit(transactionRequest);
    }

    @PreAuthorize("hasRole('ROLE_ACCOUNT_HOLDER')")
    @RequestMapping(method = RequestMethod.POST, value = "/transfer", headers="Accept=application/json", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> transfer(@RequestBody final TransferRequest transferRequest)  {
        return accountingService.transfer(transferRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate", headers="Accept=application/json", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final AuthRequest authenticationRequest) {
        return authService.authenticate(authenticationRequest);
    }
}
