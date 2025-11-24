package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//acessa e manipula os dados da participaçao
public class ParticipacaoDAO {

    // Define o formato de data/hora usado para conversão entre String (BD) e LocalDateTime (Java)
    // ATENÇÃO:("yyyy-MM-dd HH:mm:ss")
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//salva uma nova participaçao no banco de dados
    public void salvar(Participacao p) {
        String sql = "INSERT INTO participacoes (usuario_id, evento_id) VALUES (?, ?)";

        try (Connection conn = Database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
// Ligando dados do objeto ao SQL
            ps.setInt(1, p.getUsuarioId());
            ps.setInt(2, p.getEventoId());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//lista todas as particpaçoes existentes
    public List<Participacao> listar() {
        List<Participacao> lista = new ArrayList<>();

        String sql = "SELECT * FROM participacoes";

        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Participacao p = new Participacao(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("evento_id")
                );
                lista.add(p);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // lista os eventos em que o usuário está participando.
    public List<Evento> listarParticipacoesPorUsuario(int usuarioId) {
        List<Evento> lista = new ArrayList<>();

        String sql = """
        SELECT eventos.id, eventos.nome, eventos.endereco, eventos.categoria, eventos.horario, eventos.descricao
        FROM participacoes
        JOIN eventos ON participacoes.evento_id = eventos.id
        WHERE participacoes.usuario_id = ?
        """;

        try (Connection conn = Database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String horarioString = rs.getString("horario");
                LocalDateTime horarioObjeto = null;

                if (horarioString != null) {

                    horarioObjeto = LocalDateTime.parse(horarioString, FORMATTER);
                }

                Evento e = new Evento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("categoria"),
                        horarioObjeto, // Campo corrigido!
                        rs.getString("descricao")
                );
                lista.add(e);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // para cancelar a participaçao no evento
    public void cancelarParticipacao(int usuarioId, int eventoId) {
        String sql = "DELETE FROM participacoes WHERE usuario_id = ? AND evento_id = ?";

        try (Connection conn = Database.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, eventoId);
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
