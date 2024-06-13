package Codigo;
import java.time.LocalDateTime;

public class RequisicaoMesa {
    private Cliente cliente;
    private int numeroPessoas;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Mesa mesa;

    public RequisicaoMesa(Cliente cliente, int numeroPessoas) {
        this.cliente = cliente;
        this.numeroPessoas = numeroPessoas;
        this.dataHoraEntrada = LocalDateTime.now();
        this.dataHoraSaida = null;
        this.mesa = null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public void setNumeroPessoas(int numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void alocarMesa(Mesa mesa) {
        this.mesa = mesa;
        mesa.ocupar(this);
    }

    public void liberarMesa() {
        if (this.mesa != null) {
            this.dataHoraSaida = LocalDateTime.now();
            this.mesa.desocupar();
            this.mesa = null;
        }
    }
}
