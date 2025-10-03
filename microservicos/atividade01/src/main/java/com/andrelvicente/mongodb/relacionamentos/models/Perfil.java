package com.andrelvicente.mongodb.relacionamentos.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "perfil")
public class Perfil {

    @Id
    private String id;

    private String bio;
    private String site;
    private String imagem;
}
