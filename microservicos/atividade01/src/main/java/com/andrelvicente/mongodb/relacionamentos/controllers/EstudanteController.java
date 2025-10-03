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
import com.andrelvicente.mongodb.relacionamentos.models.dto.EstudanteComCursosDTO;
import com.andrelvicente.mongodb.relacionamentos.models.dto.EstudanteCompletoDTO;
import com.andrelvicente.mongodb.relacionamentos.repositories.CursoRepository;
import com.andrelvicente.mongodb.relacionamentos.services.EstudanteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estudantes")
@RequiredArgsConstructor
public class EstudanteController {

    private final EstudanteService estudanteService;
    private final CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Estudante> criarEstudante(@RequestBody Estudante estudante) {
        Estudante estudanteSalvo = estudanteService.criarEstudante(estudante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudanteSalvo);
    }

    @PostMapping("/com-cursos")
    public ResponseEntity<Estudante> criarEstudanteComCursos(@RequestBody EstudanteComCursosDTO estudanteDTO) {
        try {
            Estudante estudante = new Estudante();
            estudante.setNome(estudanteDTO.getNome());
            estudante.setMatricula(estudanteDTO.getMatricula());
            
            Estudante estudanteSalvo = estudanteService.criarEstudanteComCursos(estudante, estudanteDTO.getCursoIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(estudanteSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{estudanteId}/cursos/{cursoId}")
    public ResponseEntity<Estudante> adicionarCursoAoEstudante(@PathVariable String estudanteId, @PathVariable String cursoId) {
        try {
            Estudante estudante = estudanteService.adicionarCursoAoEstudante(estudanteId, cursoId);
            return ResponseEntity.ok(estudante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{estudanteId}/cursos/{cursoId}")
    public ResponseEntity<Estudante> removerCursoDoEstudante(@PathVariable String estudanteId, @PathVariable String cursoId) {
        try {
            Estudante estudante = estudanteService.removerCursoDoEstudante(estudanteId, cursoId);
            return ResponseEntity.ok(estudante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
        List<Estudante> estudantes = estudanteService.listarTodosEstudantes();
        return ResponseEntity.ok(estudantes);
    }

    @GetMapping("/completos")
    public ResponseEntity<List<EstudanteCompletoDTO>> listarTodosEstudantesCompletos() {
        List<Estudante> estudantes = estudanteService.listarTodosEstudantes();
        List<EstudanteCompletoDTO> estudantesCompletos = estudantes.stream()
            .map(estudante -> {
                List<Curso> cursos = estudante.getCursoIds() != null 
                    ? cursoRepository.findAllById(estudante.getCursoIds())
                    : List.of();
                return EstudanteCompletoDTO.fromEstudante(estudante, cursos);
            })
            .toList();
        return ResponseEntity.ok(estudantesCompletos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarEstudantePorId(@PathVariable String id) {
        return estudanteService.buscarEstudantePorId(id)
                .map(estudante -> ResponseEntity.ok(estudante))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/completo")
    public ResponseEntity<EstudanteCompletoDTO> buscarEstudanteCompletoPorId(@PathVariable String id) {
        return estudanteService.buscarEstudantePorId(id)
                .map(estudante -> {
                    List<Curso> cursos = estudante.getCursoIds() != null 
                        ? cursoRepository.findAllById(estudante.getCursoIds())
                        : List.of();
                    return ResponseEntity.ok(EstudanteCompletoDTO.fromEstudante(estudante, cursos));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(@PathVariable String id, @RequestBody Estudante estudante) {
        if (estudanteService.buscarEstudantePorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        estudante.setId(id);
        Estudante estudanteAtualizado = estudanteService.atualizarEstudante(estudante);
        return ResponseEntity.ok(estudanteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEstudante(@PathVariable String id) {
        if (estudanteService.buscarEstudantePorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        estudanteService.removerEstudante(id);
        return ResponseEntity.noContent().build();
    }
}
