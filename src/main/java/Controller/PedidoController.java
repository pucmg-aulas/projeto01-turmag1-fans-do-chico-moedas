/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author valad
 */



import model.Mesa;
import model.Menu;
import model.Restaurante;
import model.Pedido;
import model.RequisicaoPorMesa;

import javax.swing.*;

public class PedidoController {
    private Restaurante restaurante;

    public PedidoController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String adicionarItemPedido(int idMesa, int itemIndex, int quantidade) {
        Mesa mesa = null;
        for (Mesa m : restaurante.getMesas()) {
            if (m.getId() == idMesa && m.isOcupada()) {
                mesa = m;
                break;
            }
        }

        if (mesa != null) {
            String item = (String) Menu.getItensMenu().keySet().toArray()[itemIndex];
            double preco = Menu.getPreco(item);
            for (int i = 0; i < quantidade; i++) {
                mesa.getPedido().adicionarItem(item, preco);
            }
            return "Item adicionado com sucesso.";
        } else {
            return "Mesa não encontrada ou não está ocupada.";
        }
    }

    public String verPedidos(int idMesa) {
        Mesa mesa = null;
        for (Mesa m : restaurante.getMesas()) {
            if (m.getId() == idMesa && m.isOcupada()) {
                mesa = m;
                break;
            }
        }

        if (mesa != null) {
            Pedido pedido = mesa.getPedido();
            RequisicaoPorMesa requisicao = mesa.getRequisicao();
            if (requisicao != null) {
                StringBuilder detalhesPedido = new StringBuilder("Pedido para Mesa ").append(mesa.getId()).append(":\n");
                for (String item : pedido.getItens()) {
                    detalhesPedido.append(item).append("\n");
                }
                detalhesPedido.append("\nValor Total: R$").append(pedido.getValorTotalFormatado());
                detalhesPedido.append("\nValor com Taxa: R$").append(pedido.getValorComTaxaFormatado());
                detalhesPedido.append("\nValor por Pessoa: R$").append(pedido.getValorPorPessoaFormatado(requisicao.getNumeroPessoas()));
                return detalhesPedido.toString();
            } else {
                return "Requisição para a mesa não encontrada.";
            }
        } else {
            return "Mesa não encontrada ou não está ocupada.";
        }
    }
}
