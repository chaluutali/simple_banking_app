package com.chaluutali.chirwa.simple_banking_app.enums.security;

import com.chaluutali.chirwa.simple_banking_app.enums.security.ApplicationUserPermissions;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.chaluutali.chirwa.simple_banking_app.enums.security.ApplicationUserPermissions.ACCOUNT_READ;
import static com.chaluutali.chirwa.simple_banking_app.enums.security.ApplicationUserPermissions.ACCOUNT_WRITE;

public enum ApplicationUserRole {

    ACCOUNT_HOLDER(Sets.newHashSet(ACCOUNT_READ, ACCOUNT_WRITE)),
    ADMIN(Sets.newHashSet(ACCOUNT_READ, ACCOUNT_WRITE));

    @Getter
    private final Set<ApplicationUserPermissions> permissions;

    ApplicationUserRole(Set<ApplicationUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
