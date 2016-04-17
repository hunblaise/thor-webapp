package com.balazs.hajdu.client.account;

import com.google.common.collect.ImmutableList;

/**
 * A repository to access the client's users.
 *
 * @author Balazs Hajdu
 */
public class AccountRepository {

    private final String username;
    private final String password;
    private final ImmutableList<Account> accounts;

    public AccountRepository(String username, String password) {
        this.username = username;
        this.password = password;

        accounts = ImmutableList.of(new Account(this.username, this.password));
    }

    /**
     * Retrieves the client's users.
     *
     * @param username username
     * @return account
     */
    public Account getAccountByUsername(String username) {
        return accounts.stream()
                .filter(account -> account.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

}
