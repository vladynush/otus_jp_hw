package decorator;

import java.util.List;
import java.util.stream.Collectors;

public class LargePortion extends OrderOptionDecorator {

    public LargePortion(Order order) {
        super(order);
    }

    @Override
    public List<String> dishes() {
        return order.dishes().stream().map(x -> "Big portion " + x).collect(Collectors.toList());
    }

    @Override
    public List<String> drinks() {
        return order.drinks();
    }
}
