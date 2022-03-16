package br.usp.poli.keycloak.client.endpoint;

import org.springframework.web.bind.annotation.RequestParam;

import feign.Param;
import feign.RequestLine;

public interface OidcTokenLogoutClient {

    @RequestLine("GET ?id_token_hint={oidcToken}")
    String get(@Param("oidcToken") String oidcToken);
}
