package Codigo;

import java.util.HashMap;
import java.util.Map;

public class Cardapio {
    private Map<String, Double> itens;

    public Cardapio() {
        this.itens = new HashMap<>();
        itens.put("Moqueca de Tilápia", 20.0);
        itens.put("Falafel Assado", 15.0);
        itens.put("Salada Primavera com Macarrão Konjac", 30.0);
        itens.put("Escondidinho de Frango", 25.0);
        itens.put("Strogonoff", 25.0);
        itens.put("Caçarola de carne com legumes", 20.0);
        itens.put("Água", 5.0);
        itens.put("Suco", 7.0);
        itens.put("Refrigerante", 7.0);
        itens.put("Cerveja", 10.0);
        itens.put("Taça de vinho", 15.0);
    }

    public Map<String, Double> getItens() {
        return itens;
    }

    public void setItens(Map<String, Double> itens) {
        this.itens = itens;
    }

    public void exibirCardapio() {
        System.out.println("Cardápio:");
        for (Map.Entry<String, Double> item : itens.entrySet()) {
            System.out.println(item.getKey() + " - " + item.getValue() + " reais");
        }
    }
}
