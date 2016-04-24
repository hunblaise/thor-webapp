package com.balazs.hajdu.client.account;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class AccountRepositoryTest {

    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_PASSWORD = "test-password";

    private AccountRepository accountRepository;

    @BeforeMethod
    public void setUp() {
        accountRepository = new AccountRepository(TEST_USERNAME, TEST_PASSWORD);
    }

    @Test
    public void shouldReturnAnAccountIfTheUserIsPresentInTheDatabase() {
        // given
        Account expected = new Account(TEST_USERNAME, TEST_PASSWORD);

        // when
        Account actual = accountRepository.getAccountByUsername(TEST_USERNAME);

        // then
        assertThat(actual.getUsername(), is(expected.getUsername()));
        assertThat(actual.getPassword(), is(expected.getPassword()));
    }

    @Test
    public void shouldReturnNullIfUserIsNotFoundInTheDatabase() {
        // given
        Account expected = null;

        // when
        Account actual = accountRepository.getAccountByUsername("invalid-username");

        // then
        assertThat(actual, is(nullValue()));
    }

}