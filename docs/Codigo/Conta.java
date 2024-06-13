package Codigo;

public class Conta {
    private Pedido pedido;
    private double totalComTaxa;
    private double valorPorPessoa;

    public Conta() {
        this.pedido = new Pedido();
        this.totalComTaxa = 0;
        this.valorPorPessoa = 0;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public double getTotalComTaxa() {
        return totalComTaxa;
    }

    public double getValorPorPessoa() {
        return valorPorPessoa;
    }

    public void calcularConta(Cardapio cardapio, int numeroPessoas) {
        double total = pedido.calcularTotal(cardapio);
        this.totalComTaxa = total * 1.10;  // Adiciona 10% de taxa de serviço
        this.valorPorPessoa = this.totalComTaxa / numeroPessoas;
    }

    public void exibirConta() {
        pedido.exibirPedido();
        System.out.println("Total com taxa de serviço: " + totalComTaxa + " reais");
        System.out.println("Valor por pessoa: " + valorPorPessoa + " reais");
    }
}
