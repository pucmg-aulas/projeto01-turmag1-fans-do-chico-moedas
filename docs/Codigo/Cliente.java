import java.io.Serializable;
import java.time.LocalDateTime;

class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int numPessoas;
    private LocalDateTime entrada;

    public Cliente(String nome, int numPessoas, LocalDateTime entrada) {
        this.nome = nome;
        this.numPessoas = numPessoas;
        this.entrada = entrada;
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
}