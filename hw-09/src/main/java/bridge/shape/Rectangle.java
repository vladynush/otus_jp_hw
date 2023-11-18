package bridge.shape;


import bridge.color.Color;

public class Rectangle extends Shape {

    public Rectangle(Color color) {
        super(color);
    }

    @Override
    public String draw() {
        return "Rectangle is drawn. " + color.fill();
    }
}
