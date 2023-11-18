package decorator;

import java.util.ArrayList;
import java.util.List;

public class Aperitif extends OrderOptionDecorator {

    public Aperitif(Order order) {
        super(order);
    }

    @Override
    public List<String> dishes() {
        return order.dishes();
    }

    @Override
    public List<String> drinks() {
        List<String> drinks = new ArrayList<>(order.drinks());
        drinks.add("Aperitif");
        return drinks;
    }

}
