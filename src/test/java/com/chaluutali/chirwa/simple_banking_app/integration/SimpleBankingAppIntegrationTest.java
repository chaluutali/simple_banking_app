package com.chaluutali.chirwa.simple_banking_app.integration;

import com.chaluutali.chirwa.simple_banking_app.configurations.ApplicationConfig;
import com.chaluutali.chirwa.simple_banking_app.configurations.security.ApplicationSecurityConfig;
import com.chaluutali.chirwa.simple_banking_app.controller.AccountingController;
import com.chaluutali.chirwa.simple_banking_app.dto.Account;
import com.chaluutali.chirwa.simple_banking_app.dto.logger.TransactionLog;
import com.chaluutali.chirwa.simple_banking_app.dto.security.ApplicationUser;
import com.chaluutali.chirwa.simple_banking_app.dto.security.AuthRequest;
import com.chaluutali.chirwa.simple_banking_app.repositories.AccountRepository;
import com.chaluutali.chirwa.simple_banking_app.repositories.TransactionLoggerRepository;
import com.chaluutali.chirwa.simple_banking_app.repositories.security.ApplicationUserRepository;
import com.chaluutali.chirwa.simple_banking_app.services.AccountingService;
import com.chaluutali.chirwa.simple_banking_app.services.jwt.JwtRequestFilter;
import com.chaluutali.chirwa.simple_banking_app.services.jwt.JwtService;
import com.chaluutali.chirwa.simple_banking_app.services.logger.TransactionLogger;
import com.chaluutali.chirwa.simple_banking_app.services.security.ApplicationUserService;
import com.chaluutali.chirwa.simple_banking_app.services.security.AuthService;
import com.chaluutali.chirwa.simple_banking_app.services.validator.AccountingRequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationSecurityConfig.class,
        ApplicationConfig.class,
        AccountingController.class,
        TransactionLog.class,
        ApplicationUser.class,
        Account.class,
        ApplicationUserRepository.class,
        AccountRepository.class,
        TransactionLoggerRepository.class,
        JwtRequestFilter.class,
        JwtService.class,
        TransactionLogger.class,
        ApplicationUserService.class,
        AuthService.class,
        AccountingRequestValidator.class,
        AccountingService.class})
@WebAppConfiguration
@WebMvcTest(AccountingController.class)
public class SimpleBankingAppIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ApplicationUserRepository applicationUserRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionLoggerRepository transactionLoggerRepository;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenJAR_whenServletContext_thenItProvidesAccountingController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("accountingController"));
        Assertions.assertNotNull(webApplicationContext.getBean("accountingService"));
    }

    @Test
    public void givenAuthenticatePost_whenMockMVC_thenVerifyResponse() throws Exception {
        final AuthRequest authRequest = new AuthRequest("Chaluutali","1234");
        final ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUserID(UUID.randomUUID());
        applicationUser.setName(authRequest.getUserName());
        applicationUser.setPassword(authRequest.getPassword());
        when(applicationUserRepository.findByName(anyString())).thenReturn(applicationUser);
        when(transactionLoggerRepository.save(any())).thenReturn(null);
        when(accountRepository.save(any())).thenReturn(null);
        this.mockMvc.perform( MockMvcRequestBuilders
                .post("/authenticate")
                .content(asJsonString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwt").isNotEmpty());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
