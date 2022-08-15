package org.books.security.model;

import org.books.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDetailsData implements UserDetails {

    private final User user;

    protected UserDetailsData(User user) {
        this.user = Optional.of(user)
                .orElse(new User());
    }

    /* <-> method builders <-> */

    public static UserDetailsData from(User user) {
        return new UserDetailsData(user);
    }

    /* <-> getters and setters <-> */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<? extends GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj != null && obj.getClass() != this.getClass())
            return false;

        return Objects.equals( getActualUser().getId(),
                ((UserDetailsData)obj).getActualUser().getId() );
    }

    @Override
    public String getPassword() {
        return getActualUser().getPassword();
    }

    @Override
    public String getUsername() {
        return getActualUser().getUsername();
    }

    public User getActualUser() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
