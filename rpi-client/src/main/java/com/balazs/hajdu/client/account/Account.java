package com.balazs.hajdu.client.account;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store Raspberry Pi client account.
 *
 * @author Balazs Hajdu
 */
public final class Account {

    private final String username;
    private final String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equal(username, account.username) &&
                Objects.equal(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .toString();
    }
    // generated code ends here
}
