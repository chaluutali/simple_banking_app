package com.chaluutali.chirwa.simple_banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class TransactionRequest {
    @NonNull
    private final String accountNumber;
    @NonNull
    private final double amount;
}
