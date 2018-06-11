package com.example.serviceb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Viet Quoc Tran vt on 6/11/18. www.zeroexception.com
 */

@RestController
@RequestMapping("/api")
public class RestApi {


  @Value("${app.name}")
  private String appName;

  @GetMapping("/appName")
  public String getAppName() {
    return this.appName;
  }
}
