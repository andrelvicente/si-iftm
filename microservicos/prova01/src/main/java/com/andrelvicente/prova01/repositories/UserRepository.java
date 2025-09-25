package com.andrelvicente.prova01.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.andrelvicente.prova01.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByCpf(String cpf);
    List<User> queryByNomeLike(String nome);
    Page<User> findAll(Pageable page);
}
