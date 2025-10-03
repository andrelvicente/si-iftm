package com.andrelvicente.mongodb.relacionamentos.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuario")
public class Usuario {

    @Id
    private String id;

    private String nome;
    private String email;

    @DBRef
    private Perfil perfil;

    @DBRef
    private List<Postagem> postagens;
}
