package com.company;

import javax.sound.midi.Soundbank;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Dice dice = new Dice();

        Player player = new Player("Dan");
        player.shuffle();
        System.out.println("printing 4 dice values");
        player.printDice();
        System.out.println("now player loose a die");
        player.looseDie();
        System.out.println("printing 3 dice values");
        player.printDice();

    }
}
//i write copy paste