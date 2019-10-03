package com.company;

public class Dice {

    public Dice(){
    };

    public void roll (){
       double value = (Math.random() * ((6 - 1) + 1)) + 1;
    }

}
