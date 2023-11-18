package decorator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record OrdinaryOrder(List<String> dishes, List<String> drinks) implements Order {

    public OrdinaryOrder(@NotNull List<String> dishes, @NotNull List<String> drinks) {
        this.dishes = dishes;
        this.drinks = drinks;
    }
}
