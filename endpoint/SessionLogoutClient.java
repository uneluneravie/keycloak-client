package br.usp.poli.keycloak.client.endpoint;

import java.util.Map;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SessionLogoutClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    String post(Map<String, ?> form);
    
    @RequestLine("GET ?id_token_hint={oidcToken}")
    String get(@Param("oidcToken") String oidcToken);
}
