package com.balazs.hajdu.client.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Spring security related service.
 *
 * @author Balazs Hajdu
 */
@Service
public class AccountService implements UserDetailsService {

    private static final String USER_ROLE = "USER_ROLE";

    private final PasswordEncoder passwordEncoder;

    @Inject
    private AccountRepository accountRepository;

    public AccountService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return createUser(account);
    }

    private UserDetails createUser(Account account) {
        return new User(account.getUsername(), account.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(USER_ROLE)));
    }

}
