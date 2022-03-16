package br.usp.poli.keycloak.client.enums;

public enum GrantType {

    PASSWORD("password"),
    UMA_TICKET("urn:ietf:params:oauth:grant-type:uma-ticket");

    private String descricao;

    private GrantType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getName() {
        return name();
    }
}
