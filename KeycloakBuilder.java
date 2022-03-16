package br.usp.poli.keycloak.client;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import br.usp.poli.keycloak.client.endpoint.OidcTokenLogoutClient;
import br.usp.poli.keycloak.client.endpoint.SessionLogoutClient;
import br.usp.poli.keycloak.client.endpoint.TokenClient;
import br.usp.poli.keycloak.client.error.KeycloakErrorDecoder;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

public class KeycloakBuilder {

    private CloseableHttpClient httpClient;
    private String url;
    private String realm;
    private String clientId;
    private String clientSecret;
    
    // Configuracao para proxy, se necessario: 
    // https://stackoverflow.com/questions/29385224/org-apache-http-impl-client-closeablehttpclient-proxy-authentication

    public static KeycloakBuilder create() {
        return new KeycloakBuilder();
    }
    
    public KeycloakBuilder url(String url) {
        this.url = url;
        return this;
    }
    
    public KeycloakBuilder realm(String realm) {
        this.realm = realm;
        return this;
    }
    
    public KeycloakBuilder clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }
    
    public KeycloakBuilder clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public KeycloakClient build() {
        if (this.url == null) {
            throw new IllegalArgumentException("KeycloakClient - missing required configuration: URL.");
        }
        if (this.realm == null) {
            throw new IllegalArgumentException("KeycloakClient - Missing required configuration: realm.");
        }
        if (this.clientId == null) {
            throw new IllegalArgumentException("KeycloakClient - Missing required configuration: client ID.");
        }
        if (this.clientSecret == null) {
            throw new IllegalArgumentException("KeycloakClient - Missing required configuration: client secret.");
        }

        this.httpClient = HttpClients.custom().setSSLHostnameVerifier((HostnameVerifier) NoopHostnameVerifier.INSTANCE).build();

        TokenClient tokenClient = getClient(TokenClient.class, "/token", ContentType.APPLICATION_FORM_URLENCODED);
        
        SessionLogoutClient logoutClient = getClient(SessionLogoutClient.class, "/logout", ContentType.APPLICATION_FORM_URLENCODED);
        
        OidcTokenLogoutClient oidcTokenLogoutClient = getClient(OidcTokenLogoutClient.class, "/logout");

        return KeycloakClient.builder()
                .tokenClient(tokenClient)
                .logoutClient(logoutClient)
                .oidcTokenLogoutClient(oidcTokenLogoutClient)
                .build();
    }

    public <T> T build(Class<T> clazz, String resource, ContentType contentType) {
        return getClient(clazz, resource, contentType);
    }

    private <T> T getClient(Class<T> clazz, String resource, ContentType contentType) {
        T testeClient = (T) Feign
                .builder()
                .logger((Logger) new Slf4jLogger(KeycloakClient.class))
                .logLevel(Logger.Level.FULL)
                .errorDecoder((ErrorDecoder) new KeycloakErrorDecoder())
                .decoder((Decoder) new GsonDecoder())
                .encoder(encoder(contentType))
                .client((Client) new ApacheHttpClient((HttpClient) this.httpClient))
                .requestInterceptor(requestInterceptor(contentType))
                .target(clazz, this.url + "/auth/realms/" + this.realm + "/protocol/openid-connect" + resource);
        return testeClient;
    }
    
    private <T> T getClient(Class<T> clazz, String resource) {
        T testeClient = (T) Feign
                .builder()
                .logger((Logger) new Slf4jLogger(KeycloakClient.class))
                .logLevel(Logger.Level.FULL)
                .errorDecoder((ErrorDecoder) new KeycloakErrorDecoder())
                .decoder((Decoder) new GsonDecoder())
                .client((Client) new ApacheHttpClient((HttpClient) this.httpClient))
                .target(clazz, this.url + "/auth/realms/" + this.realm + "/protocol/openid-connect" + resource);
        return testeClient;
    }

    private Encoder encoder(ContentType contentType) {

        if (ContentType.APPLICATION_JSON.equals(contentType)) {
            return new GsonEncoder();
        }
        if (ContentType.APPLICATION_FORM_URLENCODED.equals(contentType)) {
            return (Encoder) new FormEncoder(new JacksonEncoder());
        }
        return null;
    }

    private RequestInterceptor requestInterceptor(ContentType contentType) {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", new String[] { contentType.getMimeType() });
        };
    }

}
