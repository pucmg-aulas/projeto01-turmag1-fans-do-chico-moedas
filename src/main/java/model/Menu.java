/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author valad
 */


import java.util.HashMap;
import java.util.Map;

public class Menu {
    private static final Map<String, Double> itensMenu = new HashMap<>();

    static {
        itensMenu.put("Moqueca de Tilápia", 25.00);
        itensMenu.put("Falafel Assado", 18.00);
        itensMenu.put("Salada Primavera com Macarrão Konjac", 20.00);
        itensMenu.put("Escondidinho de Frango", 22.00);
        itensMenu.put("Strogonoff", 24.00);
        itensMenu.put("Caçarola de carne com legumes", 26.00);
        itensMenu.put("Água", 3.00);
        itensMenu.put("Suco", 5.00);
        itensMenu.put("Refrigerante", 4.00);
        itensMenu.put("Cerveja", 6.00);
        itensMenu.put("Taça de vinho", 8.00);
    }

    public static Map<String, Double> getItensMenu() {
        return itensMenu;
    }

    public static double getPreco(String item) {
        return itensMenu.getOrDefault(item, 0.0);
    }
}
