import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

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

        carregarEstado(); // Carregar o estado anterior, se existir
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
            RequisicaoMesa req = filaEspera.get(0);
            if (req != null && req.getMesa() == null) { // Verificar se a requisição não é nula e se não foi alocada em nenhuma mesa
                filaEspera.remove(0); // Remover o cliente da fila de espera
                mesa.setOcupada(true);
                mesa.setCliente(req.getCliente());
                System.out.println("Mesa " + mesa.getNumero() + " liberada. Cliente " + cliente.getNome() + " realocado.");
            }
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
                writer.println("Cliente: " + cliente.getNome() + " (Pessoas: " + cliente.getNumPessoas() + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}