package br.usp.poli.keycloak.client.payload.session;

import java.util.Map;

import br.usp.poli.keycloak.client.payload.BaseKeycloakRequest;

public class SessionLogoutRequest extends BaseKeycloakRequest {

    private String refreshToken;

    public SessionLogoutRequest(BaseKeycloakRequest baseRequest) {
        super(baseRequest.getClientId(), baseRequest.getClientSecret());
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public Map<String, String> toForm() {
        Map<String, String> form = super.toForm();
        form.put("refresh_token", refreshToken);
        return form;
    }
}