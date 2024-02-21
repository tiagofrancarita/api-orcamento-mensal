package br.com.franca.api.orcamento.mensal.core.repositorys;

import br.com.franca.api.orcamento.mensal.core.entities.CartaoCreditoEntity;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface CartaoCreditoRepository extends JpaRepository<CartaoCreditoEntity, Long> {

    CartaoCreditoEntity findByNumeroCartao(String numeroCartao);

    @Query("SELECT cc FROM CartaoCreditoEntity cc WHERE cc.numeroCartao LIKE %:numeroCartao%")
    public List<CartaoCreditoEntity> buscarCartaoPorNumero(String numeroCartao);

    @NonNull
    @Query("SELECT cc FROM CartaoCreditoEntity cc WHERE cc.ativo = true and cc.status = 'Valido' ")
    List<CartaoCreditoEntity> findByStatusAtivoTrue();

    CartaoCreditoEntity findBynomeTitular(String nomeTitular);

    CartaoCreditoEntity findByDataVencimento(LocalDate dataVencimento);

    CartaoCreditoEntity findByStatus(String status);

    @Query("SELECT cc FROM CartaoCreditoEntity cc WHERE cc.dataVencimento BETWEEN :dataInicio AND :dataFim")
    List<CartaoCreditoEntity> findByDataVencimentoBetween(LocalDate dataInicio, LocalDate dataFim);

}
