package adapter;


import adapter.payservice.PayPal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayPalToSitePaymentAdapter implements SitePayment {

    private final PayPal payPal;

    public PayPalToSitePaymentAdapter(PayPal payPal) {
        this.payPal = payPal;
    }

    @Override
    public void runPayment(int amount) {
        log.info("Switching on adapter for pay pal payment at site...");
        payPal.runPayPalPayment(amount);
        log.info("Payment done");
    }
}
