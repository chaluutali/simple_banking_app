package com.chaluutali.chirwa.simple_banking_app.services.security;

import com.chaluutali.chirwa.simple_banking_app.dto.security.AuthRequest;
import com.chaluutali.chirwa.simple_banking_app.dto.security.AuthResponse;
import com.chaluutali.chirwa.simple_banking_app.services.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final ApplicationUserService applicationUserService;

    @Autowired
    private final JwtService jwtService;

    public ResponseEntity<?> authenticate(final AuthRequest authenticationRequest) {
        final UserDetails userDetails = applicationUserService
                .loadUserByUsername(authenticationRequest.getUserName());
        if(userDetails != null && authenticationRequest.getPassword().equals(userDetails.getPassword())) {
            final String jwt = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(jwt));
        }else if(userDetails != null && !(authenticationRequest.getPassword().equals(userDetails.getPassword()))){
            throw new UsernameNotFoundException(String.format("Incorrect %s password!", authenticationRequest.getPassword()));
        }else {
            throw new UsernameNotFoundException(String.format("User %s not found!", authenticationRequest.getUserName()));
        }
    }
}
