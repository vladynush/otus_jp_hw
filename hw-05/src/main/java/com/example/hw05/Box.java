package com.example.hw05;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public boolean compare(Box<T> anotherBox) {
        return this.weight() == anotherBox.weight();
    }

    public void moveToAnotherBox(Box<? super T> other) {
        for (T fruit : this.getFruits()) {
            other.addFruit(fruit);
        }
        this.getFruits().clear();
    }
}
