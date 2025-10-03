package com.andrelvicente.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.andrelvicente.mongodb.relacionamentos.models.Postagem;

@Repository
public interface PostagemRepository extends MongoRepository<Postagem, String> {
}
