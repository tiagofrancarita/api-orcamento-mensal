package br.com.franca.api.orcamento.mensal.controller;

import br.com.franca.api.orcamento.mensal.entities.UserEntity;
import br.com.franca.api.orcamento.mensal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {


    private final UserService userService;

    @PostMapping("/salvarUser")
    public UserEntity salvarUser(@RequestBody UserEntity userEntity) {

        userService.salvar(userEntity);

        return userEntity;

    }


}