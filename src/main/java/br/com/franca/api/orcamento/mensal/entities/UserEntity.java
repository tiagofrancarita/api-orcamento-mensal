package br.com.franca.api.orcamento.mensal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "O username é obrigatório")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "O e-mail é obrigatório")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Column(name = "senha")
    private String senha;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    @Column(name = "confirma_senha")
    private String confirmaSenha;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "expiracao_senha")
    private LocalDate expiracaoSenha;

    @ManyToMany
    private List<RoleEntity> roles;
}
