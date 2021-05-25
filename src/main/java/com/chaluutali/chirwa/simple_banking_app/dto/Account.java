package com.chaluutali.chirwa.simple_banking_app.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "account", schema = "bank")
public class Account {
    @Id
    private UUID accountID;
    private String accountNumber;
    private String password;
    private String accountType;
    private double accountBalance;
    private String accountHolderName;
}
