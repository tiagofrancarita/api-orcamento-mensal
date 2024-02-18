package br.com.franca.api.orcamento.mensal.entities;

import jakarta.persistence.*;
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

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "confirma_senha")
    private String confirmaSenha;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "expiracao_senha")
    private LocalDate expiracaoSenha;

    @ManyToMany
    private List<RoleEntity> roles;
}
