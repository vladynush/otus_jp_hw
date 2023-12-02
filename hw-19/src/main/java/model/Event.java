package model;

public class Event {

    private final EventType type;
    private final String value;

    public Event(EventType type, String value) {
        this.type = type;
        this.value = value;
    }

    public EventType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }
}
