package org.books.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserSignInPayload {

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5, max = 150)
    private String password;

    public UserSignInPayload() {}

    /* <-> getters and setters <-> */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
