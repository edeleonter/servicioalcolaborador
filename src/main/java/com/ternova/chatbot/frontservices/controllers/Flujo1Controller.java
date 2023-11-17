package com.ternova.chatbot.frontservices.controllers;

import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternova.chatbot.frontservices.utils.Helpers;

import payloads.responses.GeneralResponse;

@RestController
@RequestMapping("/api/front/flujo/1")
public class Flujo1Controller {
    
    @Value("${key}")
    private String key;
    @Value("${host}")
    private String host;
    @Value("${backPath}")
    private String backPath;

    @GetMapping("/directoryMenu")
    GeneralResponse getMenuPrincipal() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();
        String requestUri = host.concat(backPath).concat("1/directoryMenu");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService("", requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        response.setResponseValid(generalResponse.isResponseValid());
        response.setMessage(generalResponse.getMessage());
        
        return response;    
    }

    @GetMapping("/directoryInfo/{_directory}")
    GeneralResponse getDirectoryInfo(@PathVariable int _directory) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("1/directoryInfo");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService(String.valueOf(_directory), requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        return generalResponse;    
    }

    @GetMapping("/directoryInfoV2/{_directory}")
    GeneralResponse getDirectoryInfoV2(@PathVariable int _directory) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();

        String requestUri = host.concat(backPath).concat("1/directoryInfoV2");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService(String.valueOf(_directory), requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        response.setResponseValid(generalResponse.isResponseValid());
        response.setMessage(generalResponse.getMessage());
        response.setObjectResponse(generalResponse.getObjectResponse());
        
        return response;    
    }

    @GetMapping("/subDirectoryInfoV2/{_directorio}/{_subDirectorio}")
    GeneralResponse getSubDirectoryInfoV2(@PathVariable int _directorio, @PathVariable int _subDirectorio) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();

        String requestUri = host.concat(backPath).concat("1/subDirectoryInfoV2");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService(String.valueOf(_directorio) + "/" + String.valueOf(_subDirectorio), requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        response.setResponseValid(generalResponse.isResponseValid());
        response.setMessage(generalResponse.getMessage());
        
        return response;    
    }
    
}
