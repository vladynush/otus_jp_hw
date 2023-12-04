package service;

import model.Event;
import model.EventType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Observer implements Runnable {

    private static final String INSERTED_FILE = "hw-19\\src\\main\\resources\\inserted.txt";
    private static final String DELETED_FILE = "hw-19\\src\\main\\resources\\deleted.txt";
    private final ObservedList observedList;

    public Observer(ObservedList observedList) {
        this.observedList = observedList;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Event event = observedList.getEventQueue().take();
                if (event.getType() == EventType.INSERTED) {
                    writeValueToFile(INSERTED_FILE, event.getValue());
                } else if (event.getType() == EventType.DELETED) {
                    writeValueToFile(DELETED_FILE, event.getValue());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void writeValueToFile(String path, String value){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(value + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
