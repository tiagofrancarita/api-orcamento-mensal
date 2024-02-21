package br.com.franca.api.orcamento.mensal.core.configs;


import br.com.franca.api.orcamento.mensal.core.entities.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(UserEntity user){
        this.username = user.getUsername();
        this.password = user.getSenha();

        this.authorities = user.getRoles().stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_".concat(role.getName()));
        }).collect(Collectors.toList());
    }

    public static UserPrincipal create(UserEntity user){
        return new UserPrincipal(user);
    }
}