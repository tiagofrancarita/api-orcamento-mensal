package br.com.franca.api.orcamento.mensal.core.service;

import br.com.franca.api.orcamento.mensal.core.entities.CartaoCreditoEntity;
import br.com.franca.api.orcamento.mensal.core.repositorys.CartaoCreditoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartaoValidatorImpl implements CartaoValidator {

    public Logger log = LoggerFactory.getLogger(CartaoCreditoServiceImpl.class);

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Override
    public void validarCartao(CartaoCreditoEntity cartaoCreditoEntity) {
        if (cartaoCreditoEntity.getId() != null) {
            log.error("============ ERRO AO CADASTRAR, CODIGO JÁ EXISTE ============");
            throw new RuntimeException("ERRO AO CADASTRAR, CODIGO JÁ EXISTE");
        }
        if (cartaoCreditoEntity.getNomeTitular() == null || cartaoCreditoEntity.getNomeTitular().isEmpty()) {
            log.error("============ ERRO AO CADASTRAR, NOME DO TITULAR NÃO INFORMADO ============");
            throw new RuntimeException("Nome do titular do cartão de crédito não informado.");
        }
        if (cartaoCreditoEntity.getNumeroCartao() == null || cartaoCreditoEntity.getNumeroCartao().isEmpty()) {
            log.error("============ ERRO AO CADASTRAR, NUMERO DO CARTÃO NÃO INFORMADO ============");
            throw new RuntimeException("Número do cartão de crédito não informado.");
        }
        if (cartaoCreditoEntity.getDataVencimento() == null) {
            log.error("============ ERRO AO CADASTRAR, DATA DE VALIDADE NÃO INFORMADA ============");
            throw new RuntimeException("Validade do cartão de crédito não informado.");
        }

        if (cartaoCreditoEntity.getNumeroCartao() == null){
            log.info("============ VERIFICANDO O NUMERO DO CARTÃO NA BASE DE DADOS ============");
            List<CartaoCreditoEntity> cartoesCadastrados = cartaoCreditoRepository.buscarCartaoPorNumero(cartaoCreditoEntity.getNumeroCartao());
            if (cartoesCadastrados.isEmpty()) {
                log.error("============ ERRO AO CADASTRAR, NUMERO DE CARTÃO JÁ EXISTE ============");
                throw new RuntimeException("ERRO AO CADASTRAR, NUMERO DE CARTÃO JÁ EXISTE");
            }
        }
    }
}
