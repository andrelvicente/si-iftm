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
public class CursoService {

    private final CursoRepository cursoRepository;
    private final EstudanteRepository estudanteRepository;

    public Curso criarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso criarCursoComEstudantes(Curso curso, List<String> estudanteIds) {
        // Verificar se todos os estudantes existem
        List<Estudante> estudantes = estudanteRepository.findAllById(estudanteIds);
        if (estudantes.size() != estudanteIds.size()) {
            throw new RuntimeException("Alguns estudantes não foram encontrados");
        }
        
        // Definir os IDs dos estudantes no curso
        curso.setEstudanteIds(estudanteIds);
        
        // Salvar o curso
        Curso cursoSalvo = cursoRepository.save(curso);
        
        // Atualizar os estudantes para incluir este curso
        for (Estudante estudante : estudantes) {
            if (estudante.getCursoIds() == null) {
                estudante.setCursoIds(List.of(cursoSalvo.getId()));
            } else if (!estudante.getCursoIds().contains(cursoSalvo.getId())) {
                estudante.getCursoIds().add(cursoSalvo.getId());
            }
            estudanteRepository.save(estudante);
        }
        
        return cursoSalvo;
    }

    public Curso adicionarEstudanteAoCurso(String cursoId, String estudanteId) {
        Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);
        Optional<Estudante> estudanteOpt = estudanteRepository.findById(estudanteId);
        
        if (cursoOpt.isEmpty() || estudanteOpt.isEmpty()) {
            throw new RuntimeException("Curso ou estudante não encontrado");
        }
        
        Curso curso = cursoOpt.get();
        Estudante estudante = estudanteOpt.get();
        
        // Adicionar estudante ao curso
        if (curso.getEstudanteIds() == null) {
            curso.setEstudanteIds(List.of(estudanteId));
        } else if (!curso.getEstudanteIds().contains(estudanteId)) {
            curso.getEstudanteIds().add(estudanteId);
        }
        
        // Adicionar curso ao estudante
        if (estudante.getCursoIds() == null) {
            estudante.setCursoIds(List.of(cursoId));
        } else if (!estudante.getCursoIds().contains(cursoId)) {
            estudante.getCursoIds().add(cursoId);
        }
        
        // Salvar ambos
        estudanteRepository.save(estudante);
        return cursoRepository.save(curso);
    }

    public Curso removerEstudanteDoCurso(String cursoId, String estudanteId) {
        Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);
        Optional<Estudante> estudanteOpt = estudanteRepository.findById(estudanteId);
        
        if (cursoOpt.isEmpty() || estudanteOpt.isEmpty()) {
            throw new RuntimeException("Curso ou estudante não encontrado");
        }
        
        Curso curso = cursoOpt.get();
        Estudante estudante = estudanteOpt.get();
        
        // Remover estudante do curso
        if (curso.getEstudanteIds() != null) {
            curso.getEstudanteIds().removeIf(id -> id.equals(estudanteId));
        }
        
        // Remover curso do estudante
        if (estudante.getCursoIds() != null) {
            estudante.getCursoIds().removeIf(id -> id.equals(cursoId));
        }
        
        // Salvar ambos
        estudanteRepository.save(estudante);
        return cursoRepository.save(curso);
    }

    public List<Curso> listarTodosCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarCursoPorId(String id) {
        return cursoRepository.findById(id);
    }

    public Curso atualizarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void removerCurso(String id) {
        cursoRepository.deleteById(id);
    }
}