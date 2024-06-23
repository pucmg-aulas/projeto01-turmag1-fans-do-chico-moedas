/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author valad
 */

import model.Mesa;
import model.RequisicaoPorMesa;
import model.Restaurante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class MesaController {
    private Restaurante restaurante;

    public MesaController(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String liberarMesa(int idMesa) {
        Mesa mesa = restaurante.getMesas().stream()
                .filter(m -> m.getId() == idMesa && m.isOcupada())
                .findFirst()
                .orElse(null);

        if (mesa != null) {
            RequisicaoPorMesa requisicao = mesa.getRequisicao();
            LocalDateTime horaSaida = LocalDateTime.now();
            requisicao.setHoraSaida(horaSaida);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String[] metodosPagamento = {"Dinheiro", "Pix", "Débito", "Crédito"};
            String metodoPagamento = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecione o método de pagamento:",
                    "Método de Pagamento",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    metodosPagamento,
                    metodosPagamento[0]
            );

            mesa.getPedido().setMetodoPagamento(metodoPagamento);
            double valorRecebido = mesa.getPedido().calcularValorRecebido();
            int prazoRecebimento = mesa.getPedido().calcularPrazoRecebimento();

            restaurante.registrarVenda(valorRecebido, metodoPagamento);

            String resultado = "Mesa " + idMesa + " liberada. Horário de saída: " + horaSaida.format(formatter) +
                    "\nValor Total: R$" + mesa.getPedido().getValorTotalFormatado() +
                    "\nValor com Taxa: R$" + mesa.getPedido().getValorComTaxaFormatado() +
                    "\nValor por Pessoa: R$" + mesa.getPedido().getValorPorPessoaFormatado(requisicao.getNumeroPessoas()) +
                    "\nMétodo de Pagamento: " + metodoPagamento +
                    "\nValor Recebido: R$" + mesa.getPedido().calcularValorRecebidoFormatado() +
                    "\nPrazo para Recebimento: " + prazoRecebimento + " dias";

            restaurante.liberarMesa(mesa);
            return resultado;
        } else {
            return "Mesa não encontrada ou já está liberada.";
        }
    }
}
