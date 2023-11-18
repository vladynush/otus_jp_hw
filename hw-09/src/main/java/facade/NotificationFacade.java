package facade;

import facade.notofocator.EmailNotificator;
import facade.notofocator.PushNotificator;
import facade.notofocator.SmsNotificator;

public class NotificationFacade {

    public String sendNotification(NotificationType notificationType, String notificationTo,
                                   String notificationFrom, String text) {
        return switch (notificationType) {
            case EMAIL -> new EmailNotificator(notificationTo, notificationFrom)
                    .sendNotification(text);
            case PUSH -> new PushNotificator().sendNotification(notificationFrom, notificationTo, text);
            case SMS -> new SmsNotificator(notificationFrom).sendNotification(notificationTo, text);
        };
    }
}
