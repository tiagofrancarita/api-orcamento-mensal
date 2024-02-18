package br.com.franca.api.orcamento.mensal.service;

import br.com.franca.api.orcamento.mensal.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserEntity salvar(UserEntity userEntity);
}
