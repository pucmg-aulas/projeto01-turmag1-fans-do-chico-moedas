package Codigo;

public class Mesa {
    private int numero;
    private int capacidade;
    private boolean ocupada;
    private RequisicaoMesa requisicao;
    private Conta conta;

    public Mesa(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.ocupada = false;
        this.requisicao = null;
        this.conta = new Conta();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public RequisicaoMesa getRequisicao() {
        return requisicao;
    }

    public void ocupar(RequisicaoMesa requisicao) {
        this.ocupada = true;
        this.requisicao = requisicao;
    }

    public void desocupar() {
        this.ocupada = false;
        this.requisicao = null;
    }

    public Conta getConta() {
        return conta;
    }
}
