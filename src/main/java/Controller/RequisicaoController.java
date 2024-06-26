/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author valad
 */


import exception.MesaNaoDisponivelException;
import model.Mesa;
import model.RequisicaoPorMesa;
import model.Restaurante;

public class RequisicaoController {
    private Restaurante restaurante;

    public RequisicaoController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String adicionarRequisicao(String nomeCliente, int numeroPessoas) {
        RequisicaoPorMesa requisicao = new RequisicaoPorMesa(restaurante.getFilaDeEspera().size() + 1, nomeCliente, numeroPessoas);
        try {
            Mesa mesa = restaurante.alocarMesa(requisicao);
            return "Mesa alocada: " + mesa.getId();
        } catch (MesaNaoDisponivelException e) {
            return "Requisição adicionada à fila de espera. Motivo: " + e.getMessage();
        }
    }
}
