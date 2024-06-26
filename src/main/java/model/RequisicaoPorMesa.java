/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author valad
 */



import java.io.Serializable;
import java.time.LocalDateTime;

public class RequisicaoPorMesa implements Serializable {
    private int id;
    private String nomeCliente;
    private int numeroPessoas;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private Mesa mesa;

    public RequisicaoPorMesa(int id, String nomeCliente, int numeroPessoas) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.numeroPessoas = numeroPessoas;
        this.horaEntrada = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
}
