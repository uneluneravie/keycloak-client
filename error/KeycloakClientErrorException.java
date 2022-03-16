package br.usp.poli.keycloak.client.error;

public class KeycloakClientErrorException extends RuntimeException {
    private int httpResponseCode = 200;

    private static final long serialVersionUID = 1L;

    public KeycloakClientErrorException(String message) {
        super(message);
    }

    public KeycloakClientErrorException(String message, int httpResponseCode) {
        super(message);
        this.httpResponseCode = httpResponseCode;
    }

    public int getHttpResponseCode() {
        return this.httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }
}
