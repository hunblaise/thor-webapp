package com.balazs.hajdu.client.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package configuration.
 *
 * @author Balazs Hajdu
 */
@Configuration
public class PackageConfiguration {

    @Value("${RPI_CLIENT_USERNAME}")
    private String username;
    @Value("${RPI_CLIENT_PASSWORD}")
    private String password;

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepository(username, password);
    }

}
