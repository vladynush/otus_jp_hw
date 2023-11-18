package decorator;

public abstract class OrderOptionDecorator implements Order {

    protected final Order order;

    public OrderOptionDecorator(Order order) {
        this.order = order;
    }
}
