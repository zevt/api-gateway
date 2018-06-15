package com.example.gateway.config;

import com.example.gateway.model.AuthenticatedUser;
import com.example.gateway.model.Role;
import com.example.gateway.model.User;
import java.time.Instant;
import java.util.Collections;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class SimpleAuthenticationManager implements AuthenticationManager {

  @Override
  public Authentication authenticate(Authentication preAuthentication)
      throws AuthenticationException {
    String token = preAuthentication.getPrincipal().toString();
    if (token != null && token.length() > 2) {
      Role role = new Role().setName("ADMIN");
      User user =
          new User()
              .setExpireAt(Instant.now().getEpochSecond() * 10)
              .setUsername("John")
              .setGrantedAuthorities(Collections.singletonList(role));
      return new AuthenticatedUser(user);
    }
    return null;
  }
}
