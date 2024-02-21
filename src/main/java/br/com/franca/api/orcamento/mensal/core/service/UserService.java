package br.com.franca.api.orcamento.mensal.core.service;

import br.com.franca.api.orcamento.mensal.core.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserEntity salvar(UserEntity userEntity);
}
