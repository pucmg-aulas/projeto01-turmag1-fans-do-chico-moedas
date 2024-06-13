package Codigo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();
        Cardapio cardapio = new Cardapio();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Restaurante ---");
            System.out.println("1. Adicionar novo cliente");
            System.out.println("2. Ver mesas ocupadas e livres");
            System.out.println("3. Realizar pedido");
            System.out.println("4. Fechar conta");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir nova linha

            switch (opcao) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Número de pessoas: ");
                    int numeroPessoas = scanner.nextInt();
                    scanner.nextLine();  // Consumir nova linha

                    Cliente cliente = new Cliente(nome);
                    RequisicaoMesa requisicao = new RequisicaoMesa(cliente, numeroPessoas);

                    restaurante.receberRequisicao(requisicao);
                    System.out.println("Requisição adicionada.");

                    if (requisicao.getMesa() != null) {
                        System.out.println("Cliente alocado na mesa " + requisicao.getMesa().getNumero() + ".");
                    } else {
                        System.out.println("Não há mesas disponíveis. Cliente adicionado à fila de espera.");
                    }
                    break;

                case 2:
                    System.out.println("Mesas ocupadas:");
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            System.out.println("Mesa " + mesa.getNumero() + ": " + mesa.getRequisicao().getCliente().getNome() + ", " + mesa.getRequisicao().getNumeroPessoas() + " pessoas (capacidade para " + mesa.getCapacidade() + " pessoas).");
                        }
                    }

                    System.out.println("\nMesas livres:");
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (!mesa.isOcupada()) {
                            System.out.println("Mesa " + mesa.getNumero() + " de " + mesa.getCapacidade() + " pessoas está livre.");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Número da mesa: ");
                    int numeroMesa = scanner.nextInt();
                    scanner.nextLine();  // Consumir nova linha

                    Mesa mesa = restaurante.encontrarMesaPorNumero(numeroMesa);
                    if (mesa != null && mesa.isOcupada()) {
                        Pedido pedido = mesa.getConta().getPedido();
                        String continuar;

                        do {
                            cardapio.exibirCardapio();
                            System.out.print("Digite o nome do item: ");
                            String item = scanner.nextLine();
                            System.out.print("Digite a quantidade: ");
                            int quantidade = scanner.nextInt();
                            scanner.nextLine();  // Consumir nova linha

                            pedido.adicionarItem(item, quantidade);

                            System.out.print("Deseja adicionar mais itens? (s/n): ");
                            continuar = scanner.nextLine();
                        } while (continuar.equalsIgnoreCase("s"));
                    } else {
                        System.out.println("Mesa não encontrada ou não está ocupada.");
                    }
                    break;

                case 4:
                    System.out.print("Número da mesa: ");
                    numeroMesa = scanner.nextInt();
                    scanner.nextLine();  // Consumir nova linha

                    mesa = restaurante.encontrarMesaPorNumero(numeroMesa);
                    if (mesa != null && mesa.isOcupada()) {
                        mesa.getConta().calcularConta(cardapio, mesa.getRequisicao().getNumeroPessoas());
                        mesa.getConta().exibirConta();
                        restaurante.liberarMesa(mesa.getRequisicao());
                    } else {
                        System.out.println("Mesa não encontrada ou não está ocupada.");
                    }
                    break;

                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 5);

        scanner.close();
    }
}
