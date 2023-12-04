import service.ObservedList;
import service.Observer;

public class Hw19Application {
    public static void main(String[] args) throws InterruptedException {
        ObservedList observedList = new ObservedList();
        Observer observer = new Observer(observedList);
        Thread observerThread = new Thread(observer);

        observerThread.start();
        observedList.insert("I know");
        observedList.insert("what U did");
        observedList.delete("last");
        observedList.delete("summer");
        Thread.sleep(1000);
        observerThread.interrupt();

        observerThread.join();
    }
}