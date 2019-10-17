package com.company;
import java.util.ArrayList;
import java.util.Scanner;


public class Player extends Dice {
    Scanner keyboard = new Scanner(System.in);
    public String name;
    boolean viborg = false;
    ArrayList<Dice> myDice = new ArrayList<Dice>();
    int betAmount = 0;
    int betNumber = 0;

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

    //if increase function is not okay with previous player (server stuff)
    //then call this function again
    public void increase(){
        System.out.println("Enter amount");
        betAmount = keyboard.nextInt();
        while (betNumber > 6 || betNumber<2){
            System.out.println("Enter number between 2 and 6 including these numbers");
            betNumber  = keyboard.nextInt();
        }
    }


    public void lift(){
    /*
    count all relevant dice (count 6s eg0)rint all dice as:
    "Player1: 2, 6, 5, 4"
    "Player2: 3,3,6,5"
    ...
     */
    }

    public void looseDie(){
    myDice.remove(myDice.size()-1);
    }

}
