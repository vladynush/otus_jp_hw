package facade.notofocator;

public class PushNotificator {

    public String sendNotification(String pushFrom, String pushTo, String pushText) {
        return String.format(
                "Push notification from '%s' app to '%s' user with text: '%s'", pushFrom, pushTo, pushText
        );
    }
}
