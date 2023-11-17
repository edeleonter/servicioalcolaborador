package com.ternova.chatbot.frontservices.utils;

import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Helpers {
    
    public static String executePostRequestToService(Object jsonObject, String requestUri, String key) throws URISyntaxException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException
    {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.TEXT_PLAIN);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestbody = objectMapper.writeValueAsString(jsonObject);

        // encrypt request body
        String cipherText = EncryptDecryptUtil.encrypt(requestbody, key);

        HttpEntity<String> httpEntity = new HttpEntity<>(cipherText, headers);

        RestTemplate restTemplate = new RestTemplate();
        String backReponse =  restTemplate.postForObject(requestUri, httpEntity, String.class);

        return EncryptDecryptUtil.decrypt(backReponse, key);
    }

    public static String executeGetRequestToService(String jsonObject, String requestUri, String key) throws URISyntaxException, JsonProcessingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException
    {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.TEXT_PLAIN);
        // encrypt uri property
        if (!StringUtils.isEmpty(jsonObject))
        {
            String cipherText = EncryptDecryptUtil.encrypt(jsonObject.toString(), key);
            requestUri += "/" + cipherText;
        }        

        RestTemplate restTemplate = new RestTemplate();
        String backReponse =  restTemplate.getForObject(requestUri, String.class);

        return EncryptDecryptUtil.decrypt(backReponse, key);
    }

    public static boolean validarFecha(String fecha){
        boolean ret = false;

        try{
            //Split la fecha
            String[] f = fecha.split("/");

            //Si el split no devulve una longitud de 3 es erronea
            if (f.length == 3){

                //Parse de los valores de fecha, sino se devuelve error
                int dia = Integer.parseInt(f[0]);
                int mes = Integer.parseInt(f[1]);
                int year = Integer.parseInt(f[2]);

                //Si no se obtiene una istancia valida, se obtiene una exception
                LocalDate.of(year, mes, dia);

                ret = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }

    public static boolean validarRangoFecha(String _fechaI, String _fechaF){
        boolean ret = false;

        try{
            //Split la fecha
            String[] fi = _fechaI.split("/");
            String[] ff = _fechaF.split("/");

            //Parse de los valores de fecha, sino se devuelve error
            int diai = Integer.parseInt(fi[0]);
            int mesi = Integer.parseInt(fi[1]);
            int yeari = Integer.parseInt(fi[2]);

            int diaf = Integer.parseInt(ff[0]);
            int mesf = Integer.parseInt(ff[1]);
            int yearf = Integer.parseInt(ff[2]);            

            //Si no se obtiene una istancia valida, se obtiene una exception
            LocalDate ini = LocalDate.of(yeari, mesi, diai);
            LocalDate fin = LocalDate.of(yearf, mesf, diaf);

            if (fin.isEqual(ini) || fin.isAfter(ini)){
                ret = true;
            }

        }catch (Exception e){
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }
}