package com.chaluutali.chirwa.simple_banking_app.dto.security;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "user", schema = "bank")
public class ApplicationUser {
    @Id
    private UUID userID;
    private String name;
    private String password;
}
