package br.com.franca.api.orcamento.mensal.core.controllers;

import br.com.franca.api.orcamento.mensal.core.entities.UserEntity;
import br.com.franca.api.orcamento.mensal.core.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "entrypoint-users", description = "entrypoint para gerenciamento de usuarios")
public class UsersController {


    private final UserService userService;

    @PostMapping("/salvarUser")
    public UserEntity salvarUser(@RequestBody UserEntity userEntity) {

        userService.salvar(userEntity);

        return userEntity;

    }


}
