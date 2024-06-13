package Codigo;
import java.util.LinkedList;
import java.util.Queue;

public class FilaDeEspera {
    private Queue<RequisicaoMesa> fila;

    public FilaDeEspera() {
        this.fila = new LinkedList<>();
    }

    public Queue<RequisicaoMesa> getFila() {
        return fila;
    }

    public void setFila(Queue<RequisicaoMesa> fila) {
        this.fila = fila;
    }

    public void adicionar(RequisicaoMesa requisicao) {
        fila.add(requisicao);
    }

    public RequisicaoMesa remover() {
        return fila.poll();
    }

    public boolean temEspera() {
        return !fila.isEmpty();
    }
}
