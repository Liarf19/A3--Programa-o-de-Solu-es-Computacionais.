package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuarioDAO {
    //Cadastra um novo usuário no banco de dados.
    public void inserirUsuario(Scanner sc) {
        try {
            //entrada
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
//comando SQL
            String sql = "INSERT INTO usuarios (nome, email, telefone) VALUES (?, ?, ?)";

            try (Connection conn = Database.conectar();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nome);
                ps.setString(2, email);
                ps.setString(3, telefone);
                ps.executeUpdate();
            }

            System.out.println("Usuário cadastrado!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário!");
            e.printStackTrace();
        }
    }

//Lista todos os usuários cadastrados.
    public void listarUsuarios() {
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " - " +
                                rs.getString("nome") + " | " +
                                rs.getString("email") + " | " +
                                rs.getString("telefone")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar usuários!");
            e.printStackTrace();
        }
    }
}


