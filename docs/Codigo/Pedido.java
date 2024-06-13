package Codigo;

import java.util.HashMap;
import java.util.Map;

public class Pedido {
    private Map<String, Integer> itens;

    public Pedido() {
        this.itens = new HashMap<>();
    }

    public Map<String, Integer> getItens() {
        return itens;
    }

    public void adicionarItem(String item, int quantidade) {
        itens.put(item, itens.getOrDefault(item, 0) + quantidade);
    }

    public double calcularTotal(Cardapio cardapio) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : itens.entrySet()) {
            total += cardapio.getItens().get(entry.getKey()) * entry.getValue();
        }
        return total;
    }

    public void exibirPedido() {
        System.out.println("Pedido:");
        for (Map.Entry<String, Integer> item : itens.entrySet()) {
            System.out.println(item.getKey() + " - " + item.getValue() + " unidade(s)");
        }
    }
}
