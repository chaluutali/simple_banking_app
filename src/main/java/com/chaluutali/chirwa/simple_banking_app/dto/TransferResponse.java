package com.chaluutali.chirwa.simple_banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
public class TransferResponse {
    @NonNull
    private final String accountTo;
    @NonNull
    private final boolean isSuccess;
    @NonNull
    private final double amount;
}
