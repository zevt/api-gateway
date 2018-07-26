package com.example.serviceb.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Viet Quoc Tran vt on 6/11/18. www.zeroexception.com
 */

@RestController
@RequestMapping("/api")
public class RestApi {


  @Value("${app.name}")
  private String appName;

  @GetMapping("/userId")
  public int getUserId(@RequestParam(value = "userId", required = false, defaultValue = "0") int userId) {
    return userId;
  }
  @GetMapping("/all-params")
  public HttpEntity<?> getAllParams(@RequestParam Map<String, String> params) {
    return new ResponseEntity<>(params, HttpStatus.OK);
  }
  @GetMapping("/appName")
  public String getAppName() {
    return this.appName;
  }
}
