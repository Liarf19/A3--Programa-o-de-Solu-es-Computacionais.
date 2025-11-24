package org.example;
//registra a participaçao de um usuario num evento
public class Participacao {
    //atributos
    private int id;
    private int usuarioId;
    private int eventoId;

    public Participacao() {}
//cria uma nova participaçao
    public Participacao(int usuarioId, int eventoId) {
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
    }
//busca registro existente
    public Participacao(int id, int usuarioId, int eventoId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
    }
    // GETTERS E SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getEventoId() { return eventoId; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
}
