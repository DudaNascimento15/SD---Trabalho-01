import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Processo extends Thread {
    private final int id;
    private final Coordenador coordenador;
    private final Random random = new Random();
    private final AtomicBoolean executando = new AtomicBoolean(true);

    public Processo(int id, Coordenador coordenador) {
        this.id = id;
        this.coordenador = coordenador;
        setName("Processo-" + id);
    }

    public int getIdProcesso() {
        return id;
    }

    public void pararProcesso() {
        executando.set(false);
        interrupt();
    }

    @Override
    public void run() {
        try {
            while (executando.get()) {
                // Espera aleatória entre 10 a 25 segundos para tentar acessar o recurso
                int esperaParaRequisitar = 10 + random.nextInt(16);
                System.out.println("[" + getName() + "] Esperando " + esperaParaRequisitar + "s para tentar acessar.");
                TimeUnit.SECONDS.sleep(esperaParaRequisitar);

                if (!coordenador.estaVivo()) {
                    System.out.println("[" + getName() + "] Coordenador está morto, aguardando novo coordenador.");
                    while (!coordenador.estaVivo()) {
                        TimeUnit.SECONDS.sleep(1);
                    }
                }

                boolean requisitado = coordenador.requisitarRecurso(this);
                if (!requisitado) {
                    continue;
                }

                Processo processoConcedido;
                synchronized (coordenador) {
                    processoConcedido = coordenador.garantirAcesso();
                }

                if (processoConcedido != null && processoConcedido.getIdProcesso() == this.id) {
                    int tempoProcessamento = 5 + random.nextInt(11); // espera entre 5 e 15 segundos para processar o recurso
                    System.out.println("[" + getName() + "] Processando recurso por " + tempoProcessamento + "s.");
                    TimeUnit.SECONDS.sleep(tempoProcessamento);
                    coordenador.liberarRecurso(this);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("[" + getName() + "] Interrompido.");
        }
        System.out.println("[" + getName() + "] Finalizando.");
    }
}
