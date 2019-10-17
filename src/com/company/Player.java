package com.company;
import java.util.ArrayList;
import java.util.Scanner;


public class Player extends Dice {
    Scanner keyboard = new Scanner(System.in);
    public String name;
    boolean viborg = false;
    ArrayList<Dice> myDice = new ArrayList<Dice>();

    //constructor
    public Player(String name){
        this.name = name;
        myDice.add(new Dice());
        myDice.add(new Dice());
        myDice.add(new Dice());
        myDice.add(new Dice());

    }

    public void shuffle(){
        for (int i=0; i<myDice.size(); i++){
            myDice.get(i).roll();
        }
    }

    public void printDice(){
        System.out.println("Your dice: ");
        myDice.forEach( (i) -> System.out.print(i.value + ", "));
        System.out.println(" ");
    }

    public void increase(int amount, int number){
        System.out.println("Enter amount");
        amount = keyboard.nextInt();
        System.out.println("Enter number");
        //try catch statement here(number cannot be higher than 6)
        number = keyboard.nextInt();


    }

    public void lift(){
    /*
    count all relevant dice
    print all dice as:
    "Player1: 2, 6, 5, 4"
    "Player2: 3,3,6,5"
    ...
     */
    }

    public void looseDie(){
    myDice.remove(myDice.size()-1);
    }

}
