/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author valad
 */

import Controller.MesaController;
import model.Restaurante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelLiberarMesa extends JPanel {
    private JTextField txtIdMesa;
    private MesaController mesaController;
    private Runnable atualizarStatus;

    public PainelLiberarMesa(Restaurante restaurante, Runnable atualizarStatus) {
        this.mesaController = new MesaController(restaurante);
        this.atualizarStatus = atualizarStatus;

        setLayout(new FlowLayout());

        add(new JLabel("ID da Mesa:"));
        txtIdMesa = new JTextField(5);
        add(txtIdMesa);

        JButton btnLiberarMesa = new JButton("Liberar Mesa");
        btnLiberarMesa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                liberarMesa();
            }
        });
        add(btnLiberarMesa);
    }

    private void liberarMesa() {
        try {
            int idMesa = Integer.parseInt(txtIdMesa.getText());
            String resultado = mesaController.liberarMesa(idMesa);
            JOptionPane.showMessageDialog(this, resultado);
            txtIdMesa.setText("");
            atualizarStatus.run();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido de mesa.");
        }
    }
}
