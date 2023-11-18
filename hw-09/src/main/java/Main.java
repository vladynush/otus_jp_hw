import adapter.PayPalToSitePaymentAdapter;
import adapter.payservice.PayPal;
import adapter.payservice.PayPalImpl;
import bridge.color.BlueColor;
import bridge.color.Color;
import bridge.color.RedColor;
import bridge.shape.Rectangle;
import bridge.shape.Shape;
import bridge.shape.Triangle;
import decorator.Aperitif;
import decorator.LargePortion;
import decorator.Order;
import decorator.OrdinaryOrder;
import facade.NotificationFacade;
import facade.NotificationType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Start decorator demo");
        decoratorDemo();
        log.info("Start bridge demo");
        bridgeDemo();
        log.info("Start  facade demo");
        facadeDemo();
        log.info("Start adapter demo");
        adapterDemo();
    }

    private static void decoratorDemo() {
        Order ordinaryOrder =
                new OrdinaryOrder(Arrays.asList("Caesar salad", "Chicken"), Arrays.asList("Long Island", "Cola"));
        Order aperitifOrd = new Aperitif(ordinaryOrder);
        Order largeOrd = new LargePortion(aperitifOrd);
        System.out.println(largeOrd.drinks());
        System.out.println(largeOrd.dishes());
    }

    private static void bridgeDemo() {
        Color red = new RedColor();
        Color blue = new BlueColor();
        Shape redRectangle = new Rectangle(red);
        Shape blueRectangle = new Rectangle(blue);
        Shape redTriangle = new Triangle(red);
        log.info(redTriangle.draw());
        log.info(blueRectangle.draw());
        log.info(redRectangle.draw());
    }

    public static void facadeDemo() {
        log.info(
                new NotificationFacade()
                        .sendNotification(
                                NotificationType.EMAIL,
                                "Alice",
                                "Bob",
                                "Hi, Alice! How are you?"
                        )
        );
        log.info(
                new NotificationFacade()
                        .sendNotification(NotificationType.SMS,
                                "Bob",
                                "Delivery",
                                "Your pizza is on the way!"
                        )
        );
        log.info(
                new NotificationFacade()
                        .sendNotification(NotificationType.PUSH,
                                "Alice",
                                "Navigator",
                                "You are 10 min far from home"
                        )
        );
    }

    private static void adapterDemo() {
        PayPal payPal = new PayPalImpl();
        log.info("Running payment at site with pay pal started...");
        new PayPalToSitePaymentAdapter(payPal).runPayment(5);
    }
}