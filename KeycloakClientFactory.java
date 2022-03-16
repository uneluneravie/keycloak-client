package br.usp.poli.keycloak.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.usp.poli.keycloak.client.payload.BaseKeycloakRequest;

@Service
public class KeycloakClientFactory {

    private KeycloakClient client;

    private KeycloakBuilder builder;
    
    @Value("${keycloak.client.url}")
    private String url;
    @Value("${keycloak.client.realm}")
    private String realm;
    @Value("${keycloak.client.clientId}")
    private String clientId;
    @Value("${keycloak.client.clientSecret}")
    private String clientSecret;
    
    private KeycloakClientFactory() {
    }
    
    public BaseKeycloakRequest getBaseRequest() {
        return new BaseKeycloakRequest(clientId, clientSecret);
    }

    public KeycloakClient getClient() {
        if (client != null) {
            return client;
        }

        client = getBuilder().build();
        return client;
    }

    public KeycloakBuilder getBuilder() {
        if (builder != null) {
            return builder;
        }


        builder = KeycloakBuilder.create()
                .url(url)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret);

        return builder;
    }

}
