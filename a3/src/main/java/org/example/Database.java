package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
//faz conexao com o banco de dados SQLite
public class Database {
    public static Connection conectar() {
        try {
            String url = "jdbc:sqlite:database/banco.db";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco");
            e.printStackTrace();
            return null;
        }
    }
}
