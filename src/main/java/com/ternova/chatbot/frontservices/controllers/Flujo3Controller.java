package com.ternova.chatbot.frontservices.controllers;

import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternova.chatbot.frontservices.utils.Helpers;

import payloads.requests.flujo3.Flujo3ActualizarCampoExpRequest;
import payloads.requests.flujo3.Flujo3ExpedienteRequest;
import payloads.requests.flujo3.Flujo3SubirAdjuntoRequest;
import payloads.requests.flujo3.Flujo3ValidarAdjuntosDUIRequest;
import payloads.requests.flujo3.Flujo3ValidarCampoStringRequest;
import payloads.requests.flujo3.Flujo3ValidarCatalogoRequest;
import payloads.requests.flujo3.Flujo3ValidarCorreoRequest;
import payloads.responses.GeneralResponse;

@RestController
@RequestMapping("api/front/flujo/3")
public class Flujo3Controller {

    @Value("${key}")
    private String key;
    @Value("${host}")
    private String host;
    @Value("${backPath}")
    private String backPath;

    @PostMapping("/expedienteUsuario")
    GeneralResponse getExpediente(@RequestBody Flujo3ExpedienteRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = host.concat(backPath).concat("3/expedienteUsuario");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping("/expedienteActualizarDinamico")
    GeneralResponse expedienteActualizarDinamico(@RequestBody Flujo3ActualizarCampoExpRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = "back/flujo/3/expedienteActualizarDinamico";
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @GetMapping("/menuExpediente")
    GeneralResponse getMenuPrincipal() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("3/menuExpediente");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executeGetRequestToService("", requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        return generalResponse;    
    }

    @GetMapping("/departamentos")
    GeneralResponse getDepartamentos() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("3/departamentos");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executeGetRequestToService("", requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        return generalResponse;    
    }

    @GetMapping("/catalogoExpedienteDinamico/{_catalogoExpediente}")
    GeneralResponse catalogoExpedienteDinamico(@PathVariable int _catalogoExpediente) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("3/catalogoExpedienteDinamico");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService(String.valueOf(_catalogoExpediente), requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        return generalResponse;     
    }

    @PostMapping("/actualizarAdjuntosDUI")
    GeneralResponse actualizarAdjuntosDUI(@RequestBody Flujo3ValidarAdjuntosDUIRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = host.concat(backPath).concat("3/actualizarAdjuntosDUI");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping("/subirArchivo")
    GeneralResponse subirArchivo(@RequestBody Flujo3SubirAdjuntoRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = host.concat(backPath).concat("3/subirArchivo");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping("/actualizarExpedienteDinamico")
    GeneralResponse actualizarExpedienteDinamico(@RequestBody Flujo3ActualizarCampoExpRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = host.concat(backPath).concat("3/actualizarExpedienteDinamico");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping("/actualizarExpedienteFamiliares")
    GeneralResponse actualizarExpedienteFamiliares(@RequestBody Flujo3ActualizarCampoExpRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = host.concat(backPath).concat("3/actualizarExpedienteFamiliares");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @GetMapping("/catalogoFamiliares/{_carnet}")
    GeneralResponse catalogoFamiliares(@PathVariable int _carnet) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("3/catalogoFamiliares");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService(String.valueOf(_carnet), requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        return generalResponse;     
    }

    @GetMapping("/validarCatalogoFamiliares/{_idSeleccionado}")
    GeneralResponse catalogoFamiliares(@PathVariable String _idSeleccionado) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException, JsonProcessingException
    {
        String requestUri = host.concat(backPath).concat("3/validarCatalogoFamiliares");
        ObjectMapper objectMapper = new ObjectMapper();
        String backResponse = Helpers.executeGetRequestToService(String.valueOf(_idSeleccionado), requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);
        
        return generalResponse;     
    }

    @PostMapping("/validarCatalogo")
    GeneralResponse validarCatalogo(@RequestBody Flujo3ValidarCatalogoRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        String requestUri = host.concat(backPath).concat("3/validarCatalogo");
        ObjectMapper objectMapper = new ObjectMapper();

        String backResponse = Helpers.executePostRequestToService(_request, requestUri, key);
        GeneralResponse generalResponse = objectMapper.readValue(backResponse, GeneralResponse.class);

        return generalResponse;
    }

    @PostMapping("/validarCorreo")
    GeneralResponse validarCorreo(@RequestBody Flujo3ValidarCorreoRequest _request) throws JsonMappingException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException
    {
        GeneralResponse generalResponse = new GeneralResponse();

        if (StringUtils.isNotBlank(_request.getCorreo()))
        {
            if (validarCorreo(_request.getCorreo()))
            {
                generalResponse.setResponseValid(true);
            }
            else
            {
                generalResponse.setResponseValid(false);
                generalResponse.setMessage("Por favor escribe una dirección de correo válida.");
            }
        }
        else
        {
            generalResponse.setResponseValid(false);
            generalResponse.setMessage("Por favor ingrese su correo electronico");
        }

        return generalResponse;
    }

    public static boolean validarCorreo(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        return emailPat.matcher(email).find();
    }

    @PostMapping("/validarNumero")
    GeneralResponse validarNumero(@RequestBody Flujo3ValidarCampoStringRequest _request) throws JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException {
        
        GeneralResponse generalResponse = new GeneralResponse();

        if (StringUtils.isNotBlank(_request.getCampoValidar()))
        {
            // Eliminar el guion si lo trae
            String telefono = _request.getCampoValidar().replace("-", "");

            // Validar si el string tiene exactamente 8 dígitos
            if (telefono.matches("\\d{8}") && StringUtils.isNumeric(telefono)) 
            {
                // Insertar un guion después del cuarto dígito
                telefono = telefono.substring(0, 4) + "-" + telefono.substring(4);

                generalResponse.setResponseValid(true);
                generalResponse.setMessage(telefono);

            }
            else
            {
                generalResponse.setResponseValid(false);
                generalResponse.setMessage("Por favor escribe un número de 8 dígitos.");
            }
        }
        else
        {
            generalResponse.setResponseValid(false);
            generalResponse.setMessage("Por favor ingrese su correo electronico");
        }

        return generalResponse;
    }

    @PostMapping("/validarFechaDui")
    GeneralResponse validarFechaDui(@RequestBody Flujo3ValidarCampoStringRequest _request) throws JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException {
        
        GeneralResponse generalResponse = new GeneralResponse();

        if (StringUtils.isNotBlank(_request.getCampoValidar()) && validarDate(_request.getCampoValidar()))
        {
            generalResponse.setResponseValid(true);
        }
        else
        {
            generalResponse.setResponseValid(false);
            generalResponse.setMessage("¡Parece que la fecha no es correcta! Por favor coloca la fecha de vencimiento de tu nuevo DUI de la siguiente manera: día/mes/año. Ejemplo: 12/04/2028");
        }

        return generalResponse;
    }

    @PostMapping("/validarFechaNac")
    GeneralResponse validarFechaNac(@RequestBody Flujo3ValidarCampoStringRequest _request) throws JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException {
        
        GeneralResponse generalResponse = new GeneralResponse();

        if (StringUtils.isNotBlank(_request.getCampoValidar()) && validarDate2(_request.getCampoValidar()))
        {
            generalResponse.setResponseValid(true);
        }
        else
        {
            generalResponse.setResponseValid(false);
            generalResponse.setMessage("¡Parece que la fecha no es correcta! Por favor coloca la fecha de nacimiento la siguiente manera: día/mes/año. Ejemplo: 12/04/2028");
        }

        return generalResponse;
    }

    @PostMapping("/validarStringSinCaracteresEspeciales")
    GeneralResponse validarStringSinCaracteresEspeciales(@RequestBody Flujo3ValidarCampoStringRequest _request) throws JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, URISyntaxException {
        
        GeneralResponse generalResponse = new GeneralResponse();

        if (StringUtils.isNotBlank(_request.getCampoValidar()))
        {
            String pattern = "^[a-zA-Z\\s]*$";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(_request.getCampoValidar());

            if (m.matches())
            {
                generalResponse.setResponseValid(true);
            }
            else
            {
                generalResponse.setResponseValid(false);
                generalResponse.setMessage("¡Ha ocurrido un error! Asegúrate de no escribir números ni caracteres especiales");   
            }
            
        }
        else
        {
            generalResponse.setResponseValid(false);
            generalResponse.setMessage("¡Ha ocurrido un error! Asegúrate de no escribir números ni caracteres especiales");
        }

        return generalResponse;
    }


    public static boolean validarDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            Date parseDate = dateFormat.parse(date.trim());
            Date todayDate = new Date();

            return parseDate.after(todayDate);

        } catch (ParseException pe) {
            return false;
        }
    }

    public static boolean validarDate2(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }

        return true;
    }
}
