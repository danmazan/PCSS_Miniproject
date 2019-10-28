package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Player {

    Scanner keyboard = new Scanner(System.in);
    public String name;
    int betAmount = 0;
    int betNumber = 0;

    //constructor
    public Player(){
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
}
