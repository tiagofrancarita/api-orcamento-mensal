package br.com.franca.api.orcamento.mensal.core.service;

import br.com.franca.api.orcamento.mensal.core.entities.UserEntity;
import br.com.franca.api.orcamento.mensal.core.repositorys.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity salvar(UserEntity userEntity) {

        try {
            log.info("============ INICIANDO PROCESSO PARA SALVAR USUÁRIO(A) ============");

            validateUser(userEntity);

            log.info("============ DADOS INFORMADOS VALIDADOS COM SUCESSO ============");


            log.info("============ INICIADO PROCESSO DE GERAÇÃO E CODIFICAÇÃO DA SENHA ============");
            userEntity.setSenha(passwordEncoder().encode(userEntity.getSenha()));
            userEntity.setConfirmaSenha(passwordEncoder().encode(userEntity.getConfirmaSenha()));

            log.info("============ PROCESSO DE GERAÇÃO E CODIFICAÇÃO DA SENHA REALIZADO COM SUCESSO============");

            UserEntity userSalvo = userRepository.save(userEntity);


            log.info("============ USUÁRIO(A) SALVO COM SUCESSO============" + "ID: " + userSalvo.getId());

            return userSalvo;

        } catch (RuntimeException e) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A) ============");
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar usuário(a)");

        }
    }

    public void validateUser(UserEntity user) {

        UserEntity existeUsername = userRepository.findByUsername(user.getUsername());
        UserEntity existeEmail = userRepository.findByEmail(user.getEmail());

        if (existeEmail != null) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), EMAIL JÁ EXISTENTE ============");
            throw new RuntimeException("Email já existe");
        }

        if (existeUsername != null) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), USUÁRIO(A) JÁ EXISTENTE ============");
            throw new RuntimeException("Usuário já existe");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), USERNAME NÃO PODE SER NULO ============");
            throw new RuntimeException("Usuário não pode ser nulo");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), E-MAIL NÃO PODE SER NULO ============");
            throw new RuntimeException("E-mail não pode ser nulo");
        }

        if (user.getSenha() == null || user.getSenha().isEmpty()) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), SENHA NÃO PODE SER NULO ============");
            throw new RuntimeException("Senha não pode ser nula");
        }

        if (user.getConfirmaSenha() == null || user.getConfirmaSenha().isEmpty()) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), CONFIRMAÇÃO DE SENHA NÃO PODE SER NULO ============");
            throw new RuntimeException("Senha não pode ser nula");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), NOME NÃO PODE SER NULO ============");
            throw new RuntimeException("Nome não pode ser nulo");
        }

        if (user.getName().length() < 3) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), NOME NÃO PODE TER MENOS QUE 3 CARACTERES ============");
            throw new RuntimeException("Nome deve ter no mínimo 3 caracteres");
        }

        if (user.getEmail().contains("@") == false) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), E-MAIL INVÁLIDO ============");
            throw new RuntimeException("E-mail inválido");
        }

        if (user.getSenha().length() < 8 && user.getSenha().contains("@0123456789!@#$%¨&*()-+=[]}{?<>") == false) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), A SENHA DEVE TER NO MÍNIMO 8 CARACTERES E CONTER LETRAS NUMEROS E CARACTERES ESPECIAIS ============");
            throw new RuntimeException("Senha deve ter no mínimo 8 caracteres e conter letras,números e caracteres especiais");
        }

        if (user.getSenha().equals(user.getConfirmaSenha()) == false) {
            log.error("============ ERRO AO SALVAR USUÁRIO(A), AS SENHAS NÃO CONFEREM ============");
            throw new RuntimeException("Senhas não conferem");
        }
    }
}