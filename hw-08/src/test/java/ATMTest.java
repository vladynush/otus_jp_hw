import model.Banknote;
import org.junit.jupiter.api.Test;
import service.ATMService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    @Test
    public void testInsertValueInEmptyATM() {
        ATMService atm = new ATMService();
        List<Banknote> banknotes = Arrays.asList(Banknote.THOUSAND, Banknote.HUNDRED, Banknote.HUNDRED);
        atm.insertValue(banknotes);
        assertEquals(1200, atm.getBalance());
        atm.insertValue(Arrays.asList(Banknote.THOUSAND, Banknote.HUNDRED));
        assertEquals(2300, atm.getBalance());
        atm.insertValue(Arrays.asList(Banknote.FIVE_HUNDRED, Banknote.TEN));
        assertEquals(2810, atm.getBalance());
    }

    @Test
    public void testInsertValueInPredefinedATM() {
        Map<Banknote, Long> atmInitState = new HashMap<>();
        atmInitState.put(Banknote.FIVE_THOUSAND, 2L);
        atmInitState.put(Banknote.FIFTY, 2L);
        ATMService atm1 = new ATMService(atmInitState);
        assertEquals(10100, atm1.getBalance());
        atm1.insertValue(List.of(Banknote.FIVE_HUNDRED));
        assertEquals(10600, atm1.getBalance());
    }

    @Test
    public void testWithdrawValueFromATM() {
        Map<Banknote, Long> atmInitState = new HashMap<>();
        atmInitState.put(Banknote.FIFTY, 2L);
        ATMService atm = new ATMService(atmInitState);
        assertThrows(IllegalArgumentException.class, () -> atm.withdrawValue(10));
        assertThrows(IllegalArgumentException.class, () -> atm.withdrawValue(500));
        assertThrows(IllegalArgumentException.class, () -> atm.withdrawValue(-1));
        assertDoesNotThrow(() -> atm.withdrawValue(50));
        assertEquals(50, atm.getBalance());
    }

    @Test
    public void testWithdrawValueFromEmptyATM() {
        ATMService atm = new ATMService();
        assertThrows(IllegalArgumentException.class, () -> atm.withdrawValue(10));
    }
}
