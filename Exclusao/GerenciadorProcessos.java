import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class GerenciadorProcessos {
    private final Map<Integer, Processo> processos = new ConcurrentHashMap<>();
    private final Coordenador coordenador;
    private final ExecutorService executor;
    private final Random random = new Random();

    public GerenciadorProcessos(Coordenador coordenador, ExecutorService executor) {
        this.coordenador = coordenador;
        this.executor = executor;
    }

    public Processo criarNovoProcesso() {
        int id;
        do {
            id = random.nextInt(1000);
        } while (processos.containsKey(id));

        Processo p = new Processo(id, coordenador);
        processos.put(id, p);
        executor.submit(p);
        System.out.println("[Gerenciador] Criou novo processo com ID: " + id);
        return p;
    }

    public Map<Integer, Processo> getProcessos() {
        return processos;
    }

    public void pararTodosProcessos() {
        for (Processo p : processos.values()) {
            p.pararProcesso();
        }
    }
}
