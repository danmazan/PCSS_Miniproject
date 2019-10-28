package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {
    DataOutputStream toServer = null;

    public static void main(String[] args) {
        /* THIS IS A LOBBY EVA*/

        Scanner scan = new Scanner(System.in);  // Create a Scanner object        //Get input from user.

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
            String name = scan.nextLine(); //set "name" value to the next value inputted
            osToServer.writeUTF(name);
            osToServer.flush(); // send the message
            //osToServer.close(); // close the output stream when we're done.


            System.out.println("Write 'ready' to initiate your game or 'quit' to leave the lobby. ");
            Thread lobby = new Thread(() -> {
                boolean connect = true;
                while (connect) {
                    try {
                        String initiate = scan.nextLine();
                        osToServer.writeUTF(initiate);
                        osToServer.flush();

                        if (initiate.equalsIgnoreCase("quit")) {
                            connectToServer.close();
                            connect = false;
                            scan.close();
                        }
                        if (initiate.equalsIgnoreCase("ready")) {
                            connect = false;
                            scan.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            lobby.start();


            //THREAD FOR FLOW OF THE GAME
            Thread read = new Thread(() -> {
                boolean connect = true;
                while (connect) {
                    try {
                        String message = isFromServer.readUTF();
                        System.out.println(message);
                        if (message.equalsIgnoreCase("Game started")) {
                            connect = false;
                        }
                    } catch (IOException e) {
                        System.out.println(e + "SocketException expected");
                        break;
                    }
                }

                boolean game = true;
                while (game) {
                    boolean turn = false;
                    int numOfPlayers = 0;
                    try {
                        numOfPlayers = isFromServer.readInt();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //NEW ROUNDS SYSTEM HERE//////////////////////////////
                    // - LOOSE A DIE
                    // - SERVE NEW DICE

                    for (int i = 0; i < numOfPlayers; i++) {

                        try {
                            turn = isFromServer.readBoolean();
                            if (turn) {
                                System.out.println(isFromServer.readUTF());

                                boolean correctCommand = false;
                                do {
                                    String command = scan.nextLine();
                                    osToServer.writeUTF(command);
                                    osToServer.flush();
                                    if (command.equalsIgnoreCase("increase")) {
                                        //server needs to be updated to do this
                                        //player.increase
                                        correctCommand = true;
                                    }
                                    if (command.equalsIgnoreCase("lift")) {
                                        //server needs to be updated to do this
                                        //player.lift
                                        correctCommand = true;
                                    }
                                } while (!correctCommand);
                            } else {
                                System.out.println(isFromServer.readUTF());
                                System.out.println(isFromServer.readUTF());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    // END OF THE GAME RESULTED HERE\
                    try {
                        //end the game?
                        System.out.println(isFromServer.readUTF());
                        for (int i = 0; i < numOfPlayers; i++) {
                            String temp = isFromServer.readUTF();
                            System.out.println(temp);
                            if (temp.equalsIgnoreCase("Type 'ready' to continue or anything else to quit")) {
                                String ready = scan.nextLine();
                                osToServer.writeUTF(ready);
                                osToServer.flush();
                            }
                            game = isFromServer.readBoolean();
                            if (!game) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                scan.close();
            });
            read.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

      /*  player.shuffle();
        System.out.println("printing 4 dice values");
        player.printDice();
        System.out.println("now player loose a die");
        player.looseDie();
        player.shuffle();
        System.out.println("printing 3 dice values");
        player.printDice();

        player.increase();
 */
