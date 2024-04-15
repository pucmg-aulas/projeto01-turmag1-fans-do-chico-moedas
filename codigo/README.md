import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int numPessoas;
    private LocalDateTime entrada;

    public Cliente(String nome, int numPessoas) {
        this.nome = nome;
        this.numPessoas = numPessoas;
    }

    public String getNome() {
        return nome;
    }

    public int getNumPessoas() {
        return numPessoas;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }
}

class Mesa implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private int capacidade;
    private boolean ocupada;
    private Cliente cliente;

    public Mesa(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.ocupada = false;
        this.cliente = null;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

class RequisicaoMesa implements Serializable {
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private Mesa mesa;
    private boolean atendida;
    private LocalDateTime entrada;

    public RequisicaoMesa(Cliente cliente, Mesa mesa, LocalDateTime entrada) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.entrada = entrada;
        this.atendida = false;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }
}

class Restaurante implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Mesa> mesas;
    private List<RequisicaoMesa> filaEspera;

    public Restaurante() {
        mesas = new ArrayList<>();
        filaEspera = new ArrayList<>();
        // Adicionar mesas com capacidades especificadas
        mesas.add(new Mesa(1, 4));
        mesas.add(new Mesa(2, 4));
        mesas.add(new Mesa(3, 4));
        mesas.add(new Mesa(4, 4));
        mesas.add(new Mesa(5, 6));
        mesas.add(new Mesa(6, 6));
        mesas.add(new Mesa(7, 6));
        mesas.add(new Mesa(8, 6));
        mesas.add(new Mesa(9, 8));
        mesas.add(new Mesa(10, 8));
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void requisitarMesa(String nomeCliente, int numPessoas, LocalDateTime entrada) {
        Cliente cliente = new Cliente(nomeCliente, numPessoas);
        Mesa mesaDisponivel = encontrarMesaDisponivel(cliente.getNumPessoas());
        if (mesaDisponivel != null) {
            mesaDisponivel.setOcupada(true);
            mesaDisponivel.setCliente(cliente);
            RequisicaoMesa requisicao = new RequisicaoMesa(cliente, mesaDisponivel, entrada);
            requisicao.setAtendida(true);
            cliente.setEntrada(entrada);
            System.out.println("Mesa alocada para " + nomeCliente);
        } else {
            filaEspera.add(new RequisicaoMesa(cliente, null, entrada));
            System.out.println("Não há mesas disponíveis. " + nomeCliente + " adicionado à fila de espera.");
        }
    }

    public void liberarMesa(int numMesa) {
        Mesa mesa = mesas.get(numMesa - 1);
        mesa.setOcupada(false);
        Cliente cliente = mesa.getCliente();
        mesa.setCliente(null);

        // Atender o próximo cliente da fila de espera, se houver
        if (!filaEspera.isEmpty()) {
            RequisicaoMesa req = filaEspera.remove(0);
            Mesa mesaLiberada = req.getMesa();
            mesaLiberada.setOcupada(true);
            mesaLiberada.setCliente(req.getCliente());
            System.out.println("Mesa " + mesaLiberada.getNumero() + " liberada. Cliente " + cliente.getNome() + " realocado.");
        } else {
            System.out.println("Mesa " + mesa.getNumero() + " liberada. Não há clientes na fila de espera.");
        }
    }

    private Mesa encontrarMesaDisponivel(int numPessoas) {
        for (Mesa mesa : mesas) {
            if (!mesa.isOcupada() && mesa.getCapacidade() >= numPessoas) {
                return mesa;
            }
        }
        return null;
    }

    public void salvarEstado() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("restaurante.txt"))) {
            for (Mesa mesa : mesas) {
                if (mesa.isOcupada()) {
                    Cliente cliente = mesa.getCliente();
                    writer.println("Mesa: " + mesa.getNumero() + ", Capacidade: " + mesa.getCapacidade() +
                            ", Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verFilaEspera() {
        try (BufferedReader reader = new BufferedReader(new FileReader("restaurante.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurante restaurante;

        // Carregar estado anterior ou criar um novo restaurante
        File file = new File("restaurante.txt");
        if (file.exists()) {
            restaurante = new Restaurante();
        } else {
            restaurante = new Restaurante();
        }

        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Alocar cliente");
            System.out.println("2. Liberar mesa");
            System.out.println("3. Ver fila de espera");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    String nomeCliente = scanner.nextLine();
                    System.out.print("Quantidade de pessoas: ");
                    int numPessoas = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer do scanner
                    LocalDateTime entrada = LocalDateTime.now();
                    restaurante.requisitarMesa(nomeCliente, numPessoas, entrada);
                    restaurante.salvarEstado();
                    break;
                case 2:
                    System.out.println("Mesa | Capacidade | Cliente");
                    for (Mesa mesa : restaurante.getMesas()) {
                        if (mesa.isOcupada()) {
                            Cliente cliente = mesa.getCliente();
                            System.out.println(mesa.getNumero() + " | " + mesa.getCapacidade() + " | " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + ")");
                        }
                    }
                    System.out.print("Número da mesa a ser liberada: ");
                    int numMesa = scanner.nextInt();
                    restaurante.liberarMesa(numMesa);
                    restaurante.salvarEstado();
                    break;
                case 3:
                    restaurante.verFilaEspera();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }
}
