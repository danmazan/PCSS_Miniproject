package com.company;

public class Dice {

    public Dice(){
    }

    public int roll = (int)(Math.random() * ((6 - 1) + 1)) + 1;
}
