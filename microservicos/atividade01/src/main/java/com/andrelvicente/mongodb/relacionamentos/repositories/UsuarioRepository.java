package com.andrelvicente.mongodb.relacionamentos.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.andrelvicente.mongodb.relacionamentos.models.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
