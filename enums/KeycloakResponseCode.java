package br.usp.poli.keycloak.client.enums;

public enum KeycloakResponseCode {

    OK("0", "Transacao OK");

    private String value;
    private String descricao;

    private KeycloakResponseCode(String value, String descricao) {
        this.value = value;
        this.descricao = descricao;
    }

    public String getValue() {
        return value;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getName() {
        return name();
    }
}
