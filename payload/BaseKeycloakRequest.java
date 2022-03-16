package br.usp.poli.keycloak.client.payload;

import java.util.HashMap;
import java.util.Map;

public class BaseKeycloakRequest {

    protected String clientId;

    protected String clientSecret;

    public BaseKeycloakRequest(String clientId, String clientSecret) {
        super();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    
    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Map<String, String> toForm() {
        Map<String, String> form = new HashMap<>();
        form.put("audience", clientId);
        form.put("client_id", clientId);
        form.put("client_secret", clientSecret);
        return form;
    }
}
