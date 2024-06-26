/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurante;

/**
 *
 * @author valad
 */
import view.MainFrame;
import model.Restaurante;

public class Main {
    public static void main(String[] args) {
        // Inicializar o restaurante
        Restaurante restaurante = Restaurante.carregarDados("restaurante.dat");

   
        System.out.println("Relatório de Vendas Diárias:");
        System.out.println(restaurante.gerarRelatorioVendasDiarias());

        System.out.println("Relatório de Recebimentos Futuros:");
        System.out.println(restaurante.gerarRelatorioRecebimentosFuturos());

        System.out.println("Relatório Completo de Vendas:");
        System.out.println(restaurante.gerarRelatorioCompletoVendas());

        System.out.println("Relatório de Pedidos:");
        System.out.println(restaurante.gerarRelatorioPedidos());

        // Inicializar a interface gráfica
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        restaurante.salvarDados("restaurante.dat");
                    }
                });
                System.out.println("Interface exibida corretamente");
            }
        });
    }
}
