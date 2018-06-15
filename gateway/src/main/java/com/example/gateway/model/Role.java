package com.example.gateway.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

  @Getter
  private String name;

  @Override
  public String getAuthority() {
    return this.name;
  }

  public Role setName(String name) {
    this.name = name;
    return this;
  }
}
