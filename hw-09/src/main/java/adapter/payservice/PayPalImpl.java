package adapter.payservice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayPalImpl implements PayPal {

    @Override
    public void runPayPalPayment(int amount) {
        log.info("Running {} dollars pay pal payment\n", amount);
    }
}
