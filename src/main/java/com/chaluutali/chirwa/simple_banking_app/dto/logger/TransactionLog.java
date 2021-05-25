package com.chaluutali.chirwa.simple_banking_app.dto.logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "audit", schema = "bank")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLog {
    @Id
    private UUID auditID;
    private Instant auditorTimestamp;
    private String auditAction;
    private String accountNumber;
}
