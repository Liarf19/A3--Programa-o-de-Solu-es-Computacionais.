package org.example;

import java.util.Scanner;

public class Menu {

    public static void exibirMenu() {
//acessa o banco
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        EventoDAO eventoDAO = new EventoDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Listar usuários");

            System.out.println("3. Cadastrar evento");
            System.out.println("4. Listar eventos");
            System.out.println("5. Editar evento");
            System.out.println("6. Excluir evento");
            System.out.println("7. Eventos futuros");
            System.out.println("8. Eventos passados");
            System.out.println("9. Eventos agora");

            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {


                // USUÁRIOS
                case 1 -> usuarioDAO.inserirUsuario(sc);
                case 2 -> usuarioDAO.listarUsuarios();


                // EVENTOS
                case 3 -> eventoDAO.inserirEvento(sc);
                case 4 -> eventoDAO.listarEventos();
                case 5 -> eventoDAO.editarEvento(sc);
                case 6 -> eventoDAO.excluirEvento(sc);
                case 7 -> eventoDAO.eventosFuturos();
                case 8 -> eventoDAO.eventosPassados();
                case 9 -> eventoDAO.eventosAgora();

                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }
}



