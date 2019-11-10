package com.company;

import javafx.application.Application;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;



public class Client {



    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

            try {

                boolean lift = true;


                Socket s = new Socket("localhost", 8000);
                System.out.println("Connected to server");

                DataInputStream inputStream = new DataInputStream(s.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
                System.out.println("Data streams established");
                System.out.println("Waiting for players");

                boolean connect = true;
                boolean newGame =false;
                while (connect) {
                    do {

                        boolean game = true;

                        Double playerNum = inputStream.readDouble();
                        if (playerNum == 1) {
                            System.out.println("you are player 1");
                        }
                        if (playerNum == 2) {
                            System.out.println("you are player 2");
                        }
                        if (playerNum == 3) {
                            System.out.println("you are player 3");
                        }
                        //showing dice to user
                        System.out.println("Your dice:");
                        for (int i = 0; i < 4; i++) {
                            int dice = inputStream.readInt();
                            System.out.println(dice);
                        }

                        //while game loop in session.java
                        do {

                            boolean correctAction = false;
                            //players turn action items
                            do {
                                String gameStatus = inputStream.readUTF();
                                System.out.println("game status: " + gameStatus);

                                //receiving turn order messages
                                Double yourTurn = inputStream.readDouble();

                                if (yourTurn == 1) {
                                    System.out.println("It is your turn");
                                    System.out.println("You can use commands: increase | lift | quit ");
                                    boolean correctCommand = false;
                                    do {
                                        String action = keyboard.nextLine();
                                        outputStream.writeUTF(action);
                                        outputStream.flush();


                                        if (action.equalsIgnoreCase("increase")) {
                                            do {
                                                correctCommand = true;
                                                System.out.println("Enter amount");
                                                // sending amount
                                                int amount = keyboard.nextInt();
                                                outputStream.writeInt(amount);
                                                System.out.println("Enter number");
                                                //sending number
                                                int number = keyboard.nextInt();
                                                outputStream.writeInt(number);
                                                //receiving correct/false increase message
                                                String correctIncreaseMes = inputStream.readUTF();
                                                System.out.println(correctIncreaseMes);

                                                lift = inputStream.readBoolean();

                                            } while (lift);

                                            correctAction = true;
                                        }

                                        if (action.equalsIgnoreCase("lift")) {
                                            correctCommand = true;
                                            correctAction = true;
                                            game = false;
                                        }
                                        if(action.equalsIgnoreCase("quit")){
                                            correctCommand = true;
                                            correctAction=true;
                                            game = false;
                                            newGame=true;
                                            connect = false;
                                        }
                                        else {
                                            System.out.println("\nnew command");
                                        }
                                    } while (!correctCommand);
                                }
                                if (yourTurn == 0) {
                                    System.out.println("wait for your turn");
                                }
                                if (yourTurn == 2) {
                                    System.out.println("Game result: ");
                                    correctAction = true;
                                    game = false;
                                }

                            } while (!correctAction);
                        } while (game);

                        //game result from server received here
                        String winner = inputStream.readUTF();
                        System.out.println(winner);
                        System.out.println("This game has ended");
                        System.out.println("\nNEXT ROUND");
                    }while (!newGame);
                }
            } catch (IOException e) {
                System.out.println("connection error");
            }
    }
}