package com.balazs.hajdu.client.account;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Balazs Hajdu
 */
@ContextConfiguration(classes = {PackageConfiguration.class})
public class AccountServiceTest {

    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_PASSWORD = "test-password";

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(enabled = false)
    public void shouldReturnUserIfItIsPresentInTheDatabase() {
        // given
        given(accountRepository.getAccountByUsername(TEST_USERNAME)).willReturn(anAccount());

        // when
        UserDetails actual = accountService.loadUserByUsername(TEST_USERNAME);

        // then
        assertThat(actual.getUsername(), is(TEST_USERNAME));
        assertThat(actual.getPassword(), is(TEST_PASSWORD));
    }

    @Test(expectedExceptions = UsernameNotFoundException.class, enabled = false)
    public void shouldThrowExceptionIfUserIsNotFound() {
        // given
        given(accountRepository.getAccountByUsername("invalid-username")).willReturn(null);

        // when
        UserDetails actual = accountService.loadUserByUsername("invalid-username");

        // then
    }

    private Account anAccount() {
        return new Account(TEST_USERNAME, TEST_PASSWORD);
    }
}