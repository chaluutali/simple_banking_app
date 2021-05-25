package com.chaluutali.chirwa.simple_banking_app.repositories.security;

import com.chaluutali.chirwa.simple_banking_app.dto.security.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, UUID> {
    ApplicationUser findByName(final String name);
}
