package service;

import model.Event;
import model.EventType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ObservedList {

    private final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
    private final BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    public void insert(String value) {
        list.add(value);
        eventQueue.add(new Event(EventType.INSERTED, value));
    }

    public void delete(String value) {
        list.remove(value);
        eventQueue.add(new Event(EventType.DELETED, value));
    }

    public BlockingQueue<Event> getEventQueue() {
        return eventQueue;
    }
}
