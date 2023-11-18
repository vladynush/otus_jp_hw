package bridge.shape;


import bridge.color.Color;

public class Triangle extends Shape {

    public Triangle(Color color) {
        super(color);
    }

    @Override
    public String draw() {
        return "Triangle is drawn. " + color.fill();
    }
}
