package br.usp.poli.keycloak.client;

import br.usp.poli.keycloak.client.endpoint.OidcTokenLogoutClient;
import br.usp.poli.keycloak.client.endpoint.SessionLogoutClient;
import br.usp.poli.keycloak.client.endpoint.TokenClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakClient {

    private TokenClient tokenClient;
    
    private SessionLogoutClient logoutClient;

    private OidcTokenLogoutClient oidcTokenLogoutClient;
}
