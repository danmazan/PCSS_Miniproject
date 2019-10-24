package com.company;

import javax.sound.midi.Soundbank;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main  {
    DataOutputStream toServer = null;

    public static void main(String[] args) {
	// write your code here
        Dice dice = new Dice();
        Player player = new Player();

        boolean connect = true; //True as long as user is connected.
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        //Get input from user.
        Scanner input = new Scanner(System.in);

        while(connect) {
            try {
                // Create a socket to connect to the server
                Socket connectToServer = new Socket("localhost", 8000);
                //Socket connectToServer = new Socket("130.254.204.36", 8000);

                // Create an input stream to receive data from the server
                DataInputStream isFromServer = new DataInputStream(connectToServer.getInputStream());
                // Create an output stream to send data to the server
                DataOutputStream osToServer = new DataOutputStream(connectToServer.getOutputStream());

                //Ask user to input their name
                System.out.println("Enter name");
                String name = myObj.nextLine(); //set "name" value to the next value inputted
                osToServer.writeUTF(name);
                osToServer.flush(); // send the message
                osToServer.close(); // close the output stream when we're done.

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        player.shuffle();
        System.out.println("printing 4 dice values");
        player.printDice();
        System.out.println("now player loose a die");
        player.looseDie();
        player.shuffle();
        System.out.println("printing 3 dice values");
        player.printDice();

        player.increase();

    }
}
