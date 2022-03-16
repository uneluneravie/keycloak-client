package br.usp.poli.keycloak.client.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakErrorResponse {
    private String error;
    private String error_description;
}
