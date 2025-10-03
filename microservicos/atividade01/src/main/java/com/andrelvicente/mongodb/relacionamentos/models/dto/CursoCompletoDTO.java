package com.andrelvicente.mongodb.relacionamentos.models.dto;

import java.util.List;

import com.andrelvicente.mongodb.relacionamentos.models.Curso;
import com.andrelvicente.mongodb.relacionamentos.models.Estudante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoCompletoDTO {
    private String id;
    private String nome;
    private String descricao;
    private List<Estudante> estudantes;

    public static CursoCompletoDTO fromCurso(Curso curso, List<Estudante> estudantes) {
        return new CursoCompletoDTO(
            curso.getId(),
            curso.getNome(),
            curso.getDescricao(),
            estudantes
        );
    }
}
