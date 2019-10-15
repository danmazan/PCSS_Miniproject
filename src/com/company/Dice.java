package com.company;

public class Dice {

    public int value;

    public Dice(){
    value = 0;
    }
    public void roll(){
        value = (int) (Math.random() * ((6 - 1) + 1)) + 1;
    }
}
