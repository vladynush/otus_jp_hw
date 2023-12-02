import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hw18Application {

    public static void main(String[] args) {
        CyclicBarrier runBarrier = new CyclicBarrier(2);
        CyclicBarrier printBarrier = new CyclicBarrier(2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Printer(1, runBarrier, printBarrier));
        executorService.submit(new Printer(2, runBarrier, printBarrier));

        executorService.shutdown();
    }
}