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

import com.andrelvicente.mongodb.relacionamentos.models.Perfil;
import com.andrelvicente.mongodb.relacionamentos.repositories.PerfilRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<Perfil> criarPerfil(@RequestBody Perfil perfil) {
        Perfil perfilSalvo = perfilRepository.save(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listarTodosPerfis() {
        List<Perfil> perfis = perfilRepository.findAll();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPerfilPorId(@PathVariable String id) {
        return perfilRepository.findById(id)
                .map(perfil -> ResponseEntity.ok(perfil))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> atualizarPerfil(@PathVariable String id, @RequestBody Perfil perfil) {
        if (!perfilRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        perfil.setId(id);
        Perfil perfilAtualizado = perfilRepository.save(perfil);
        return ResponseEntity.ok(perfilAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPerfil(@PathVariable String id) {
        if (!perfilRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        perfilRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
