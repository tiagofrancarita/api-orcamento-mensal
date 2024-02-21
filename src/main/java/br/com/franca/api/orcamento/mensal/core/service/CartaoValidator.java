package br.com.franca.api.orcamento.mensal.core.service;

import br.com.franca.api.orcamento.mensal.core.entities.CartaoCreditoEntity;

public interface CartaoValidator {

    void validarCartao(CartaoCreditoEntity cartaoCreditoEntity);

}
