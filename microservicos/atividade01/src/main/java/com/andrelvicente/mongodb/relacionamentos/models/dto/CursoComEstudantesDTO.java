package com.andrelvicente.mongodb.relacionamentos.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoComEstudantesDTO {
    private String nome;
    private String descricao;
    private List<String> estudanteIds;
}
