package com.rcksrs.delivery.infra.security;

import com.rcksrs.delivery.core.domain.entity.Role;
import com.rcksrs.delivery.core.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public record UserDetailsEntity(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = new HashSet<GrantedAuthority>();

        switch (user.getRole()) {
            case ADMIN -> {
                roles.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
                roles.add(new SimpleGrantedAuthority(Role.MANAGER.name()));
                roles.add(new SimpleGrantedAuthority(Role.USER.name()));
            }
            case MANAGER -> {
                roles.add(new SimpleGrantedAuthority(Role.MANAGER.name()));
                roles.add(new SimpleGrantedAuthority(Role.USER.name()));
            }
            case USER -> roles.add(new SimpleGrantedAuthority(Role.USER.name()));
        }

        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
