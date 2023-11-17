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

import payloads.requests.flujo5.Flujo5SolicitudPermisoRequest;
import payloads.responses.GeneralResponse;

@RestController
@RequestMapping("/api/front/flujo/5")
public class Flujo5Controller{

    @Value("${key}")
    private String key;
    @Value("${host}")
    private String host;
    @Value("${backPath}")
    private String backPath;

    @PostMapping( path = "/razonPermiso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse razonPermiso(@RequestBody Flujo5SolicitudPermisoRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("5/razonPermiso");
        ObjectMapper objectMapper = new ObjectMapper();       

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }
    
    @PostMapping( path="/crearSolicitud", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse crearSolicitud(@RequestBody Flujo5SolicitudPermisoRequest  _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("5/crearSolicitud");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping( path="/mostrarSolicitudes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse mostrarSolicitudes(@RequestBody Flujo5SolicitudPermisoRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("5/mostrarSolicitudes");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping( path="/validaciones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse validaciones(@RequestBody Flujo5SolicitudPermisoRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException    
    {
        ObjectMapper objectMapper = new ObjectMapper();
        GeneralResponse generalResponse = new GeneralResponse();
        boolean razonValida = false;

        switch (_request.getCampoValidacion()) {
            case "1":
                generalResponse = this.razonPermiso(_request);
                String listado = objectMapper.convertValue(generalResponse.getMessage(),String.class);

                String[] razones = listado.split("\n");
                for (String r : razones) {
                    if (r.contains(_request.getRazon().concat("."))){
                        razonValida = true;
                        break;
                    }                    
                }

                generalResponse.setResponseValid(razonValida);
                break;
        
            case "2":
                if (_request.getMotivo().length() > 1800){
                    generalResponse.setResponseValid(razonValida);
                    generalResponse.setMessage("Motivo de permiso debe ser menor a 1800 caracteres");
                }
                else{
                    generalResponse.setResponseValid(true);
                }
                break;

            case "3":
                razonValida = Helpers.validarFecha(_request.getFechaInicio());
                generalResponse.setResponseValid(razonValida);
                break;
                
            case "4":
                razonValida = Helpers.validarFecha(_request.getFechaFin());
                if (razonValida){
                    razonValida = Helpers.validarRangoFecha(_request.getFechaInicio(), _request.getFechaFin());
                }
                generalResponse.setResponseValid(razonValida);
                break;

            case "5":
                String requestUri = host.concat(backPath).concat("5/validarCreacionDePermiso");

                String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
                generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
                break;

            case "6":
                if (Integer.parseInt(_request.getHoraFin()) < Integer.parseInt(_request.getHoraInicio())){
                    //Si la fecha fin es menor a la fecha inicio validamos que sea en dias diferentes
                    razonValida = Helpers.validarRangoFecha(_request.getFechaInicio(), _request.getFechaFin());

                    generalResponse.responseValid(razonValida);
                }
                else{
                    generalResponse.responseValid(true);
                }

                break;

            case "7":
                String requestUris = host.concat(backPath).concat("5/validarCreacionDePermiso");

                String backResponses = Helpers.executePostRequestToService(_request, requestUris, key);
                generalResponse = objectMapper.readValue(backResponses, GeneralResponse.class);
                break;
        }

        return generalResponse;
    }    
}