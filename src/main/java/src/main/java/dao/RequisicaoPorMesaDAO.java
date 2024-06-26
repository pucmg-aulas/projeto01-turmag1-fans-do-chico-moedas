/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author valad
 */

import model.RequisicaoPorMesa;
import java.util.List;

public class RequisicaoPorMesaDAO extends AbstractDAO {
    private final String arquivo = "requisicoes.dat";

    public void gravarRequisicoes(List<RequisicaoPorMesa> requisicoes) {
        gravar(arquivo, requisicoes);
    }

    public List<RequisicaoPorMesa> recuperarRequisicoes() {
        return recuperar(arquivo);
    }
}

