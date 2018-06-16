package com.example.gateway.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.stereotype.Component;

@Component
public class SimpleFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(SimpleFilter.class);
  private AuthenticationManager authenticationManager;
  private TokenExtractor tokenExtractor = new BearerTokenExtractor();

  public SimpleFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    Authentication preAuthentication = tokenExtractor.extract(request);

    if (preAuthentication != null) {
      try {
        Authentication authentication = this.authenticationManager.authenticate(preAuthentication);
        if (authentication != null) {
          SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
          SecurityContextHolder.clearContext();
        }

      } catch (AuthenticationException exception) {
        logger.debug("AuthenticationException occurs ");
        SecurityContextHolder.clearContext();
        exception.printStackTrace();
      }
    }
    filterChain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
}
