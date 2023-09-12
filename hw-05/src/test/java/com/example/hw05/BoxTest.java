package com.example.hw05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BoxTest {

    Box<Apple> appleBox;
    Box<Orange> orangeBox;
    Box<Fruit> fruitBox;


    @BeforeEach
    void setUp() {
        fruitBox = new Box<>();
        fruitBox.addFruit(new Apple(8));
        fruitBox.addFruit(new Orange(5));

        orangeBox = new Box<>();
        orangeBox.addFruit(new Orange(4));

        appleBox = new Box<>();
        appleBox.addFruit(new Apple(7));
    }

    @Test
    void addFruit() {
        fruitBox.addFruit(new Apple(8));
        fruitBox.addFruit(new Orange(4));

        assertEquals(4, fruitBox.getFruits().size());

        orangeBox.addFruit(new Orange(4));

        assertEquals(2, orangeBox.getFruits().size());

        appleBox.addFruit(new Apple(4));

        assertEquals(2, appleBox.getFruits().size());
    }

    @Test
    void moveToAnotherBox_fromOrangeToFruit() {
        orangeBox.moveToAnotherBox(fruitBox);

        assertEquals(3, fruitBox.getFruits().size());
        assertEquals(0, orangeBox.getFruits().size());
    }

    @Test
    void moveToAnotherBox_fromAppleToFruit() {
        appleBox.moveToAnotherBox(fruitBox);

        assertEquals(3, fruitBox.getFruits().size());
        assertEquals(0, appleBox.getFruits().size());
    }

    @Test
    void weight(){
        assertEquals(13, fruitBox.weight());
        assertEquals(7, appleBox.weight());
        assertEquals(4, orangeBox.weight());
    }

    @Test
    void compareWeight() {
        assertFalse(fruitBox.compare(appleBox));
        assertFalse(appleBox.compare(orangeBox));
        assertFalse(fruitBox.compare(orangeBox));

        appleBox.addFruit(new Apple(6));

        assertTrue(fruitBox.compare(appleBox));
        assertFalse(appleBox.compare(orangeBox));
        assertFalse(fruitBox.compare(orangeBox));
    }
}