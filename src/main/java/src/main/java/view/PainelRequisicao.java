/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author valad
 */

import Controller.RequisicaoController;
import model.Restaurante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelRequisicao extends JPanel {
    private JTextField txtNomeCliente;
    private JTextField txtNumPessoas;
    private RequisicaoController requisicaoController;
    private Runnable atualizarStatus;

    public PainelRequisicao(Restaurante restaurante, Runnable atualizarStatus) {
        this.requisicaoController = new RequisicaoController(restaurante);
        this.atualizarStatus = atualizarStatus;

        setLayout(new FlowLayout());

        add(new JLabel("Nome do Cliente:"));
        txtNomeCliente = new JTextField(10);
        add(txtNomeCliente);

        add(new JLabel("Número de Pessoas:"));
        txtNumPessoas = new JTextField(5);
        add(txtNumPessoas);

        JButton btnAdicionarRequisicao = new JButton("Adicionar Requisição");
        btnAdicionarRequisicao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarRequisicao();
            }
        });
        add(btnAdicionarRequisicao);
    }

    private void adicionarRequisicao() {
        try {
            String nomeCliente = txtNomeCliente.getText();
            int numeroPessoas = Integer.parseInt(txtNumPessoas.getText());
            String resultado = requisicaoController.adicionarRequisicao(nomeCliente, numeroPessoas);
            JOptionPane.showMessageDialog(this, resultado);
            txtNomeCliente.setText("");
            txtNumPessoas.setText("");
            atualizarStatus.run();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido de pessoas.");
        }
    }
}
