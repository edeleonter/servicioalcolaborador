package com.ternova.chatbot.frontservices.controllers;

import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternova.chatbot.frontservices.utils.Helpers;

import payloads.requests.flujo0.Flujo0ApellidoRequest;
import payloads.requests.flujo0.Flujo0CarnetRequest;
import payloads.requests.flujo0.Flujo0DUIRequest;
import payloads.requests.flujo0.Flujo0MenuPrincipalAccessRequest;
import payloads.responses.GeneralResponse;



@RestController
@RequestMapping("/api/front/flujo/0")
public class Flujo0Controller {

    @Value("${key}")
    private String key;
    @Value("${host}")
    private String host;
    @Value("${backPath}")
    private String backPath;
    
    @PostMapping( path = "/validateDui", consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse validateDUI(@RequestBody Flujo0DUIRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();
        String requestUri = host.concat(backPath).concat("0/validateDui");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String dui = _request.getDui();
        dui = dui.replace("-", "");

        if (dui.length() == 9)
        {
            _request.setDui(dui.substring(0, 8) + "-" + dui.substring(8));            
            String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
            GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
            
            if (generalResponse.isResponseValid())
            {
                response.setResponseValid(true);
                response.setObjectResponse(generalResponse.getObjectResponse());
            }
            else
            {
                response.setResponseValid(false);
                response.setMessage(generalResponse.getMessage());
            }
        }
        else
        {
            response.setResponseValid(false);
            response.setMessage("El DUI no coincide con los datos registrados, por favor digita correctamente tu DUI");
        }

        return response;
    
    }
    
    @PostMapping( path = "/validateCarnet", consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse validateCarnet(@RequestBody Flujo0CarnetRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();

        String formattedCarnet = StringUtils.trim(StringUtils.upperCase(StringUtils.stripAccents(_request.getUserCarnet())));

        if (StringUtils.equals(formattedCarnet, _request.getCarnet()))
        {
            response.setResponseValid(true);
        }
        else
        {
            response.setResponseValid(false);
            response.setMessage("El carnet no coincide con los datos registrados, favor digita correctamente el Carnet");
        }

        return response;
    }

    @PostMapping( path = "/validateApellidos", consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse validateApellidos(@RequestBody Flujo0ApellidoRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();

        String formattedApellido = StringUtils.trim(StringUtils.upperCase(StringUtils.stripAccents(_request.getUserApellido())));

        if (StringUtils.equals(formattedApellido, _request.getApellido()))
        {
            response.setResponseValid(true);
        }
        else
        {
            response.setResponseValid(false);
            response.setMessage("Los apellidos no coinciden con los datos registrados, por favor digítalos correctamente");
        }

        return response;
    }

    @GetMapping("/menuPrincipal")
    GeneralResponse getMenuPrincipal() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();
        String requestUri = host.concat(backPath).concat("0/menuPrincipal");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService("", requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        response.setResponseValid(generalResponse.isResponseValid());
        response.setMessage(generalResponse.getMessage());
        
        return response;    
    }

    @PostMapping( path = "/validateMenuPrincipalAccess", consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse validateMenuPrincipalAccess(@RequestBody Flujo0MenuPrincipalAccessRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        GeneralResponse response = new GeneralResponse();

        String requestUri = host.concat(backPath).concat("0/validateMenuPrincipalAccess");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        response.setResponseValid(generalResponse.isResponseValid());
        response.setMessage(generalResponse.getMessage());
        
        return response;
    }

    
}
