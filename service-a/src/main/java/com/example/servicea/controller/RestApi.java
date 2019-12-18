package com.example.servicea.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.security.rsa.RSAPrivateCrtKeyImpl;

/**
 * @author Viet Quoc Tran vt on 6/11/18. www.zeroexception.com
 */


@RestController
@RequestMapping("/api")
@Slf4j
public class RestApi {

    private String rsaStringKey = "-----BEGIN RSA PRIVATE KEY-----                                  MIIEogIBAAKCAQEAtnOsK9MBatlsPSey8SV8qYwpgJINmDNubYl91Ilk3RiZJhk5 e1CzY0pTuMfMMzY8ptalFnhop1IahQI0l00AsHZrjaavA0i9ElpWcZEYO3BOQOK1 PMweejrKdkZzOakFEDmHeWbsea6CywDEy7WpBfpZSJjxiRkWkXmRWikDPtfyzzcr 4wUtHtZCOjLg/VqHlNLEehS5w/LoeSGoktwT+m05T28qr1ntHg7EulCaBzYN4h7S 9+anwV1LgDT5FE8j2+dsJVD+J3g5rnk58jWv334au5Vk/YQrISrimvtt4yNSqVjd bppD91WH61WZRgbsovxBQnbJh0tdQ7JuiC+uhwIDAQABAoIBAEZTTcDSo1A5ICRu YeXbajK8CN867KyG3oCwJ2U91Df4nEd4H5TtpUeXRbAKqyXuOPCh+Z4wqT4dQoWU NQtArgxWiNxfrEPazK3/TiSWJb37at5NxEf78Z9xe8qdGxrVkFMRqatcxbedUWLI GBLhIZZhSfrWoPsURYOrVSBogIy+7wbG7Mf0fHiP6xENIVrbuPAXsF3fwfpXwXZE bAsQnzEFBg23JKrZ48t+hGAVTzcz6VAcadkQqMm3NpWH6HBa7a77UqcLd98PijCe AOk/FuEs8+ZsDGTI54Hk37E3IXMmIGqEwKkxnZfTj8sA8S9SfbKox3z7LZQsC0da dUHCDdkCgYEA6Zsxt085LUiB8OaXqJ+QSPbgHssYBNMuyOB/GipXZpNPWj4qthYF nZ8CpQ0Xdq7brxaqZ/jM7UBeczIrbBKerfRc3m+SJakwXCRxpSf22mXnPOCrDZzo jVDRZTnu3YbPNrvp9oCn05w9vS+HfL60BcTml7eQsH0Wt0iIs6vFlBMCgYEAx/Eg PpqS7etiYKjxxbKxjLPuxXLUSwl/CmKXKYw8DZcRzQA1pUDXjU+du7Ty2rfgbi2g pN44Uz4j4eT2AJMHvvuq7QdLTnhjeoGV+0t/PEYJ0W1DjfY8fnC/82F8lKJrxEfH I4Mj0JshHocPLmR8WbodDwn8LK/AaienoYsnwj0CgYAZUKYclUs+6wKZ1oNM1K3Q GmDDNYMO0svZHOrpLPl3tjL9NotuXAiTwi2JMVf/lodb+/rPdZjPhRA3L5KhDYuA kmCWAkU2K5WABrsCCMipdi2O3VHsEbrpxX5Ll/GYtAk0hzydZ+fH+MCABeyKyie8 EFBk1JGrboLNreCSh+TLXQKBgCsI8WHIL3vaQDCB7Jga4DBiwi/piVwlTRzVH6gn sP1T2NcBBortK/gUfOTcC7GcBkhwtbUGx6TTKTID8Bcrjm1DCgU+nqzjKLZDkh6m n1o/0PksxW+W4mpspkQiZYgwdXWn5Wr1XQ4Xdcd2VoQryMAfWQXVpluP8KBorKIO q54ZAoGAXM+nPf/jU+c+CCchny5jC69rwig2XWPIgrzkbcNsJZGkdayFk617sT7o abJbDnAvQwbjgpT3ezqmi6YKawRG3mbAElX1GV6TlANS9WN/padgPfghJ0q0qMeR yYwfFZE2uyP0ZrZQqisrOzLXrOJuVTLObH4pEtgS81p8Q6QIDxA=             -----END RSA PRIVATE KEY-----                                    ";

    @Value("${app.name}")
    private String appName;

    @GetMapping("/appName")
    public String getAppName() {
        return this.appName;
    }

    @GetMapping("/userId")
    public int getUserId(
            @RequestParam(value = "userId", required = false, defaultValue = "0") int userId) {
        return userId;
    }

    @GetMapping("/all-params")
    public HttpEntity<?> getAllParams(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(params, HttpStatus.OK);
    }

    @GetMapping("/headers")
    public HttpEntity<?> getHeaders(@RequestHeader(value = "x-param") String headers) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("message", "success");
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @GetMapping("/message")
    public HttpEntity<?> getMessage() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("message", "success");
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @GetMapping("/identitymanagement/v3/Users/me")
    public HttpEntity<?> getUsersMe(
            @RequestParam(value = "username", required = false, defaultValue = "No Name") String username) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("display", "user");
        ObjectNode node1 = JsonNodeFactory.instance.objectNode();
        node1.put("username", username);
        return new ResponseEntity<>(Arrays.asList(node, node, node1), HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<?> getToken() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        json.put("token", "..token..");

        Map<String, Object> values = new HashMap<>();
        values.put("name", "John");

//        Claims claims;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateCrtKeyImpl privateKey = (RSAPrivateCrtKeyImpl)keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

//        DatatypeConverter.
        Base64.getEncoder().encodeToString(privateKey.getEncoded());

        log.debug(privateKey.getModulus().toString());
        log.debug(privateKey.getPrivateExponent().toString());
        log.debug(publicKey.toString());
        log.debug("============================================================");
        log.debug(getHexString(privateKey.getEncoded()));
        log.debug("-----BEGIN PRIVATE KEY----- {} -----END PRIVATE KEY-----", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        log.debug(getHexString(publicKey.getEncoded()));
        log.debug("-----BEGIN PUBLIC KEY----- {} -----END PUBLIC KEY-----",
            Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        this.rsaStringKey = this.rsaStringKey.replace("-----BEGIN PRIVATE KEY-----", "");
        this.rsaStringKey = this.rsaStringKey.replace("-----END PRIVATE KEY-----", "");
        this.rsaStringKey = this.rsaStringKey.replaceAll("\\s+","");

//        PKCS8EncodedKeySpec keySpec =
//        RSAPrivateKey key = RSAPrivateCrtKeyImpl.newKey(this.rsaStringKey.getBytes());
//        String secretKey = RandomStringUtils.random(10);


//        byte [] pkcs8EncodedBytes = Base64.decode(this.rsaStringKey, );

        // extract the private key

//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        PrivateKey privKey = kf.generatePrivate(keySpec);
//        System.out.println(privKey);

        String claim = Jwts.builder()
                .setId("jti_id_zzz")
                .setIssuer("auth.lingbook.io")
                .setSubject("user_id_zz")
                .setAudience("audience_zz")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
//                .setHeader()
//                .setPayload()
                .addClaims(values)
                .signWith(SignatureAlgorithm.RS256, privateKey)
//                .signWith(SignatureAlgorithm.RS256, key)
                .compact();

//        RsaSigner signer = new RsaSigner(this.rsaStringKey);
//        Jwt jwt = JwtHelper.encode(values.toString(), signer);
//        log.debug(jwt.getEncoded());

        return ResponseEntity.of(Optional.of(claim));
    }


    @PostMapping("/token")
    public ResponseEntity<?> verifyToken(@RequestBody String token) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.
        return ResponseEntity.of(node);
    }

    private String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}
