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
public class EstudanteCompletoDTO {
    private String id;
    private String nome;
    private String matricula;
    private List<Curso> cursos;

    public static EstudanteCompletoDTO fromEstudante(Estudante estudante, List<Curso> cursos) {
        return new EstudanteCompletoDTO(
            estudante.getId(),
            estudante.getNome(),
            estudante.getMatricula(),
            cursos
        );
    }
}
