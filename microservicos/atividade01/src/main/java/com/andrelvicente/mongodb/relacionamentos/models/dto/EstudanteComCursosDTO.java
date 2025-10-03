package com.andrelvicente.mongodb.relacionamentos.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudanteComCursosDTO {
    private String nome;
    private String matricula;
    private List<String> cursoIds;
}
