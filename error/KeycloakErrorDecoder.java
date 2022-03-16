package br.usp.poli.keycloak.client.error;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import br.usp.poli.keycloak.client.payload.KeycloakErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

public class KeycloakErrorDecoder implements ErrorDecoder {

    public Exception decode(String methodKey, Response response) {
        if (response == null) {
            return new IllegalArgumentException("KeycloakErrorDecoder response cannot be null!");
        }

        String resp = null;
        try {
            resp = IOUtils.toString(response.body().asInputStream(), "UTF-8");
        } catch (IOException e) {
            return (Exception) new KeycloakClientErrorException(
                    "Couldn't process Keycloak's response; " + e.getMessage());
        }

        Gson gson = new Gson();
        KeycloakErrorResponse error = null;
        try {
            error = (KeycloakErrorResponse) gson.fromJson(resp, KeycloakErrorResponse.class);
        } catch (JsonSyntaxException e) {
            return (Exception) new KeycloakClientErrorException("Unexpected response from Keycloak: " + resp, 500);
        }

        return new KeycloakClientErrorException(
                (error != null) ? (error.getError() + " - " + error.getError_description()) : String.valueOf(response.status()),
                response.status());
    }

}
