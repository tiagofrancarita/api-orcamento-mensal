package br.com.franca.api.orcamento.mensal.service;

import br.com.franca.api.orcamento.mensal.entities.CartaoCreditoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartaoCreditoService {


    List<CartaoCreditoEntity> listarCartao();
    CartaoCreditoEntity salvarCartao(CartaoCreditoEntity cartaoCreditoEntity);
    CartaoCreditoEntity atualizar(CartaoCreditoEntity cartaoCreditoEntity);
    ResponseEntity<CartaoCreditoEntity> deletarPorId(Long id);



}
