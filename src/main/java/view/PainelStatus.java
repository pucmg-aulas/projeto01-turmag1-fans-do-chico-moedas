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
import java.util.Map;
import java.util.stream.Collectors;

public class PainelStatus extends JPanel {
    private JTextArea areaMesasLivres;
    private JTextArea areaMesasOcupadas;
    private Restaurante restaurante;
    private Map<Mesa, RequisicaoPorMesa> mesasOcupadas;

    public PainelStatus(Restaurante restaurante, Map<Mesa, RequisicaoPorMesa> mesasOcupadas) {
        this.restaurante = restaurante;
        this.mesasOcupadas = mesasOcupadas;

        setLayout(new GridLayout(1, 2));

        areaMesasLivres = new JTextArea();
        areaMesasLivres.setEditable(false);
        JScrollPane scrollPaneLivres = new JScrollPane(areaMesasLivres);
        add(scrollPaneLivres);

        areaMesasOcupadas = new JTextArea();
        areaMesasOcupadas.setEditable(false);
        JScrollPane scrollPaneOcupadas = new JScrollPane(areaMesasOcupadas);
        add(scrollPaneOcupadas);

        atualizarStatus();
    }

    public void atualizarStatus() {
        String statusLivres = restaurante.getMesas().stream()
                .filter(m -> !m.isOcupada())
                .map(m -> "Mesa " + m.getId() + " - Capacidade: " + m.getCapacidade())
                .collect(Collectors.joining("\n", "Mesas Livres:\n", ""));

        String statusOcupadas = restaurante.getMesas().stream()
                .filter(Mesa::isOcupada)
                .map(m -> "Mesa " + m.getId() + " - Nome: " + m.getRequisicao().getNomeCliente() + " - Pessoas: " + m.getRequisicao().getNumeroPessoas())
                .collect(Collectors.joining("\n", "Mesas Ocupadas:\n", ""));

        areaMesasLivres.setText(statusLivres);
        areaMesasOcupadas.setText(statusOcupadas);
    }
}
