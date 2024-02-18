package br.com.franca.api.orcamento.mensal.repository;

import br.com.franca.api.orcamento.mensal.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);


    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles where u.username = :username")
    UserEntity findByUsernameFetchRoles(String username);
}