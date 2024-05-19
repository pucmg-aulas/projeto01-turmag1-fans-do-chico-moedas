import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurante restaurante;

        // Carrega o estado do restaurante
        File mesasOcupadasFile = new File("mesas_ocupadas.txt");
        File listaEsperaFile = new File("lista_espera.txt");
        if (mesasOcupadasFile.exists()) {
            restaurante = new Restaurante();
        } else {
            restaurante = new Restaurante();
        }

        int opcao;
        do {
            System.out.println("\n======================================");
            System.out.println("=============== OPÇÕES ===============");
            System.out.println("======================================");
            System.out.println("\nCLIENTE:");
            System.out.println("[1] Alocar cliente");
            System.out.println("\nMESA:");
            System.out.println("[2] Liberar mesa");
            System.out.println("[3] Ver mesas ocupadas");
            System.out.println("\nFILA DE ESPERA:");
            System.out.println("[4] Ver lista de espera");
            System.out.println("[5] Excluir cliente da fila de espera");
            System.out.println("\nREQUISIÇÕES:");
            System.out.println("[6] Ver requisições por dia");
            System.out.println("\nPEDIDO:");
            System.out.println("[7] Fazer pedido");
            System.out.println("\nCONTA:");
            System.out.println("[8] Fechar conta de uma mesa");
            System.out.println("\n[0] Sair");
            System.out.print("\nEscolha uma opção: ");
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
                    break;

                case 2:
                    System.out.println("\nMesas Ocupadas:");
                    int i = 1;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println("Mesa " + i + ": Capacidade: " + mesa.getCapacidade() + ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + Restaurante.formatarDataHora(cliente.getEntrada()) + ", Hora de saída: " + Restaurante.formatarDataHora(cliente.getSaida()));
                        }
                        i++;
                    }

                    System.out.print("Número da mesa a ser liberada: ");
                    int numMesa = scanner.nextInt();
                    restaurante.liberarMesa(numMesa);
                    break;

                case 3:
                    System.out.println("\nMesas Ocupadas:");
                    i = 1;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println("Mesa " + i + ": Capacidade: " + mesa.getCapacidade() + ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + Restaurante.formatarDataHora(cliente.getEntrada()) + ", Hora de saída: " + Restaurante.formatarDataHora(cliente.getSaida()));
                        }
                        i++;
                    }
                    break;

                case 4:
                    System.out.println("\nLista de Espera:");
                    restaurante.verListaEspera();
                    break;
            
                case 5:
                    System.out.println("\nLista de Espera:");
                    restaurante.verListaEspera();
                    System.out.print("\nNome do cliente a ser excluído da fila de espera: ");
                    String nomeExcluir = scanner.nextLine();
                    restaurante.excluirClienteFilaEspera(nomeExcluir);
                    break;

                case 6:
                    System.out.print("\nDigite a data (dd/MM/yyyy): ");
                    String dataStr = scanner.nextLine();
                    LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    restaurante.verRequisicoesPorDia(data);
                    break;

                case 7:
                    System.out.println("\nMesas Ocupadas:");
                    i = 1;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println("Mesa " + i + ": Capacidade: " + mesa.getCapacidade() + ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + Restaurante.formatarDataHora(cliente.getEntrada()) + ", Hora de saída: " + Restaurante.formatarDataHora(cliente.getSaida()));
                        }
                        i++;
                    }

                    System.out.print("\nNúmero da mesa para fazer o pedido: ");
                    numMesa = scanner.nextInt();
                    restaurante.fazerPedido(numMesa);
                    break;

                case 8:
                    System.out.println("\nMesas Ocupadas:");
                    i = 1;
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println("Mesa " + i + ": Capacidade: " + mesa.getCapacidade() + ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + Restaurante.formatarDataHora(cliente.getEntrada()) + ", Hora de saída: " + Restaurante.formatarDataHora(cliente.getSaida()));
                        }
                        i++;
                    }

                    System.out.print("\nNúmero da mesa para fechar a conta: ");
                    numMesa = scanner.nextInt();
                    restaurante.fecharConta(numMesa);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            restaurante.salvarEstado();
            restaurante.salvarListaEspera();
        } while (opcao != 0);

        scanner.close();
    }
}