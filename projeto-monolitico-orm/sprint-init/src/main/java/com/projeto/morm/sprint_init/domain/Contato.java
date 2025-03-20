package com.projeto.morm.sprint_init.domain;

public class Contato {
    private Integer codigo;
    private String nome;

    public Contato() {
    }

    public Contato(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
