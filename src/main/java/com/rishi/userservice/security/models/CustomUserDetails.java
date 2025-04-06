package com.rishi.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rishi.userservice.models.Role;
import com.rishi.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {

    String username;
    String password;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;
    List<GrantedAuthority> authorities;

    CustomUserDetails(){}

    public CustomUserDetails(User user) {
          this.username = user.getEmail();
          this.password = user.getPassword();
          this.accountNonExpired = true;
          this.accountNonLocked = true;
          this.credentialsNonExpired = true;
          this.enabled = true;
          this.authorities = new ArrayList<>();
          for (Role role : user.getRoles()) {
              authorities.add(new CustomGrantedAuthority(role));
          }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
