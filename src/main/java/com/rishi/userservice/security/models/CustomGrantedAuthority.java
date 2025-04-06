package com.rishi.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rishi.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    String authority;

    CustomGrantedAuthority() {}

    CustomGrantedAuthority(Role role) {
        this.authority = role.getValue();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
