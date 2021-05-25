package com.chaluutali.chirwa.simple_banking_app.enums.security;

import lombok.Getter;

public enum ApplicationUserPermissions {

    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write");

    @Getter
    private final String permission;

    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }
}
