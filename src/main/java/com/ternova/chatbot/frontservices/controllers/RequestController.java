package com.ternova.chatbot.frontservices.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternova.chatbot.frontservices.utils.EncryptDecryptUtil;

import payloads.requests.RequestEntity;

@RestController
@RequestMapping("/api")
public class RequestController {

    @Value("${key}")
    private String key;
    
    @GetMapping("/test1")
    String all() throws URISyntaxException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity();

        URI uri = new URI("http://localhost:8081/api/back");
        requestEntity.setFormat("b");

        ObjectMapper objectMapper = new ObjectMapper();

        String requestbody = objectMapper.writeValueAsString(requestEntity);
        
        // encrypt request body
        String cipherText = EncryptDecryptUtil.encrypt(requestbody, key);

        HttpEntity<String> httpEntity = new HttpEntity<>(cipherText, headers);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, httpEntity, String.class);
        
        return EncryptDecryptUtil.decrypt(result, key);    
    }

    @GetMapping("/key")
    String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @GetMapping("/IvParameter")
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
