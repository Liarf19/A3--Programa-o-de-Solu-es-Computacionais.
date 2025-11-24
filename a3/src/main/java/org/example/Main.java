package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EventoDAO eventoDAO = new EventoDAO(); // <-- AQUI
//loop infinito
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar eventos");
            System.out.println("3 - Editar evento");
            System.out.println("4 - Excluir evento");
            System.out.println("5 - Eventos futuros");
            System.out.println("6 - Eventos passados");
            System.out.println("7 - Eventos acontecendo agora");
            System.out.println("0 - Sair");

            System.out.print("Escolha: ");
            //lê a opção
            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> eventoDAO.inserirEvento(sc);
                case 2 -> eventoDAO.listarEventos();
                case 3 -> eventoDAO.editarEvento(sc);
                case 4 -> eventoDAO.excluirEvento(sc);
                case 5 -> eventoDAO.eventosFuturos();
                case 6 -> eventoDAO.eventosPassados();
                case 7 -> eventoDAO.eventosAgora();
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }
}
