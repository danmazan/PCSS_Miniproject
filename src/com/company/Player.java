package com.company;
import java.util.Scanner;


public class Player extends Dice {
    Scanner keyboard = new Scanner(System.in);
    public String name;
    boolean viborg = false;
    Dice[] myDice = new Dice[3];

    //constructor
    public Player(String name){
        this.name = name;
        for (int i = 0; i<=myDice.length; i++){
            myDice[i].roll();
        }
    }

    public void increase(int amount, int number){
        System.out.println("Enter amount");
        amount = keyboard.nextInt();
        System.out.println("Enter number");
        //try catch statement here(number cannot be higher than 6)
        number = keyboard.nextInt();


    }

    public void lift(){ //related to dice

    }

    public void looseDie(){ //related to dice

    }

}
