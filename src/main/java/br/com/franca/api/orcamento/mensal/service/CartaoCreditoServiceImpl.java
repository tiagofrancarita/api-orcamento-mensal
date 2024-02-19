package br.com.franca.api.orcamento.mensal.service;

import br.com.franca.api.orcamento.mensal.entities.CartaoCreditoEntity;
import br.com.franca.api.orcamento.mensal.entities.UserEntity;
import br.com.franca.api.orcamento.mensal.exceptions.CartaoNotFoundException;
import br.com.franca.api.orcamento.mensal.exceptions.CartaoValidationException;
import br.com.franca.api.orcamento.mensal.repository.CartaoCreditoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.List;

@Service
public class CartaoCreditoServiceImpl implements CartaoCreditoService {

    private static final String MSG_SUCESSO_DELTE = "Registro excluido com sucesso..";
    private static final String MSG_FALHA = "Operação não realizada.";

    public Logger log = LoggerFactory.getLogger(CartaoCreditoServiceImpl.class);


    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Validator validator;

    @Autowired
    private CartaoValidator cartaoValidator;


    @Override
    public List<CartaoCreditoEntity> listarCartao() {

        log.info("============ INICIANDO PROCESSO PARA LISTAR OS CARTÕES CADASTRADOS ============");

        List<CartaoCreditoEntity> cartoesAtivos = cartaoCreditoRepository.findByStatusAtivoTrue();

        if (cartoesAtivos == null || cartoesAtivos.isEmpty()) {
            log.error("============ Nenhum cartão ativo encontrado. ============");
            throw new CartaoNotFoundException("Não há cartões de crédito ativos/valido cadastrados.");
        }

        log.info("============ Fim do processo listar cartões de crédito, processo realizado com sucesso.============");
        return cartoesAtivos;

    }

    @Override
    public CartaoCreditoEntity salvarCartao(CartaoCreditoEntity cartaoCreditoEntity) {

        log.info("============ INICIANDO PROCESSO PARA CADASTRAR CARTÃO ============");

        log.info("============ VALIDANDO DADOS INFORMADOS PARA CADASTRAR CARTÃO ============");
        cartaoValidator.validarCartao(cartaoCreditoEntity);


        log.info("============ DADOS VALIDADOS COM SUCESSO ============");

        cartaoCreditoEntity  = cartaoCreditoRepository.save(cartaoCreditoEntity);
        log.info("============ FINALIZANDO PROCESSO DE CADASTRO DE CARTÃO ============");
        log.info("============ CARTÃO CADASTRADO COM SUCESSO ============");
        return cartaoCreditoEntity;
    }




    @Override
    public CartaoCreditoEntity atualizar(CartaoCreditoEntity cartaoCreditoEntity) {
        return null;
    }

    @Override
    public ResponseEntity<CartaoCreditoEntity> deletarPorId(Long id) {

        log.info("============ INICIANDO PROCESSO PARA EXCLUSÃO DO CARTÃO ============");

        try {
            cartaoCreditoRepository.deleteById(id);
            log.info("============ CARTÃO EXCLUIDO COM SUCESSO ============");
            return new ResponseEntity<CartaoCreditoEntity>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("============ ERRO AO EXCLUIR O CARTÃO ============");
            return new ResponseEntity<CartaoCreditoEntity>(HttpStatus.NOT_FOUND);
        }
    }
}