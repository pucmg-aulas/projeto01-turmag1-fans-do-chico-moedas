import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Restaurante implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Mesa> mesas;
    private List<RequisicaoMesa> filaEspera;
    private List<RequisicaoMesa> todasRequisicoes;
    private List<Pedido> menu;

    public Restaurante() {
        mesas = new ArrayList<>();
        filaEspera = new ArrayList<>();
        todasRequisicoes = new ArrayList<>();
        menu = new ArrayList<>();
        // Cria as mesas com a quantidade de lugares
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

        carregarEstado(); // Carrega o estado do restaurante
        carregarMenu();   // Carrega o menu de bebidas e comidas
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void requisitarMesa(String nomeCliente, int numPessoas, LocalDateTime entrada) {
        Cliente cliente = new Cliente(nomeCliente, numPessoas, entrada);
        Mesa mesaDisponivel = encontrarMesaDisponivel(cliente.getNumPessoas());
        if (mesaDisponivel != null) {
            mesaDisponivel.setOcupada(true);
            mesaDisponivel.setCliente(cliente);
            RequisicaoMesa requisicao = new RequisicaoMesa(cliente, mesaDisponivel, entrada);
            requisicao.setAtendida(true);
            cliente.setEntrada(entrada);
            System.out.println("Mesa alocada para " + nomeCliente + ". Hora de entrada: " + formatarDataHora(entrada));
            todasRequisicoes.add(requisicao);
        } else {
            if (cliente.getNumPessoas() <= 8) {
                filaEspera.add(new RequisicaoMesa(cliente, null, entrada));
                System.out.println("Não há mesas disponíveis. " + nomeCliente + " adicionado à fila de espera. Hora de entrada: " + formatarDataHora(entrada));
            } else {
                System.out.println("Não há mesas disponíveis com mais de 8 lugares. Não foi possível alocar mesa para " + nomeCliente + ". Hora de entrada: " + formatarDataHora(entrada));
            }
        }
        salvarEstado(); // Salvar o estado das mesas ocupadas
        salvarListaEspera(); // Salvar a lista de espera
        salvarRequisicoes(); // Salvar todas as requisições
    }

    public void liberarMesa(int numMesa) {
        Mesa mesa = mesas.get(numMesa - 1);
        mesa.setOcupada(false);
        Cliente cliente = mesa.getCliente();
        mesa.setCliente(null);
        LocalDateTime saida = LocalDateTime.now();
        cliente.setSaida(saida);

        // Atender o próximo cliente da fila de espera, se houver
        if (!filaEspera.isEmpty()) {
            RequisicaoMesa req = filaEspera.get(0);
            if (req != null && req.getMesa() == null && req.getCliente().getNumPessoas() <= mesa.getCapacidade()) {
                filaEspera.remove(0); // Remover o cliente da fila de espera
                mesa.setOcupada(true);
                mesa.setCliente(req.getCliente());
                System.out.println("Mesa " + mesa.getNumero() + " liberada. Cliente " + cliente.getNome() + " realocado. Hora de entrada: " + formatarDataHora(req.getEntrada()));
                todasRequisicoes.add(req);
            } else {
                System.out.println("Não há clientes na fila de espera com número de pessoas compatível com a mesa liberada. Hora de entrada do cliente liberado: " + formatarDataHora(cliente.getEntrada()));
            }
        } else {
            System.out.println("Mesa " + mesa.getNumero() + " liberada. Não há clientes na fila de espera. Hora de entrada: " + formatarDataHora(cliente.getEntrada()));
        }
        salvarEstado(); // Salvar o estado das mesas ocupadas
        salvarListaEspera(); // Salvar a lista de espera
        salvarRequisicoes(); // Salvar todas as requisições
    }

    public void excluirClienteFilaEspera(String nome) {
        for (Iterator<RequisicaoMesa> iterator = filaEspera.iterator(); iterator.hasNext();) {
            RequisicaoMesa req = iterator.next();
            if (req.getCliente().getNome().equals(nome)) {
                iterator.remove();
                System.out.println("Cliente " + nome + " excluído da fila de espera.");
                return;
            }
        }
        System.out.println("Cliente " + nome + " não encontrado na fila de espera.");
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mesas_ocupadas.txt"))) {
            oos.writeObject(mesas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarListaEspera() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("lista_espera.txt"))) {
            for (RequisicaoMesa req : filaEspera) {
                Cliente cliente = req.getCliente();
                writer.println("Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + "), Hora de entrada: " + formatarDataHora(req.getEntrada()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarRequisicoes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("requisicoes.txt"))) {
            oos.writeObject(todasRequisicoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarEstado() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("mesas_ocupadas.txt"))) {
            mesas = (List<Mesa>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void verListaEspera() {
        try (BufferedReader reader = new BufferedReader(new FileReader("lista_espera.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verRequisicoesPorDia(LocalDate data) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("requisicoes.txt"))) {
            @SuppressWarnings("unchecked")
            List<RequisicaoMesa> requisicoes = (List<RequisicaoMesa>) ois.readObject();
            for (RequisicaoMesa req : requisicoes) {
                LocalDateTime entrada = req.getEntrada();
                LocalDateTime saida = req.getCliente().getSaida();
                if (entrada.toLocalDate().equals(data) && saida != null) {
                    Cliente cliente = req.getCliente();
                    System.out.println("Nome do cliente: " + cliente.getNome() + ", Data: " + formatarData(entrada) +
                                       ", Hora de entrada: " + formatarHora(entrada) + ", Hora de saída: " + formatarHora(saida) +
                                       ", Quantidade de pessoas: " + cliente.getNumPessoas());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void carregarMenu() {
        try (BufferedReader bebidasReader = new BufferedReader(new FileReader("bebidas.txt"));
             BufferedReader comidasReader = new BufferedReader(new FileReader("comidas.txt"))) {

            String linha;
            while ((linha = bebidasReader.readLine()) != null) {
                String[] partes = linha.split(": R\\$ ");
                menu.add(new Pedido(partes[0].trim(), Double.parseDouble(partes[1].trim().replace(",", "."))));
            }

            while ((linha = comidasReader.readLine()) != null) {
                String[] partes = linha.split(": R\\$ ");
                menu.add(new Pedido(partes[0].trim(), Double.parseDouble(partes[1].trim().replace(",", "."))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> getMenu() {
        return menu;
    }

    public void fazerPedido(int numMesa) {
        Mesa mesa = mesas.get(numMesa - 1);
        if (!mesa.isOcupada()) {
            System.out.println("Mesa não está ocupada.");
            return;
        }

        Cliente cliente = mesa.getCliente();
        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;

            System.out.println("\nMenu:");
            int i = 1;
            for (Pedido pedido : menu) {
                System.out.println("[" + i + "] " + pedido);
                i++;
            }
            System.out.println("[0] Finalizar pedido");

            do {
                System.out.print("\nEscolha uma opção: ");
                opcao = scanner.nextInt();
                if (opcao > 0 && opcao <= menu.size()) {
                    Pedido pedido = menu.get(opcao - 1);
                    cliente.adicionarPedido(pedido);
                    System.out.println("Adicionado: " + pedido);
                }
            } while (opcao != 0);
        }
    }

    public void fecharConta(int numMesa) {
        Mesa mesa = mesas.get(numMesa - 1);
        if (!mesa.isOcupada()) {
            System.out.println("Mesa não está ocupada.");
            return;
        }
    
        Cliente cliente = mesa.getCliente();
        List<Pedido> pedidos = cliente.getPedidos();
    
        double total = 0.0;
        for (Pedido pedido : pedidos) {
            total += pedido.getPreco();
        }
    
        double couvertArtistico = cliente.getNumPessoas() * 25.0;
        double taxaServico = total * 0.10;
        double totalComTaxas = total + taxaServico + couvertArtistico;
        double valorPorPessoa = totalComTaxas / cliente.getNumPessoas();
    
        System.out.println("\nDetalhes da Conta:");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
        System.out.println("\nTotal: R$ " + String.format("%.2f", total));
        System.out.println("Couvert Artístico: R$ " + String.format("%.2f", couvertArtistico));
        System.out.println("Taxa de Serviço (10%): R$ " + String.format("%.2f", taxaServico));
        System.out.println("Total com Taxas: R$ " + String.format("%.2f", totalComTaxas));
        System.out.println("Valor por Pessoa: R$ " + String.format("%.2f", valorPorPessoa));
    
        liberarMesa(numMesa); // Libera a mesa após fechar a conta
    }    

    public static String formatarData(LocalDateTime dataHora) {
        if (dataHora == null) {
            return "Não definida";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return dataHora.format(formatter);
        }
    }

    public static String formatarHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            return "Não definida";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return dataHora.format(formatter);
        }
    }

    public static String formatarDataHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            return "Não definida";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dataHora.format(formatter);
        }
    }
}