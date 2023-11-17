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

import payloads.requests.flujo0.Flujo0CarnetRequest;
import payloads.requests.flujo4.Flujo4DatosSolicitudConstanciaRequest;
import payloads.responses.GeneralResponse;
import payloads.responses.flujo4.Flujo4ListadoSitiosConstanciaResponse;

@RestController
@RequestMapping("/api/front/flujo/4")
public class Flujo4Controller {

    @Value("${key}")
    private String key;
    @Value("${host}")
    private String host;
    @Value("${backPath}")
    private String backPath;

    @PostMapping( path = "/listadoSitios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse listadoSitios(@RequestBody String _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("4/listadoSitios");
        ObjectMapper objectMapper = new ObjectMapper();       

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }
    
    @PostMapping( path="/crearSolicitud", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse crearSolicitud(@RequestBody Flujo4DatosSolicitudConstanciaRequest  _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("4/crearSolicitud");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping( path="/mostrarSolicitudes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse mostrarSolicitudes(@RequestBody Flujo0CarnetRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("4/mostrarSolicitudes");
        ObjectMapper objectMapper = new ObjectMapper();
        
        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping( path="/validaciones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResponse validaciones(@RequestBody Flujo4DatosSolicitudConstanciaRequest _request) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException    
    {
        ObjectMapper objectMapper = new ObjectMapper();
        GeneralResponse generalResponse = new GeneralResponse();
        generalResponse.setResponseValid(true);

        switch (_request.getCampoValidacion()) {
            case "1":
                if (_request.getCon_cometario().length() > 1800){
                    generalResponse.setResponseValid(false);
                    generalResponse.setMessage("Detalle de constancia debe ser menor a 1800 caracteres");
                }
                break;
        
            case "2":
                boolean sitioValido = false;
                generalResponse = this.listadoSitios("");
                Flujo4ListadoSitiosConstanciaResponse sitio = objectMapper.convertValue(generalResponse.getObjectResponse(), Flujo4ListadoSitiosConstanciaResponse.class);
                String[] sitios = sitio.getSitiosConstancia().split("\n");

                for (String s : sitios) {
                    if (s.contains(_request.getCon_ubicacion().concat("."))){
                        sitioValido = true;
                        break;
                    }
                }

                generalResponse.setResponseValid(sitioValido);
                break;
        }

        return generalResponse;
    }    
}