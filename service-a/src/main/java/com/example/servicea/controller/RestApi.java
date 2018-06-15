package com.example.servicea.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

  @GetMapping("/appName")
  public String getAppName() {
    return this.appName;
  }

  @GetMapping("/userId")
  public int getUserId(@RequestParam(value = "userId", required = false, defaultValue = "0") int userId) {
    return userId;
  }

  @GetMapping("/message")
  public HttpEntity<?> getMessage() {
    ObjectNode node = JsonNodeFactory.instance.objectNode();
    node.put("message", "success");
    return  new ResponseEntity<>(node, HttpStatus.OK);
  }

  @GetMapping("/identitymanagement/v3/Users/me")
  public HttpEntity<?> getUsersMe(@RequestParam(value = "username", required = false, defaultValue = "No Name") String username) {
    ObjectNode node = JsonNodeFactory.instance.objectNode();
    node.put("display", "user");
    ObjectNode node1 = JsonNodeFactory.instance.objectNode();
    node1.put("username", username);
    return  new ResponseEntity<>(Arrays.asList(node, node, node1), HttpStatus.OK);
//    HttpHeaders headers = new HttpHeaders();
//    headers.setLocation(URI.create("/message"));
//    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
  }
}
