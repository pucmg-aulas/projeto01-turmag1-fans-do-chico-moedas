/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author valad
 */


import model.Mesa;
import model.RequisicaoPorMesa;
import model.Restaurante;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private Restaurante restaurante;
    private Map<Mesa, RequisicaoPorMesa> mesasOcupadas;
    private PainelStatus painelStatus;

    public MainFrame() {
        restaurante = Restaurante.carregarDados("restaurante.dat");
        mesasOcupadas = new HashMap<>();

        setTitle("Nery Lanches");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel para adicionar novas requisições
        PainelRequisicao painelRequisicao = new PainelRequisicao(restaurante, this::atualizarStatus);
        add(painelRequisicao, BorderLayout.NORTH);

        // Painel para liberar mesas
        PainelLiberarMesa painelLiberarMesa = new PainelLiberarMesa(restaurante, this::atualizarStatus);
        add(painelLiberarMesa, BorderLayout.SOUTH);

        // Painel com botões de ação
        PainelAcoes painelAcoes = new PainelAcoes(restaurante);
        add(painelAcoes, BorderLayout.EAST);

        // Área de status para mostrar mesas livres e ocupadas
        painelStatus = new PainelStatus(restaurante, mesasOcupadas);
        add(painelStatus, BorderLayout.CENTER);

        System.out.println("Interface inicializada corretamente");
    }

    private void atualizarStatus() {
        painelStatus.atualizarStatus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    frame.restaurante.salvarDados("restaurante.dat");
                }
            });
            System.out.println("Interface exibida corretamente");
        });
    }
}
