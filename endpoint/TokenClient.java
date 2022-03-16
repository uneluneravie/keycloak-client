package br.usp.poli.keycloak.client.endpoint;

import java.util.Map;

import br.usp.poli.keycloak.client.payload.token.TokenResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TokenClient {

    @RequestLine("POST")
    @Headers({ "Authorization: {token_type} {access_token}", "Content-Type: application/x-www-form-urlencoded" })
    TokenResponse post(@Param("token_type") String tokenType, @Param("access_token") String accessToken,
            Map<String, ?> form);

    @RequestLine("POST")
    @Headers({ "Content-Type: application/x-www-form-urlencoded" })
    TokenResponse post(Map<String, ?> form);
}
