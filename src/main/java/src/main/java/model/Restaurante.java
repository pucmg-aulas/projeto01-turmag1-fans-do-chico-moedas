/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author valad
 */

import exception.MesaNaoDisponivelException;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Restaurante implements Serializable {
    private List<Mesa> mesas;
    private List<RequisicaoPorMesa> filaDeEspera;
    private double vendasPix;
    private double vendasDinheiro;
    private double aReceberDebito;
    private double aReceberCredito;
    private LocalDate dataUltimaVenda;
    private List<Pedido> pedidos; // Adicionando uma lista para armazenar os pedidos

    public Restaurante() {
        mesas = new ArrayList<>();
        filaDeEspera = new ArrayList<>();
        vendasPix = 0.0;
        vendasDinheiro = 0.0;
        aReceberDebito = 0.0;
        aReceberCredito = 0.0;
        dataUltimaVenda = LocalDate.now();
        pedidos = new ArrayList<>(); // Inicializando a lista de pedidos
        inicializarMesas();
    }

    private void inicializarMesas() {
        for (int i = 1; i <= 4; i++) {
            mesas.add(new Mesa(i, 4));
        }
        for (int i = 5; i <= 8; i++) {
            mesas.add(new Mesa(i, 6));
        }
        for (int i = 9; i <= 10; i++) {
            mesas.add(new Mesa(i, 8));
        }
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public List<RequisicaoPorMesa> getFilaDeEspera() {
        return filaDeEspera;
    }

    public void registrarPedido(Pedido pedido) {
        pedidos.add(pedido);
        registrarVenda(pedido.getValorTotal(), pedido.getMetodoPagamento());
    }

    public Mesa alocarMesa(RequisicaoPorMesa requisicao) throws MesaNaoDisponivelException {
        Optional<Mesa> mesaOptional = mesas.stream()
                .filter(m -> !m.isOcupada() && m.getCapacidade() >= requisicao.getNumeroPessoas())
                .findFirst();

        if (mesaOptional.isPresent()) {
            Mesa mesa = mesaOptional.get();
            mesa.ocupar();
            mesa.setRequisicao(requisicao);
            requisicao.setMesa(mesa);
            return mesa;
        } else {
            filaDeEspera.add(requisicao);
            throw new MesaNaoDisponivelException("Não há mesas disponíveis para a requisição.");
        }
    }

    public void liberarMesa(Mesa mesa) {
        mesa.desocupar();
        if (!filaDeEspera.isEmpty()) {
            RequisicaoPorMesa requisicao = filaDeEspera.remove(0);
            try {
                alocarMesa(requisicao);
            } catch (MesaNaoDisponivelException e) {
                e.printStackTrace();
            }
        }
    }

    public void registrarVenda(double valor, String metodoPagamento) {
        if (LocalDate.now().isAfter(dataUltimaVenda)) {
            vendasPix = 0.0;
            vendasDinheiro = 0.0;
            dataUltimaVenda = LocalDate.now();
        }

        switch (metodoPagamento) {
            case "Pix":
                vendasPix += valor;
                break;
            case "Dinheiro":
                vendasDinheiro += valor;
                break;
            case "Débito":
                aReceberDebito += valor;
                break;
            case "Crédito":
                aReceberCredito += valor;
                break;
        }
    }

    public double getVendasPix() {
        return vendasPix;
    }

    public double getVendasDinheiro() {
        return vendasDinheiro;
    }

    public double getAReceberDebito() {
        return aReceberDebito;
    }

    public double getAReceberCredito() {
        return aReceberCredito;
    }

    public void setAReceberDebito(double valor) {
        this.aReceberDebito = valor;
    }

    public void setAReceberCredito(double valor) {
        this.aReceberCredito = valor;
    }

    public static Restaurante carregarDados(String local) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(local))) {
            return (Restaurante) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Restaurante();
        }
    }

    public void salvarDados(String local) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(local))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos de geração de relatórios usando streams
    public String gerarRelatorioVendasDiarias() {
        return String.format("Vendas do dia:\nPix: R$%.2f\nDinheiro: R$%.2f\n",
                vendasPix, vendasDinheiro);
    }

    public String gerarRelatorioRecebimentosFuturos() {
        return String.format("Recebimentos Futuros:\nDébito (próximos 14 dias): R$%.2f\nCrédito (próximos 30 dias): R$%.2f\n",
                aReceberDebito, aReceberCredito);
    }

    public String gerarRelatorioCompletoVendas() {
        List<String> relatorios = Arrays.asList(
                gerarRelatorioVendasDiarias(),
                gerarRelatorioRecebimentosFuturos()
        );
        return relatorios.stream().collect(Collectors.joining("\n"));
    }

    // Método para gerar relatório de pedidos
    public String gerarRelatorioPedidos() {
        return pedidos.stream()
                .map(Pedido::toString)
                .collect(Collectors.joining("\n"));
    }
}
