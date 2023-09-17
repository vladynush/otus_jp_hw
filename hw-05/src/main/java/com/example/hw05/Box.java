package com.example.hw05;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {

    private List<T> fruits = new ArrayList<>();

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public int weight() {
        return fruits
                .stream()
                .mapToInt(Fruit::getWeight)
                .sum();
    }

    public boolean compare(Box<? extends Fruit> anotherBox) {
        return this.weight() == anotherBox.weight();
    }

    public void moveToAnotherBox(Box<? super T> other) {
        for (T fruit : this.getFruits()) {
            other.addFruit(fruit);
        }
        this.getFruits().clear();
    }

    public List<T> getFruits() {
        return this.fruits;
    }

    public void setFruits(List<T> fruits) {
        this.fruits = fruits;
    }
}
