package org.books.builder;

import org.books.enums.RoleType;
import org.books.model.Role;
import org.books.model.User;
import org.books.repository.RoleRepository;

import java.util.Objects;


public final class UserBuilder {

    private User user;

    private UserBuilder(User user) {
        this.user = Objects.requireNonNull(user, "User cannot be null.");
    }

    public static UserBuilder create() {
        return new UserBuilder(new User());
    }

    public UserBuilder withRoles(RoleRepository roleRepository, RoleType... roleTypes) {
        for (RoleType roleType : roleTypes) {
            roleRepository.findByRoleType(roleType).ifPresent(user.getRoles()::add);
        }
        return this;
    }

    public UserBuilder withRoles(Role... roles) {
        for(Role role : roles) {
            user.getRoles().add(role);
        }
        return this;
    }

    public UserBuilder withEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder withContact(String contact) {
        user.setContact(contact);
        return this;
    }

    public UserBuilder withUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder withCpf(String cpf) {
        user.setCpf(cpf);
        return this;
    }

    public User get() {
        return user;
    }

}
