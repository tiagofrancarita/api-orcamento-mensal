package br.com.franca.api.orcamento.mensal.core.enums;

public enum StatusCartaoCredito {

    VENCIDO ("Vencido"),
    VALIDO ("Valido");

    private String descricao;

    StatusCartaoCredito(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
