package com.ternova.chatbot.frontservices.controllers;

import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternova.chatbot.frontservices.utils.Helpers;

import payloads.requests.flujo2.Flujo2BoletaDePagoRequest;
import payloads.requests.flujo2.Flujo2ListaBoletasPendientesRequest;
import payloads.requests.flujo2.Flujo2ProcesarPlanillaRequest;
import payloads.responses.GeneralResponse;


@RestController
@RequestMapping("/api/front/flujo/2")
public class Flujo2Controller {

    @Value("${key}")
    private String key;
    @Value("${host}")
    private String host;
    @Value("${backPath}")
    private String backPath;

    @PostMapping( path = "/listaBoletasPendientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse listaBoletasPendientes(@RequestBody Flujo2ListaBoletasPendientesRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
                String requestUri = host.concat(backPath).concat("2/listaBoletasPendientes");
        ObjectMapper objectMapper = new ObjectMapper();       

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping( path="/boletaDePago", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse boletaDePago(@RequestBody Flujo2BoletaDePagoRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("2/boletaDePago");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping( path="/procesarPlanilla", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse procesarPlanilla(@RequestBody Flujo2ProcesarPlanillaRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("2/procesarPlanilla");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }
}