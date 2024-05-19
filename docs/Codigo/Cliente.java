import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int numPessoas;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private List<Pedido> pedidos;

    public Cliente(String nome, int numPessoas, LocalDateTime entrada) {
        this.nome = nome;
        this.numPessoas = numPessoas;
        this.entrada = entrada;
        this.pedidos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getNumPessoas() {
        return numPessoas;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }
}
