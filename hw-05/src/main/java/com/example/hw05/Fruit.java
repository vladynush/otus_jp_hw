package com.example.hw05;

public abstract class Fruit {

    protected int weight;

    public Fruit(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
