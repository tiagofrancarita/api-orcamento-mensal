package br.com.franca.api.orcamento.mensal.controller;


import br.com.franca.api.orcamento.mensal.entities.CartaoCreditoEntity;
import br.com.franca.api.orcamento.mensal.service.CartaoCreditoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe responsavel por controlar as requisicoes de cartao de credito
 * @since 18/02/2024
 * @version 1.0
 * @Author Tiago França
 */


@RestController
@RequestMapping("/cartoesCredito")
@Tag(name = "entrypoint-cartao-credito", description = "entrypoint para gerenciamento de cartoes de credito")
public class CartaoCreditoController {

    @Autowired
    private CartaoCreditoServiceImpl cartaoCreditoServiceImpl;

    private Logger log = LoggerFactory.getLogger(CartaoCreditoController.class);

    /**
     * Metodo responsavel por listar todas as pessoas cadastradas no banco de dados
     * @return
     */

    @Operation(summary = "Metodo responsavel por listar todas as pessoas cadastradas no banco de dados")
    @GetMapping("/listarCartaoCredito")
    public List<CartaoCreditoEntity> listarCartaoCredito(){

        return cartaoCreditoServiceImpl.listarCartao();
    }

    /**
     * Metodo responsavel cadastrar um cartão de credito
     * @param cartaoCredito
     * @return
     */
    @Operation(summary = "Metodo responsavel cadastrar um cartão de credito")
    @PostMapping("/salvarCartao")
    public CartaoCreditoEntity salvarCartao(@RequestBody CartaoCreditoEntity cartaoCredito){

        return cartaoCreditoServiceImpl.salvarCartao(cartaoCredito);
    }
}