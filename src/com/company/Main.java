package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Dice dice = new Dice();

        Player player = new Player("Dan");
        player.shuffle();
        player.printDice();
    }
}
