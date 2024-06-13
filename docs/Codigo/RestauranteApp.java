package Codigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RestauranteApp extends JFrame {
    private Restaurante restaurante;
    private Cardapio cardapio;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JTextArea outputArea;
    private JTextField clienteNomeField;
    private JTextField numeroPessoasField;
    private JTextField mesaNumeroFieldPedido;
    private JTextField itemNomeField;
    private JTextField itemQuantidadeField;
    private JTextField mesaNumeroFieldConta;

    public RestauranteApp() {
        restaurante = new Restaurante();
        cardapio = new Cardapio();

        setTitle("Restaurante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Tela inicial
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        JButton alocarClienteButton = new JButton("Alocar Cliente");
        alocarClienteButton.addActionListener(e -> cardLayout.show(cardPanel, "alocarCliente"));
        mainPanel.add(alocarClienteButton);

        JButton realizarPedidoButton = new JButton("Realizar Pedido");
        realizarPedidoButton.addActionListener(e -> cardLayout.show(cardPanel, "realizarPedido"));
        mainPanel.add(realizarPedidoButton);

        JButton verMesasButton = new JButton("Ver Mesas");
        verMesasButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "verMesas");
            atualizarMesas();
        });
        mainPanel.add(verMesasButton);

        JButton fecharContaButton1 = new JButton("Fechar Conta");
        fecharContaButton1.addActionListener(e -> cardLayout.show(cardPanel, "fecharConta"));
        mainPanel.add(fecharContaButton1);

        // Painel de alocar cliente
        JPanel alocarClientePanel = new JPanel();
        GroupLayout layout = new GroupLayout(alocarClientePanel);
        alocarClientePanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel clienteNomeLabel = new JLabel("Nome do Cliente:");
        clienteNomeField = new JTextField();
        JLabel numeroPessoasLabel = new JLabel("Número de Pessoas:");
        numeroPessoasField = new JTextField();
        JButton addClienteButton = new JButton("Adicionar Cliente");
        addClienteButton.addActionListener(new AddClienteAction());
        JButton voltarButton1 = new JButton("Voltar");
        voltarButton1.addActionListener(e -> cardLayout.show(cardPanel, "mainPanel"));

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(clienteNomeLabel)
                .addComponent(numeroPessoasLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(clienteNomeField)
                .addComponent(numeroPessoasField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(addClienteButton)
                .addComponent(voltarButton1))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(clienteNomeLabel)
                .addComponent(clienteNomeField)
                .addComponent(addClienteButton))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(numeroPessoasLabel)
                .addComponent(numeroPessoasField)
                .addComponent(voltarButton1))
        );

        // Painel de realizar pedido
        JPanel realizarPedidoPanel = new JPanel();
        GroupLayout layoutPedido = new GroupLayout(realizarPedidoPanel);
        realizarPedidoPanel.setLayout(layoutPedido);
        layoutPedido.setAutoCreateGaps(true);
        layoutPedido.setAutoCreateContainerGaps(true);

        JLabel mesaNumeroLabelPedido = new JLabel("Número da Mesa:");
        mesaNumeroFieldPedido = new JTextField();
        JLabel itemNomeLabel = new JLabel("Nome do Item:");
        itemNomeField = new JTextField();
        JLabel itemQuantidadeLabel = new JLabel("Quantidade do Item:");
        itemQuantidadeField = new JTextField();
        JButton addItemButton = new JButton("Adicionar Item");
        addItemButton.addActionListener(new RealizarPedidoAction());
        JButton voltarButton2 = new JButton("Voltar");
        voltarButton2.addActionListener(e -> cardLayout.show(cardPanel, "mainPanel"));

        layoutPedido.setHorizontalGroup(layoutPedido.createSequentialGroup()
            .addGroup(layoutPedido.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mesaNumeroLabelPedido)
                .addComponent(itemNomeLabel)
                .addComponent(itemQuantidadeLabel))
            .addGroup(layoutPedido.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mesaNumeroFieldPedido)
                .addComponent(itemNomeField)
                .addComponent(itemQuantidadeField))
            .addGroup(layoutPedido.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(addItemButton)
                .addComponent(voltarButton2))
        );

        layoutPedido.setVerticalGroup(layoutPedido.createSequentialGroup()
            .addGroup(layoutPedido.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(mesaNumeroLabelPedido)
                .addComponent(mesaNumeroFieldPedido)
                .addComponent(addItemButton))
            .addGroup(layoutPedido.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemNomeLabel)
                .addComponent(itemNomeField))
            .addGroup(layoutPedido.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemQuantidadeLabel)
                .addComponent(itemQuantidadeField)
                .addComponent(voltarButton2))
        );

        // Painel de ver mesas
        JPanel verMesasPanel = new JPanel(new BorderLayout());
        JTextArea mesasArea = new JTextArea();
        mesasArea.setEditable(false);
        JScrollPane mesasScrollPane = new JScrollPane(mesasArea);
        verMesasPanel.add(mesasScrollPane, BorderLayout.CENTER);
        JButton voltarButton3 = new JButton("Voltar");
        voltarButton3.addActionListener(e -> cardLayout.show(cardPanel, "mainPanel"));
        verMesasPanel.add(voltarButton3, BorderLayout.SOUTH);

        // Painel de fechar conta
        JPanel fecharContaPanel = new JPanel();
        GroupLayout layoutConta = new GroupLayout(fecharContaPanel);
        fecharContaPanel.setLayout(layoutConta);
        layoutConta.setAutoCreateGaps(true);
        layoutConta.setAutoCreateContainerGaps(true);

        JLabel mesaNumeroLabelConta = new JLabel("Número da Mesa:");
        mesaNumeroFieldConta = new JTextField();
        JButton fecharContaButton = new JButton("Fechar Conta");
        fecharContaButton.addActionListener(new FecharContaAction());
        JButton voltarButton4 = new JButton("Voltar");
        voltarButton4.addActionListener(e -> cardLayout.show(cardPanel, "mainPanel"));

        layoutConta.setHorizontalGroup(layoutConta.createSequentialGroup()
            .addGroup(layoutConta.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mesaNumeroLabelConta))
            .addGroup(layoutConta.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(mesaNumeroFieldConta))
            .addGroup(layoutConta.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(fecharContaButton)
                .addComponent(voltarButton4))
        );

        layoutConta.setVerticalGroup(layoutConta.createSequentialGroup()
            .addGroup(layoutConta.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(mesaNumeroLabelConta)
                .addComponent(mesaNumeroFieldConta)
                .addComponent(fecharContaButton))
            .addComponent(voltarButton4)
        );

        // Adicionar os painéis ao cardPanel
        cardPanel.add(mainPanel, "mainPanel");
        cardPanel.add(alocarClientePanel, "alocarCliente");
        cardPanel.add(realizarPedidoPanel, "realizarPedido");
        cardPanel.add(verMesasPanel, "verMesas");
        cardPanel.add(fecharContaPanel, "fecharConta");

        add(cardPanel);

        // Inicializar na tela principal
        cardLayout.show(cardPanel, "mainPanel");
    }

    private void atualizarMesas() {
        StringBuilder mesasOcupadas = new StringBuilder("Mesas ocupadas:\n");
        StringBuilder mesasLivres = new StringBuilder("\nMesas livres:\n");
        for (Mesa mesa : restaurante.getMesas()) {
            if (mesa.isOcupada()) {
                mesasOcupadas.append("Mesa ").append(mesa.getNumero()).append(": ").append(mesa.getRequisicao().getCliente().getNome())
                        .append(", ").append(mesa.getRequisicao().getNumeroPessoas()).append(" pessoas (capacidade para ")
                        .append(mesa.getCapacidade()).append(" pessoas).\n");
            } else {
                mesasLivres.append("Mesa ").append(mesa.getNumero()).append(" de ").append(mesa.getCapacidade()).append(" pessoas está livre.\n");
            }
        }
        JTextArea mesasArea = new JTextArea(mesasOcupadas.toString() + mesasLivres.toString());
        mesasArea.setEditable(false);
        JScrollPane mesasScrollPane = new JScrollPane(mesasArea);
        cardPanel.add(mesasScrollPane, "verMesas");
    }

    private class AddClienteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = clienteNomeField.getText();
            int numeroPessoas = Integer.parseInt(numeroPessoasField.getText());
            Cliente cliente = new Cliente(nome);
            RequisicaoMesa requisicao = new RequisicaoMesa(cliente, numeroPessoas);

            restaurante.receberRequisicao(requisicao);
            outputArea.append("Requisição adicionada.\n");

            if (requisicao.getMesa() != null) {
                outputArea.append("Cliente alocado na mesa " + requisicao.getMesa().getNumero() + ".\n");
            } else {
                outputArea.append("Não há mesas disponíveis. Cliente adicionado à fila de espera.\n");
            }
            cardLayout.show(cardPanel, "mainPanel");
        }
    }

    private class RealizarPedidoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int numeroMesa = Integer.parseInt(mesaNumeroFieldPedido.getText());
            Mesa mesa = restaurante.encontrarMesaPorNumero(numeroMesa);
            if (mesa != null && mesa.isOcupada()) {
                Pedido pedido = mesa.getConta().getPedido();
                String item = itemNomeField.getText();
                int quantidade = Integer.parseInt(itemQuantidadeField.getText());

                pedido.adicionarItem(item, quantidade);
                outputArea.append("Item adicionado ao pedido.\n");
            } else {
                outputArea.append("Mesa não encontrada ou não está ocupada.\n");
            }
            cardLayout.show(cardPanel, "mainPanel");
        }
    }

    private class FecharContaAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int numeroMesa = Integer.parseInt(mesaNumeroFieldConta.getText());
            Mesa mesa = restaurante.encontrarMesaPorNumero(numeroMesa);
            if (mesa != null && mesa.isOcupada()) {
                mesa.getConta().calcularConta(cardapio, mesa.getRequisicao().getNumeroPessoas());
                mesa.getConta().exibirConta();
                restaurante.liberarMesa(mesa.getRequisicao());
                outputArea.append("Conta fechada e mesa liberada.\n");
            } else {
                outputArea.append("Mesa não encontrada ou não está ocupada.\n");
            }
            cardLayout.show(cardPanel, "mainPanel");
        }
    }

    public static void main(String[] args) {
        RestauranteApp app = new RestauranteApp();
        app.setVisible(true);
    }
}
