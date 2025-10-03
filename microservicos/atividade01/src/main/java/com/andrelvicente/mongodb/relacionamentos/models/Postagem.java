package com.andrelvicente.mongodb.relacionamentos.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "postagem")
public class Postagem {

    @Id
    private String id;

    private String titulo;
    private String conteudo;
    private LocalDateTime dataCriacao;
}
