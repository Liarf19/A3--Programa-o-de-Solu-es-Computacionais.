package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventoDAO {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//metodo p/ inserir evento
    public void inserirEvento(Scanner sc) {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Endereço: ");
            String endereco = sc.nextLine();

            System.out.print("Categoria: ");
            String categoria = sc.nextLine();

            System.out.print("Data e Hora (yyyy-MM-dd HH:mm): ");
            LocalDateTime horario = LocalDateTime.parse(sc.nextLine(), FMT);

            System.out.print("Descrição: ");
            String descricao = sc.nextLine();

            Evento e = new Evento(nome, endereco, categoria, horario, descricao);
            //envia p/ o banco
            salvar(e);

            System.out.println("Evento cadastrado com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro ao cadastrar evento!");
            ex.printStackTrace();
        }
    }

//metodo p/ listar eventos
    public void listarEventos() {
        List<Evento> lista = listar();

        if (lista.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
            return;
        }
//Imprime cada evento em uma linha
        for (Evento e : lista) {
            System.out.println(
                    e.getId() + " - " + e.getNome() +
                            " | " + e.getHorario().format(FMT)
            );
        }
    }

//metodo p/ editar evento
    public void editarEvento(Scanner sc) {
        try {
            System.out.print("ID do evento: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Novo nome: ");
            String nome = sc.nextLine();

            System.out.print("Novo endereço: ");
            String endereco = sc.nextLine();

            System.out.print("Nova categoria: ");
            String categoria = sc.nextLine();

            System.out.print("Novo horário (yyyy-MM-dd HH:mm): ");
            LocalDateTime horario = LocalDateTime.parse(sc.nextLine(), FMT);

            System.out.print("Nova descrição: ");
            String descricao = sc.nextLine();

            Evento atualizado = new Evento(id, nome, endereco, categoria, horario, descricao);
            // Atualiza no banco
            atualizar(atualizado);

            System.out.println("Evento atualizado!");

        } catch (Exception ex) {
            System.out.println("Erro ao editar o evento!");
            ex.printStackTrace();
        }
    }

//metodo p/ excluir evento
    public void excluirEvento(Scanner sc) {
        try {
            System.out.print("ID do evento: ");
            int id = Integer.parseInt(sc.nextLine());
            excluir(id);

            System.out.println("Evento excluído!");

        } catch (Exception ex) {
            System.out.println("Erro ao excluir evento!");
            ex.printStackTrace();
        }
    }


    // EVENTOS FUTUROS
    public void eventosFuturos() {
        System.out.println("\n=== EVENTOS FUTUROS ===");

        List<Evento> lista = listar();
        LocalDateTime agora = LocalDateTime.now();

        lista.stream()
                .filter(e -> e.getHorario().isAfter(agora))
                .forEach(e ->
                        System.out.println(
                                e.getId() + " - " + e.getNome() +
                                        " | " + e.getHorario().format(FMT)
                        )
                );
    }

    // EVENTOS PASSADOS

    public void eventosPassados() {
        System.out.println("\n=== EVENTOS PASSADOS ===");

        List<Evento> lista = listar();
        LocalDateTime agora = LocalDateTime.now();

        lista.stream()
                .filter(e -> e.getHorario().isBefore(agora))
                .forEach(e ->
                        System.out.println(
                                e.getId() + " - " + e.getNome() +
                                        " | " + e.getHorario().format(FMT)
                        )
                );
    }


    // EVENTOS ACONTECENDO AGORA

    public void eventosAgora() {
        System.out.println("\n=== EVENTOS ACONTECENDO AGORA ===");

        List<Evento> lista = listar();
        LocalDateTime agora = LocalDateTime.now();

        lista.stream()
                .filter(e -> e.getHorario().getYear() == agora.getYear()
                        && e.getHorario().getDayOfYear() == agora.getDayOfYear()
                        && e.getHorario().getHour() == agora.getHour())
                .forEach(e ->
                        System.out.println(
                                e.getId() + " - " + e.getNome() +
                                        " | " + e.getHorario().format(FMT)
                        )
                );
    }


    //MÉTODOS PRINCIPAIS DO DAO
//salva evento no banco
    public void salvar(Evento e) {
        String sql = "INSERT INTO eventos (nome,endereco,categoria,horario,descricao) VALUES (?,?,?,?,?)";

        try (Connection conn = Database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNome());
            ps.setString(2, e.getEndereco());
            ps.setString(3, e.getCategoria());
            ps.setString(4, e.getHorario().format(FMT));
            ps.setString(5, e.getDescricao());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//lista todos os eventos
    public List<Evento> listar() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM eventos ORDER BY horario";

        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDateTime h = LocalDateTime.parse(rs.getString("horario"), FMT);

                lista.add(new Evento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("categoria"),
                        h,
                        rs.getString("descricao")
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lista;
    }
//atualizar evento existente
    public void atualizar(Evento e) {
        String sql = "UPDATE eventos SET nome=?, endereco=?, categoria=?, horario=?, descricao=? WHERE id=?";

        try (Connection conn = Database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNome());
            ps.setString(2, e.getEndereco());
            ps.setString(3, e.getCategoria());
            ps.setString(4, e.getHorario().format(FMT));
            ps.setString(5, e.getDescricao());
            ps.setInt(6, e.getId());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//excluir evento pelo id
    public void excluir(int id) {
        String sql = "DELETE FROM eventos WHERE id=?";

        try (Connection conn = Database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



