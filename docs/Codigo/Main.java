import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurante restaurante;

        // Carregar estado anterior ou criar um novo restaurante
        File mesasOcupadasFile = new File("mesas_ocupadas.txt");
        File listaEsperaFile = new File("lista_espera.txt");
        if (mesasOcupadasFile.exists()) {
            restaurante = new Restaurante();
        } else {
            restaurante = new Restaurante();
        }

        int opcao;
        do {
            System.out.println("\n==================");
            System.out.println("===== OPÇÕES =====");
            System.out.println("==================");
            System.out.println("\n[1] Alocar cliente");
            System.out.println("[2] Liberar mesa");
            System.out.println("[3] Ver mesas ocupadas");
            System.out.println("[4] Ver lista de espera");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("\nNome do cliente: ");
                    String nomeCliente = scanner.nextLine();
                    System.out.print("Quantidade de pessoas: ");
                    int numPessoas = scanner.nextInt();
                    scanner.nextLine();
                    LocalDateTime entrada = LocalDateTime.now();
                    restaurante.requisitarMesa(nomeCliente, numPessoas, entrada);
                    restaurante.salvarEstado();
                    restaurante.salvarListaEspera();
                    break;
                case 2:
                    System.out.println("\nMesas Ocupadas:");
                    int i = 1;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println("Mesa " + i + ": Capacidade: " + mesa.getCapacidade() + ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + formatarDataHora(cliente.getEntrada()));
                        }
                        i++;
                    }
                    System.out.print("Número da mesa a ser liberada: ");
                    int numMesa = scanner.nextInt();
                    restaurante.liberarMesa(numMesa);
                    restaurante.salvarEstado();
                    restaurante.salvarListaEspera();
                    break;
                case 3:
                    System.out.println("\nMesas Ocupadas:");
                    i = 1;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println("Mesa " + i + ": Capacidade: " + mesa.getCapacidade() + ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + formatarDataHora(cliente.getEntrada()));
                        }
                        i++;
                    }
                    break;
                case 4:
                    System.out.println("\nLista de Espera:");
                    restaurante.verListaEspera();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    public static String formatarDataHora(LocalDateTime dataHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }
}            