package br.usp.poli.keycloak.client.enums;

public enum TokenType {

    BEARER("Bearer"),
    ID("ID");

    private String descricao;

    private TokenType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getName() {
        return name();
    }
}
