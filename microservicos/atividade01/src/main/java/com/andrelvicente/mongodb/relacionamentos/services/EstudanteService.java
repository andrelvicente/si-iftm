package com.andrelvicente.mongodb.relacionamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.andrelvicente.mongodb.relacionamentos.models.Curso;
import com.andrelvicente.mongodb.relacionamentos.models.Estudante;
import com.andrelvicente.mongodb.relacionamentos.repositories.CursoRepository;
import com.andrelvicente.mongodb.relacionamentos.repositories.EstudanteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;

    public Estudante criarEstudante(Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    public Estudante criarEstudanteComCursos(Estudante estudante, List<String> cursoIds) {
        // Verificar se todos os cursos existem
        List<Curso> cursos = cursoRepository.findAllById(cursoIds);
        if (cursos.size() != cursoIds.size()) {
            throw new RuntimeException("Alguns cursos não foram encontrados");
        }
        
        // Definir os IDs dos cursos no estudante
        estudante.setCursoIds(cursoIds);
        
        // Salvar o estudante
        Estudante estudanteSalvo = estudanteRepository.save(estudante);
        
        // Atualizar os cursos para incluir este estudante
        for (Curso curso : cursos) {
            if (curso.getEstudanteIds() == null) {
                curso.setEstudanteIds(List.of(estudanteSalvo.getId()));
            } else if (!curso.getEstudanteIds().contains(estudanteSalvo.getId())) {
                curso.getEstudanteIds().add(estudanteSalvo.getId());
            }
            cursoRepository.save(curso);
        }
        
        return estudanteSalvo;
    }

    public Estudante adicionarCursoAoEstudante(String estudanteId, String cursoId) {
        Optional<Estudante> estudanteOpt = estudanteRepository.findById(estudanteId);
        Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);
        
        if (estudanteOpt.isEmpty() || cursoOpt.isEmpty()) {
            throw new RuntimeException("Estudante ou curso não encontrado");
        }
        
        Estudante estudante = estudanteOpt.get();
        Curso curso = cursoOpt.get();
        
        // Adicionar curso ao estudante
        if (estudante.getCursoIds() == null) {
            estudante.setCursoIds(List.of(cursoId));
        } else if (!estudante.getCursoIds().contains(cursoId)) {
            estudante.getCursoIds().add(cursoId);
        }
        
        // Adicionar estudante ao curso
        if (curso.getEstudanteIds() == null) {
            curso.setEstudanteIds(List.of(estudanteId));
        } else if (!curso.getEstudanteIds().contains(estudanteId)) {
            curso.getEstudanteIds().add(estudanteId);
        }
        
        // Salvar ambos
        cursoRepository.save(curso);
        return estudanteRepository.save(estudante);
    }

    public Estudante removerCursoDoEstudante(String estudanteId, String cursoId) {
        Optional<Estudante> estudanteOpt = estudanteRepository.findById(estudanteId);
        Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);
        
        if (estudanteOpt.isEmpty() || cursoOpt.isEmpty()) {
            throw new RuntimeException("Estudante ou curso não encontrado");
        }
        
        Estudante estudante = estudanteOpt.get();
        Curso curso = cursoOpt.get();
        
        // Remover curso do estudante
        if (estudante.getCursoIds() != null) {
            estudante.getCursoIds().removeIf(id -> id.equals(cursoId));
        }
        
        // Remover estudante do curso
        if (curso.getEstudanteIds() != null) {
            curso.getEstudanteIds().removeIf(id -> id.equals(estudanteId));
        }
        
        // Salvar ambos
        cursoRepository.save(curso);
        return estudanteRepository.save(estudante);
    }

    public List<Estudante> listarTodosEstudantes() {
        return estudanteRepository.findAll();
    }

    public Optional<Estudante> buscarEstudantePorId(String id) {
        return estudanteRepository.findById(id);
    }

    public Estudante atualizarEstudante(Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    public void removerEstudante(String id) {
        estudanteRepository.deleteById(id);
    }
}