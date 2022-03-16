package br.usp.poli.keycloak.client.payload.token;

import java.util.Map;

import br.usp.poli.keycloak.client.enums.GrantType;
import br.usp.poli.keycloak.client.payload.BaseKeycloakRequest;

public class TokenRequest extends BaseKeycloakRequest {

    private String username;
    
    private String password;
    
    private String grantType;
    
    public TokenRequest(BaseKeycloakRequest baseRequest) {
        super(baseRequest.getClientId(), baseRequest.getClientSecret());
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setGrantType(GrantType grantType) {
        this.grantType = grantType != null ? grantType.getDescricao() : null;
    }

    public Map<String, String> toForm() {
        Map<String, String> form = super.toForm();
        form.put("username", username);
        form.put("password", password);
        form.put("grant_type", grantType);
        return form;
    }
}