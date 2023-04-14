package com.curso.spring.repository;

import com.curso.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(value = "select u from User u where trim(u.nome) like %?1%") // Como so tem 1 parametro, passamos apenas o numero 1
    List<User> buscarPorNome(String name);
}
