/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author valad
 */

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private List<String> itens;
    private double valorTotal;
    private String metodoPagamento;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Pedido() {
        itens = new ArrayList<>();
        valorTotal = 0.0;
    }

    public void adicionarItem(String item, double preco) {
        itens.add(item);
        valorTotal += preco;
    }

    public List<String> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getValorTotalFormatado() {
        return df.format(valorTotal);
    }

    public double getValorComTaxa() {
        return valorTotal * 1.10; // Taxa de serviço de 10%
    }

    public String getValorComTaxaFormatado() {
        return df.format(getValorComTaxa());
    }

    public double getValorPorPessoa(int numPessoas) {
        if (numPessoas <= 0) {
            return 0;
        }
        return getValorComTaxa() / numPessoas;
    }

    public String getValorPorPessoaFormatado(int numPessoas) {
        return df.format(getValorPorPessoa(numPessoas));
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public double calcularValorRecebido() {
        double desconto = 0.0;

        switch (metodoPagamento) {
            case "Dinheiro":
                desconto = 0.0;
                break;
            case "Pix":
                desconto = Math.min(valorTotal * 0.0145, 10.0);
                break;
            case "Débito":
                desconto = valorTotal * 0.014;
                break;
            case "Crédito":
                desconto = valorTotal * 0.031;
                break;
        }

        return valorTotal - desconto;
    }

    public String calcularValorRecebidoFormatado() {
        return df.format(calcularValorRecebido());
    }

    public int calcularPrazoRecebimento() {
        int prazo = 0;

        switch (metodoPagamento) {
            case "Dinheiro":
                prazo = 0;
                break;
            case "Pix":
                prazo = 0;
                break;
            case "Débito":
                prazo = 14;
                break;
            case "Crédito":
                prazo = 30;
                break;
        }

        return prazo;
    }
}
