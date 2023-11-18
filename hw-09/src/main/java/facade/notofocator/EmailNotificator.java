package facade.notofocator;

public class EmailNotificator {

    private final String to;
    private final String from;

    public EmailNotificator(String to, String from) {
        this.to = to;
        this.from = from;
    }

    public String sendNotification(String emailText) {
        return String.format("From: %s\nTo: %s\nText: %s", from, to, emailText);
    }
}
