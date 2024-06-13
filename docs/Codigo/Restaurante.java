package Codigo;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private List<Mesa> mesas;
    private FilaDeEspera filaDeEspera;

    public Restaurante() {
        this.mesas = new ArrayList<>();
        int numeroMesa = 1;
        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(numeroMesa++, 4));
        }
        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(numeroMesa++, 6));
        }
        for (int i = 0; i < 2; i++) {
            mesas.add(new Mesa(numeroMesa++, 8));
        }
        this.filaDeEspera = new FilaDeEspera();
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    public FilaDeEspera getFilaDeEspera() {
        return filaDeEspera;
    }

    public void setFilaDeEspera(FilaDeEspera filaDeEspera) {
        this.filaDeEspera = filaDeEspera;
    }

    public void receberRequisicao(RequisicaoMesa requisicao) {
        Mesa mesa = encontrarMesaProxima(requisicao.getNumeroPessoas());
        if (mesa != null) {
            requisicao.alocarMesa(mesa);
        } else {
            filaDeEspera.adicionar(requisicao);
        }
    }

    private Mesa encontrarMesaProxima(int numeroPessoas) {
        Mesa mesaEncontrada = null;
        for (Mesa mesa : mesas) {
            if (!mesa.isOcupada() && mesa.getCapacidade() >= numeroPessoas) {
                if (mesaEncontrada == null || mesa.getCapacidade() < mesaEncontrada.getCapacidade()) {
                    mesaEncontrada = mesa;
                }
            }
        }

        if (mesaEncontrada == null) {
            for (Mesa mesa : mesas) {
                if (!mesa.isOcupada()) {
                    mesaEncontrada = mesa;
                    break;
                }
            }
        }

        return mesaEncontrada;
    }

    public void liberarMesa(RequisicaoMesa requisicao) {
        requisicao.liberarMesa();
        if (filaDeEspera.temEspera()) {
            RequisicaoMesa proxRequisicao = filaDeEspera.remover();
            receberRequisicao(proxRequisicao);
        }
    }

    public Mesa encontrarMesaPorNumero(int numero) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == numero) {
                return mesa;
            }
        }
        return null;
    }
}
