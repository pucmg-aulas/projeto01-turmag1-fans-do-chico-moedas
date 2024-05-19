import java.io.Serializable;
import java.time.LocalDateTime;

class RequisicaoMesa implements Serializable {
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private Mesa mesa;
    private boolean atendida;
    private LocalDateTime entrada;

    public RequisicaoMesa(Cliente cliente, Mesa mesa, LocalDateTime entrada) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.entrada = entrada;
        this.atendida = false;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }
}
