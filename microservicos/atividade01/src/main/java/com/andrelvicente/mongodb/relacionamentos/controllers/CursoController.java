package com.andrelvicente.mongodb.relacionamentos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrelvicente.mongodb.relacionamentos.models.Curso;
import com.andrelvicente.mongodb.relacionamentos.models.Estudante;
import com.andrelvicente.mongodb.relacionamentos.models.dto.CursoComEstudantesDTO;
import com.andrelvicente.mongodb.relacionamentos.models.dto.CursoCompletoDTO;
import com.andrelvicente.mongodb.relacionamentos.repositories.EstudanteRepository;
import com.andrelvicente.mongodb.relacionamentos.services.CursoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;
    private final EstudanteRepository estudanteRepository;

    @PostMapping
    public ResponseEntity<Curso> criarCurso(@RequestBody Curso curso) {
        Curso cursoSalvo = cursoService.criarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
    }

    @PostMapping("/com-estudantes")
    public ResponseEntity<Curso> criarCursoComEstudantes(@RequestBody CursoComEstudantesDTO cursoDTO) {
        try {
            Curso curso = new Curso();
            curso.setNome(cursoDTO.getNome());
            curso.setDescricao(cursoDTO.getDescricao());
            
            Curso cursoSalvo = cursoService.criarCursoComEstudantes(curso, cursoDTO.getEstudanteIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{cursoId}/estudantes/{estudanteId}")
    public ResponseEntity<Curso> adicionarEstudanteAoCurso(@PathVariable String cursoId, @PathVariable String estudanteId) {
        try {
            Curso curso = cursoService.adicionarEstudanteAoCurso(cursoId, estudanteId);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{cursoId}/estudantes/{estudanteId}")
    public ResponseEntity<Curso> removerEstudanteDoCurso(@PathVariable String cursoId, @PathVariable String estudanteId) {
        try {
            Curso curso = cursoService.removerEstudanteDoCurso(cursoId, estudanteId);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarTodosCursos() {
        List<Curso> cursos = cursoService.listarTodosCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<CursoCompletoDTO>> listarTodosCursosCompletos() {
        List<Curso> cursos = cursoService.listarTodosCursos();
        List<CursoCompletoDTO> cursosCompletos = cursos.stream()
            .map(curso -> {
                List<Estudante> estudantes = curso.getEstudanteIds() != null 
                    ? estudanteRepository.findAllById(curso.getEstudanteIds())
                    : List.of();
                return CursoCompletoDTO.fromCurso(curso, estudantes);
            })
            .toList();
        return ResponseEntity.ok(cursosCompletos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarCursoPorId(@PathVariable String id) {
        return cursoService.buscarCursoPorId(id)
                .map(curso -> ResponseEntity.ok(curso))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/completo")
    public ResponseEntity<CursoCompletoDTO> buscarCursoCompletoPorId(@PathVariable String id) {
        return cursoService.buscarCursoPorId(id)
                .map(curso -> {
                    List<Estudante> estudantes = curso.getEstudanteIds() != null 
                        ? estudanteRepository.findAllById(curso.getEstudanteIds())
                        : List.of();
                    return ResponseEntity.ok(CursoCompletoDTO.fromCurso(curso, estudantes));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable String id, @RequestBody Curso curso) {
        if (cursoService.buscarCursoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        curso.setId(id);
        Curso cursoAtualizado = cursoService.atualizarCurso(curso);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCurso(@PathVariable String id) {
        if (cursoService.buscarCursoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cursoService.removerCurso(id);
        return ResponseEntity.noContent().build();
    }
}
