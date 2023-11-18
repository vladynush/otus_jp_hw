package bridge.shape;


import bridge.color.Color;

public abstract class Shape {

    protected final Color color;

    public Shape(Color color) {
        this.color = color;
    }

    abstract public String draw();
}
