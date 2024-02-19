package br.com.franca.api.orcamento.mensal.entities;

import br.com.franca.api.orcamento.mensal.enums.StatusCartaoCredito;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Data
@Table(name = "cartao_credito")
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCreditoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O número do cartão é obrigatório")
    @Column(name = "numero_cartao", nullable = false, columnDefinition = "VARCHAR(16)")
    private String numeroCartao;

    @NotBlank(message = "A bandeira do cartão é obrigatória")
    @Column(name = "bandeira", nullable = false, columnDefinition = "VARCHAR(50)")
    private String bandeira;

    @NotBlank(message = "O nome do titular do cartão é obrigatório")
    @Column(name = "nome_titular", nullable = false, columnDefinition = "VARCHAR(255)")
    private String nomeTitular;

    @Column(name = "dt_vencimento", nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private LocalDate dataVencimento;


    @Column(name = "ativo", nullable = false, columnDefinition = "BOOLEAN")
    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCartaoCredito status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartaoCreditoEntity cartaoCredito)) return false;
        return Objects.equals(getId(), cartaoCredito.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}