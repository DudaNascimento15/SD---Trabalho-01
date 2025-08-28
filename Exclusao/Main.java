import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Coordenador coordenador = new Coordenador();
        ExecutorService executor = Executors.newCachedThreadPool();
        GerenciadorProcessos gerenciador = new GerenciadorProcessos(coordenador, executor);

        // Cria 3 processos iniciais
        for (int i = 0; i < 3; i++) {
            gerenciador.criarNovoProcesso();
        }

        // Thread para "matar" coordenador a cada 60 segundos e renascer
        Thread gerenciadorCoordenador = new Thread(() -> {
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(60);
                    System.out.println("[Principal] Matando coordenador...");
                    coordenador.matar();

                    // Simula o "renascimento" do coordenador
                    TimeUnit.SECONDS.sleep(5);
                    coordenador.renascer();
                }
            } catch (InterruptedException e) {
                // Thread encerrada
            }
        });
        gerenciadorCoordenador.setDaemon(true);
        gerenciadorCoordenador.start();

        // Thread para criar um novo processo a cada 40 segundos
        Thread criadorProcessos = new Thread(() -> {
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(40);
                    gerenciador.criarNovoProcesso();
                }
            } catch (InterruptedException e) {
                // Thread encerrada
            }
        });
        criadorProcessos.setDaemon(true);
        criadorProcessos.start();

        // Executa por 3 minutos
        TimeUnit.MINUTES.sleep(3);

        // Finaliza tudo
        System.out.println("[Principal] Finalizando execução...");
        gerenciadorCoordenador.interrupt();
        criadorProcessos.interrupt();

        gerenciador.pararTodosProcessos();
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("[Principal] Programa finalizado.");
    }
}
