package com.company;
import java.util.Random;
import static jdk.nashorn.internal.objects.NativeMath.random;

public class Dice {

    public Dice(){
    };

    public void roll (){
       double roll = (Math.random() * ((6 - 1) + 1)) + 1;
    }

}
