package com.example.gateway.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

  private String username;
  private String password;
  private long expireAt;
  private boolean enabled;
  private List<GrantedAuthority> grantedAuthorities;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.grantedAuthorities;
  }

  @Override
  public String getPassword() {
    throw new UnsupportedOperationException("Not Implemented Method");
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.expireAt > Instant.now().getEpochSecond();
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
    return enabled;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public User setExpireAt(long expireAt) {
    this.expireAt = expireAt;
    return this;
  }

  public User setEnabled(boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public User setGrantedAuthorities(
      List<GrantedAuthority> grantedAuthorities) {
    this.grantedAuthorities = grantedAuthorities;
    return this;
  }
}
