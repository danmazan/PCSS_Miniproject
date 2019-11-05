package com.company;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



public class Client extends JFrame {

    //Create a simple UI
    DataOutputStream toServer = null;
    private JTextArea jta = new JTextArea();
    public static void main(String[] args) {new Client();
    }


    public Client() {
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("Snyd");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

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
            //jta.append("Enter name" + '\n');
            String name = JOptionPane.showInputDialog("Enter name"); //set "name" value to the next value inputted
            osToServer.writeUTF(name);
            osToServer.flush(); // send the message
            //osToServer.close(); // close the output stream when we're done.

            //Create a confirmation box. 0 = Yes, 2 = cancel
            int initiate = JOptionPane.showConfirmDialog(null, "Ok to start game, cancel to quit program.", "Snyd",
                    JOptionPane.OK_CANCEL_OPTION);
            Thread lobby = new Thread(() -> {
                boolean connect = true;
                while (connect) {
                    if (initiate == 0){
                        try {
                            osToServer.writeUTF("ready");
                            osToServer.flush();
                            scan.close();
                            String game = isFromServer.readUTF();
                            System.out.println("connect");
                            jta.append(game + '\n');
                            connect = false;


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        }
                    if (initiate == 2){
                        try {
                            osToServer.writeUTF("quit");
                            osToServer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    }
                }
            });
           // lobby.start();


            //THREAD FOR FLOW OF THE GAME
            Thread read = new Thread(() -> {
                boolean connect = true;
                while (connect) {

                    try {
                        String message = isFromServer.readUTF();
                        jta.append(message + '\n');
                        if (message.equalsIgnoreCase("Game started")) {
                            System.out.println("Second thread");
                            System.out.println("Exit if statement");
                            connect = false;
                        }

                    } catch (IOException e) {
                        System.out.println(e + "SocketException expected");
                        break;
                    }
                }


                boolean game = true;
                while (game) {
                    System.out.println("game loop");
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
                                jta.append(isFromServer.readUTF());

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
                                jta.append(isFromServer.readUTF());
                                jta.append(isFromServer.readUTF());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    // END OF THE GAME RESULTED HERE\
                    try {
                        //end the game?
                        jta.append(isFromServer.readUTF());
                        for (int i = 0; i < numOfPlayers; i++) {
                            String temp = isFromServer.readUTF();
                            jta.append(temp);
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
