import java.io.Serializable;

public class Pedido implements Serializable {
    private String item;
    private double preco;

    public Pedido(String item, double preco) {
        this.item = item;
        this.preco = preco;
    }

    public String getItem() {
        return item;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return item + " - R$" + preco;
    }
}
