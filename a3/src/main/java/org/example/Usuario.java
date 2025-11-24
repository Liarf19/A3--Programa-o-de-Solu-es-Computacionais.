package org.example;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String telefone;

    public Usuario() {}
//construtor p/ quando o user ainda nao possuir id
    public Usuario(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
//dados ja existem no banco
    public Usuario(int id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

}
