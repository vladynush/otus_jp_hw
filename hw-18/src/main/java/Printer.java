import java.util.concurrent.CyclicBarrier;

class Printer implements Runnable {

    private final int id;
    private final CyclicBarrier runBarrier;
    private final CyclicBarrier printBarrier;

    Printer(int id, CyclicBarrier runBarrier, CyclicBarrier printBarrier) {
        this.id = id;
        this.runBarrier = runBarrier;
        this.printBarrier = printBarrier;
    }

    @Override
    public void run() {
        try {
            runBarrier.await();
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 1; i <= 10; i++) {
                    if (id == 1) {
                        printNumbers(i);
                    }
                    printBarrier.await();

                    if (id == 2) {
                        printNumbers(i);
                    }
                    printBarrier.await();
                }

                for (int i = 9; i >= 0; i--) {
                    if (id == 1) {
                        printNumbers(i);
                    }
                    printBarrier.await();
                    if (id == 2) {
                        printNumbers(i);
                    }
                    printBarrier.await();
                }
                Thread.currentThread().interrupt();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printNumbers(int i) {
        System.out.println("Поток " + id + ":" + i);
    }
}