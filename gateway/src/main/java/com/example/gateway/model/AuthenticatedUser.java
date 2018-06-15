package com.example.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser  implements Authentication {

  private UserDetails user;

  public AuthenticatedUser(UserDetails user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.user.getAuthorities();
  }

  @Override
  @JsonIgnore
  public Object getCredentials() {
    return this.user.getPassword();
  }

  @Override
  public Object getDetails() {
    return this.user.toString();
  }

  @Override
  public Object getPrincipal() {
    return this.user;
  }

  @Override
  public boolean isAuthenticated() {
    return this.user != null;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not Implemented Method");
  }

  @Override
  public String getName() {
    return this.user.getUsername();
  }
}
