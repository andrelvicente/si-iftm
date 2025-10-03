package com.andrelvicente.mongodb.relacionamentos.controllers;

import java.time.LocalDateTime;
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

import com.andrelvicente.mongodb.relacionamentos.models.Postagem;
import com.andrelvicente.mongodb.relacionamentos.repositories.PostagemRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/postagens")
@RequiredArgsConstructor
public class PostagemController {

    private final PostagemRepository postagemRepository;

    @PostMapping
    public ResponseEntity<Postagem> criarPostagem(@RequestBody Postagem postagem) {
        postagem.setDataCriacao(LocalDateTime.now());
        Postagem postagemSalva = postagemRepository.save(postagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemSalva);
    }

    @GetMapping
    public ResponseEntity<List<Postagem>> listarTodasPostagens() {
        List<Postagem> postagens = postagemRepository.findAll();
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> buscarPostagemPorId(@PathVariable String id) {
        return postagemRepository.findById(id)
                .map(postagem -> ResponseEntity.ok(postagem))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postagem> atualizarPostagem(@PathVariable String id, @RequestBody Postagem postagem) {
        if (!postagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postagem.setId(id);
        Postagem postagemAtualizada = postagemRepository.save(postagem);
        return ResponseEntity.ok(postagemAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPostagem(@PathVariable String id) {
        if (!postagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
