/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author valad
 */


import model.Mesa;
import java.util.List;

public class MesaDAO extends AbstractDAO {
    private final String arquivo = "mesas.dat";

    public void gravarMesas(List<Mesa> mesas) {
        gravar(arquivo, mesas);
    }

    public List<Mesa> recuperarMesas() {
        return recuperar(arquivo);
    }
}
