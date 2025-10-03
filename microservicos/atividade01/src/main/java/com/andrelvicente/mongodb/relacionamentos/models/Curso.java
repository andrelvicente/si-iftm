package com.andrelvicente.mongodb.relacionamentos.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "curso")
public class Curso {

    @Id
    private String id;

    private String nome;
    private String descricao;

    private List<String> estudanteIds;
}
