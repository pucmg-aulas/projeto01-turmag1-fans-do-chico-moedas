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

public class Mesa implements Serializable {
    private int id;
    private int capacidade;
    private boolean ocupada;
    private Pedido pedido;
    private RequisicaoPorMesa requisicao; // Adicionado

    public Mesa(int id, int capacidade) {
        this.id = id;
        this.capacidade = capacidade;
        this.ocupada = false;
        this.pedido = new Pedido();
    }

    public int getId() {
        return id;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocupar() {
        this.ocupada = true;
    }

    public void desocupar() {
        this.ocupada = false;
        this.pedido = new Pedido();
        this.requisicao = null; // Adicionado
    }

    public Pedido getPedido() {
        return pedido;
    }

    public RequisicaoPorMesa getRequisicao() { // Adicionado
        return requisicao;
    }

    public void setRequisicao(RequisicaoPorMesa requisicao) { // Adicionado
        this.requisicao = requisicao;
    }
}