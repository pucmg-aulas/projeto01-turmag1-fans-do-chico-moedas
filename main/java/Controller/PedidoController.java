/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author valad
 */



import model.Menu;
import model.Mesa;
import model.Restaurante;

public class PedidoController {
    private Restaurante restaurante;

    public PedidoController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String adicionarItemPedido(int idMesa, int itemIndex, int quantidade) {
        Mesa mesa = restaurante.getMesas().stream()
                .filter(m -> m.getId() == idMesa)
                .findFirst()
                .orElse(null);

        if (mesa != null && mesa.isOcupada()) {
            String item = (String) Menu.getItensMenu().keySet().toArray()[itemIndex];
            double preco = Menu.getPreco(item);
            mesa.getPedido().adicionarItem(item, preco * quantidade);
            return "Item adicionado com sucesso!";
        } else {
            return "Mesa não encontrada ou não ocupada.";
        }
    }

    public String verPedidos(int idMesa) {
        Mesa mesa = restaurante.getMesas().stream()
                .filter(m -> m.getId() == idMesa)
                .findFirst()
                .orElse(null);

        if (mesa != null && mesa.isOcupada()) {
            return mesa.getPedido().gerarRelatorioItens();
        } else {
            return "Mesa não encontrada ou não ocupada.";
        }
    }

    public void finalizarPedido(int idMesa, String metodoPagamento) {
        Mesa mesa = restaurante.getMesas().stream()
                .filter(m -> m.getId() == idMesa)
                .findFirst()
                .orElse(null);

        if (mesa != null && mesa.isOcupada()) {
            mesa.getPedido().setMetodoPagamento(metodoPagamento);
            restaurante.registrarPedido(mesa.getPedido());
            double valorComTaxa = mesa.getPedido().getValorComTaxa(); // Certifique-se de usar o valor com taxa
            restaurante.registrarVenda(valorComTaxa, metodoPagamento);
            mesa.desocupar();
        }
    }
}
