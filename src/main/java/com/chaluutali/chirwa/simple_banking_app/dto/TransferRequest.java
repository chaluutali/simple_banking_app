package com.chaluutali.chirwa.simple_banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class TransferRequest {
    @NonNull
    private final String accountFrom;
    @NonNull
    private final String accountTo;
    @NonNull
    private final double amount;
}
