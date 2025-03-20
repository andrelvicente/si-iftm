package com.projeto.morm.sprint_init.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.morm.sprint_init.domain.Contato;

@RestController
public class ContatosController {
    @GetMapping("/contatos")
    public List<Contato> contatos(){
        return Arrays.asList(
            new Contato(1, "Andre"),
            new Contato(1, "Andre")
        );
    }
}
