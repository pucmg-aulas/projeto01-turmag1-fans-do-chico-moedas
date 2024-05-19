import java.io.Serializable;

public class Conta implements Serializable {
    private double total;
    private double taxaServico;

    public Conta(double total) {
        this.total = total;
        this.taxaServico = total * 0.1; // 10% de taxa de serviço
    }

    public double getTotal() {
        return total;
    }

    public double getTaxaServico() {
        return taxaServico;
    }

    public double getTotalComTaxa() {
        return total + taxaServico;
    }

    public double getValorPorPessoa(int numPessoas) {
        return getTotalComTaxa() / numPessoas;
    }
}
