package com.example.gateway.config;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

import com.example.gateway.model.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Viet Quoc Tran vt on 6/12/18. www.zeroexception.com
 */

@Component
public class PreFilter extends ZuulFilter {

  private static final Logger log = LoggerFactory.getLogger(PreFilter.class);

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return PRE_DECORATION_FILTER_ORDER - 1;
  }

  @Override
  public boolean shouldFilter() {
    RequestContext ctx = getCurrentContext();
    return ctx.getRequest().getHeader("token") != null;

  }

  @Override
  public Object run() throws ZuulException {

    RequestContext ctx = getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    String header = request.getHeader("token");
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Map<String, List<String>> params = Maps.newHashMap();
    params.put("userId", Lists.newArrayList(header));
    params.put("username", Collections.singletonList(user.getUsername()));
    params.put("x-param", Collections.singletonList("one more param"));
    request.getParameterMap().forEach((k, v) -> params.put(k, Arrays.asList(v)));
    ctx.addZuulRequestHeader("Authorization", "Bearer " + "token");
    ctx.setRequestQueryParams(params);

    log.info(
        String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

    return null;
  }
}
