/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author valad
 */

import Controller.PedidoController;
import model.Menu;
import model.Restaurante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PainelAcoes extends JPanel {
    private PedidoController pedidoController;
    private Restaurante restaurante;

    public PainelAcoes(Restaurante restaurante) {
        this.pedidoController = new PedidoController(restaurante);
        this.restaurante = restaurante;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton btnFazerPedido = new JButton("Fazer Pedido");
        btnFazerPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaPedido();
            }
        });
        add(btnFazerPedido);

        JButton btnVerPedidos = new JButton("Ver Pedidos");
        btnVerPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verPedidos();
            }
        });
        add(btnVerPedidos);

        JButton btnVerVendas = new JButton("Ver Vendas e Recebimentos");
        btnVerVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verVendas();
            }
        });
        add(btnVerVendas);

        JButton btnDelivery = new JButton("Delivery");
        btnDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaDelivery();
            }
        });
        add(btnDelivery);
    }

    private void abrirJanelaPedido() {
        JDialog pedidoDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Fazer Pedido", true);
        pedidoDialog.setLayout(new BorderLayout());
        pedidoDialog.setSize(400, 300);

        // Selecionar mesa
        JPanel painelSelecionarMesa = new JPanel(new FlowLayout());
        painelSelecionarMesa.add(new JLabel("ID da Mesa:"));
        JTextField txtIdMesaPedido = new JTextField(5);
        painelSelecionarMesa.add(txtIdMesaPedido);
        JButton btnSelecionarMesa = new JButton("Selecionar Mesa");
        painelSelecionarMesa.add(btnSelecionarMesa);

        pedidoDialog.add(painelSelecionarMesa, BorderLayout.NORTH);

        // Lista do menu e entrada do pedido
        JTextArea areaMenu = new JTextArea();
        areaMenu.setEditable(false);
        JScrollPane scrollPaneMenu = new JScrollPane(areaMenu);
        pedidoDialog.add(scrollPaneMenu, BorderLayout.CENTER);

        JPanel painelPedido = new JPanel(new GridLayout(3, 2));
        painelPedido.add(new JLabel("Número do Item:"));
        JTextField txtNumeroItem = new JTextField(5);
        painelPedido.add(txtNumeroItem);
        painelPedido.add(new JLabel("Quantidade:"));
        JTextField txtQuantidade = new JTextField(5);
        painelPedido.add(txtQuantidade);

        JButton btnAdicionarItem = new JButton("Adicionar Item");
        painelPedido.add(btnAdicionarItem);
        pedidoDialog.add(painelPedido, BorderLayout.SOUTH);

        JButton btnCancelar = new JButton("Cancelar");
        painelPedido.add(btnCancelar);

        final int[] mesaSelecionada = {0};

        btnSelecionarMesa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mesaSelecionada[0] = Integer.parseInt(txtIdMesaPedido.getText());
                    StringBuilder menu = new StringBuilder("Menu:\n");
                    int index = 1;
                    for (Map.Entry<String, Double> entry : Menu.getItensMenu().entrySet()) {
                        menu.append(index).append(". ").append(entry.getKey()).append(" - R$").append(entry.getValue()).append("\n");
                        index++;
                    }
                    areaMenu.setText(menu.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(pedidoDialog, "Por favor, insira um ID válido de mesa.");
                }
            }
        });

        btnAdicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mesaSelecionada[0] == 0) {
                    JOptionPane.showMessageDialog(pedidoDialog, "Por favor, selecione uma mesa primeiro.");
                    return;
                }
                try {
                    int itemIndex = Integer.parseInt(txtNumeroItem.getText()) - 1;
                    int quantidade = Integer.parseInt(txtQuantidade.getText());

                    String resultado = pedidoController.adicionarItemPedido(mesaSelecionada[0], itemIndex, quantidade);
                    JOptionPane.showMessageDialog(pedidoDialog, resultado);

                    int resposta = JOptionPane.showConfirmDialog(pedidoDialog, "Deseja adicionar mais itens?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (resposta != JOptionPane.YES_NO_OPTION) {
                        pedidoDialog.dispose();
                    } else {
                        txtNumeroItem.setText("");
                        txtQuantidade.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(pedidoDialog, "Por favor, insira números válidos.");
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pedidoDialog.dispose();
            }
        });

        pedidoDialog.setVisible(true);
    }

    private void abrirJanelaDelivery() {
        JDialog deliveryDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Pedido Delivery", true);
        deliveryDialog.setLayout(new BorderLayout());
        deliveryDialog.setSize(400, 200);

        JPanel painelCliente = new JPanel();
        painelCliente.setLayout(new BoxLayout(painelCliente, BoxLayout.Y_AXIS));

        JPanel painelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNome.add(new JLabel("Nome do Cliente:"));
        JTextField txtNomeCliente = new JTextField(20);
        painelNome.add(txtNomeCliente);

        JPanel painelEndereco = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEndereco.add(new JLabel("Endereço:"));
        JTextField txtEndereco = new JTextField(20);
        painelEndereco.add(txtEndereco);

        painelCliente.add(painelNome);
        painelCliente.add(painelEndereco);

        JButton btnEnviar = new JButton("Enviar");
        painelCliente.add(btnEnviar);

        deliveryDialog.add(painelCliente, BorderLayout.CENTER);

        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCliente = txtNomeCliente.getText();
                String endereco = txtEndereco.getText();

                if (nomeCliente.isEmpty() || endereco.isEmpty()) {
                    JOptionPane.showMessageDialog(deliveryDialog, "Por favor, preencha todos os campos.");
                } else {
                    deliveryDialog.dispose();
                    abrirMenuDelivery(nomeCliente, endereco);
                }
            }
        });

        deliveryDialog.setVisible(true);
    }

    private void abrirMenuDelivery(String nomeCliente, String endereco) {
        JDialog menuDeliveryDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Menu Delivery", true);
        menuDeliveryDialog.setLayout(new BorderLayout());
        menuDeliveryDialog.setSize(400, 400);

        JTextArea areaMenu = new JTextArea();
        areaMenu.setEditable(false);
        JScrollPane scrollPaneMenu = new JScrollPane(areaMenu);
        menuDeliveryDialog.add(scrollPaneMenu, BorderLayout.CENTER);

        JPanel painelPedido = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPedido.add(new JLabel("Número do Item:"), gbc);

        gbc.gridx = 1;
        JTextField txtNumeroItem = new JTextField(5);
        painelPedido.add(txtNumeroItem, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPedido.add(new JLabel("Quantidade:"), gbc);

        gbc.gridx = 1;
        JTextField txtQuantidade = new JTextField(5);
        painelPedido.add(txtQuantidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnAdicionarItem = new JButton("Adicionar Item");
        painelPedido.add(btnAdicionarItem, gbc);

        gbc.gridy = 3;
        JButton btnFinalizar = new JButton("Finalizar Pedido");
        painelPedido.add(btnFinalizar, gbc);

        gbc.gridy = 4;
        JButton btnCancelar = new JButton("Cancelar");
        painelPedido.add(btnCancelar, gbc);

        menuDeliveryDialog.add(painelPedido, BorderLayout.SOUTH);

        final List<String> itens = new ArrayList<>();
        final List<Integer> quantidades = new ArrayList<>();

        StringBuilder menu = new StringBuilder("Menu:\n");
        int index = 1;
        for (Map.Entry<String, Double> entry : Menu.getItensMenu().entrySet()) {
            menu.append(index).append(". ").append(entry.getKey()).append(" - R$").append(entry.getValue()).append("\n");
            index++;
        }
        areaMenu.setText(menu.toString());

        btnAdicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int itemIndex = Integer.parseInt(txtNumeroItem.getText()) - 1;
                    String item = (String) Menu.getItensMenu().keySet().toArray()[itemIndex];
                    int quantidade = Integer.parseInt(txtQuantidade.getText());

                    itens.add(item);
                    quantidades.add(quantidade);

                    JOptionPane.showMessageDialog(menuDeliveryDialog, "Item adicionado: " + (itemIndex + 1) + ", Quantidade: " + quantidade);

                    int resposta = JOptionPane.showConfirmDialog(menuDeliveryDialog, "Deseja adicionar mais itens?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (resposta != JOptionPane.YES_OPTION) {
                        txtNumeroItem.setText("");
                        txtQuantidade.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(menuDeliveryDialog, "Por favor, insira números válidos.");
                }
            }
        });

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] metodosPagamento = {"Dinheiro", "Pix", "Débito", "Crédito"};
                String metodoPagamento = (String) JOptionPane.showInputDialog(
                        menuDeliveryDialog,
                        "Selecione o método de pagamento:",
                        "Método de Pagamento",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        metodosPagamento,
                        metodosPagamento[0]
                );

                if (metodoPagamento != null) {
                    double valorTotal = 0.0;
                    for (int i = 0; i < itens.size(); i++) {
                        valorTotal += Menu.getPreco(itens.get(i)) * quantidades.get(i);
                    }

                    restaurante.registrarVenda(valorTotal, metodoPagamento);
                    JOptionPane.showMessageDialog(menuDeliveryDialog, "Pedido de delivery concluído com sucesso.");
                    menuDeliveryDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(menuDeliveryDialog, "Por favor, selecione um método de pagamento.");
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDeliveryDialog.dispose();
            }
        });

        menuDeliveryDialog.setVisible(true);
    }

    private void verPedidos() {
        String idMesaStr = JOptionPane.showInputDialog(this, "Digite o ID da mesa:");
        try {
            int idMesa = Integer.parseInt(idMesaStr);
            String detalhesPedido = pedidoController.verPedidos(idMesa);
            JOptionPane.showMessageDialog(this, detalhesPedido);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido de mesa.");
        }
    }

    private void verVendas() {
        String mensagem = "Vendas do dia:\n" +
                "Pix: R$" + String.format("%.2f", restaurante.getVendasPix()) + "\n" +
                "Dinheiro: R$" + String.format("%.2f", restaurante.getVendasDinheiro()) + "\n\n" +
                "Recebimentos Futuros:\n" +
                "Débito (próximos 14 dias): R$" + String.format("%.2f", restaurante.getAReceberDebito()) + "\n" +
                "Crédito (próximos 30 dias): R$" + String.format("%.2f", restaurante.getAReceberCredito());

        JOptionPane.showMessageDialog(this, mensagem);
    }
}
