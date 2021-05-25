package com.chaluutali.chirwa.simple_banking_app.services.security;

import com.chaluutali.chirwa.simple_banking_app.dto.security.ApplicationUser;
import com.chaluutali.chirwa.simple_banking_app.enums.security.ApplicationUserRole;
import com.chaluutali.chirwa.simple_banking_app.repositories.security.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final ApplicationUser user = applicationUserRepository.findByName(username);
        if(user != null){
            return User.builder()
                    .username(user.getName())
                    .password(user.getPassword())
                    .authorities(ApplicationUserRole.ACCOUNT_HOLDER.getGrantedAuthorities())
                    .build();
        }else{
            throw new UsernameNotFoundException(String.format("User %s not found!", username));
        }
    }
}
