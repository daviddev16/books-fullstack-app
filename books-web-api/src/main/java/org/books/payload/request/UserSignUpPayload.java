package org.books.payload.request;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserSignUpPayload {

    @Size(min = 5, max = 20)
    private String username;

    @Size(min = 5, max = 150)
    private String password;

    @Email
    @Size(min = 5, max = 50)
    private String email;

    @Size(min = 11, max = 15)
    private String cpf;

    @Size(min = 10, max = 20)
    private String contact;

    public UserSignUpPayload() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
