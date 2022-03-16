package br.usp.poli.keycloak.client.payload.token;

import br.usp.poli.keycloak.client.payload.BaseKeycloakResponse;
import lombok.Data;

@Data
public class TokenResponse implements BaseKeycloakResponse {
    
    private Boolean upgraded;

    private String access_token;
    
    private String expires_in;
    
    private String refresh_expires_in;
    
    private String refresh_token;
    
    private String token_type;
    
}