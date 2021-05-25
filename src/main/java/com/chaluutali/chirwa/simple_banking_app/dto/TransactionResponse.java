package com.chaluutali.chirwa.simple_banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
public class TransactionResponse {
    @NonNull
    private final double amount;
}
