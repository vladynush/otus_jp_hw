package facade.notofocator;

public class SmsNotificator {

    private final String smsFrom;

    public SmsNotificator(String smsFrom) {
        this.smsFrom = smsFrom;
    }

    public String sendNotification(String smsTo, String smsText) {
        return String.format("Sending sms from %s to %s with text: %s", smsFrom, smsTo, smsText);
    }
}
