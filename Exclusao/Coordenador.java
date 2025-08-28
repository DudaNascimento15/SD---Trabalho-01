import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Coordenador {
    private final Queue<Processo> fila = new LinkedList<>();
    private final AtomicBoolean vivo = new AtomicBoolean(true);
    private final AtomicBoolean recursoOcupado = new AtomicBoolean(false);

    public synchronized boolean requisitarRecurso(Processo p) {
        if (!vivo.get()) {
            System.out.println("[Coordenador] Coordenador está morto. Pedido de " + p.getIdProcesso() + " negado.");
            return false;
        }  
        System.out.println("[Coordenador] Processo " + p.getIdProcesso() + " pediu acesso.");
        fila.offer(p);
        notifyAll();
        return true;
    }

    public synchronized void liberarRecurso(Processo p) {
        recursoOcupado.set(false);
        System.out.println("[Coordenador] Processo " + p.getIdProcesso() + " liberou o recurso.");
        notifyAll();
    }

    public synchronized Processo garantirAcesso() throws InterruptedException {
        while (fila.isEmpty() || recursoOcupado.get() || !vivo.get()) {
            wait();
            if (!vivo.get()) return null;
        }
        Processo proximo = fila.poll();
        recursoOcupado.set(true);
        System.out.println("[Coordenador] Recurso concedido para " + proximo.getIdProcesso());
        return proximo;
    }

    public void matar() {
        vivo.set(false);
        synchronized(this) {
            fila.clear();
            notifyAll();
        }
        System.out.println("[Coordenador] Coordenador morreu. Fila apagada.");
    }

    public boolean estaVivo() {
        return vivo.get();
    }

    public void renascer() {
        vivo.set(true);
        synchronized(this) {
            notifyAll();
        }
        System.out.println("[Coordenador] Coordenador renasceu.");
    }  
}
