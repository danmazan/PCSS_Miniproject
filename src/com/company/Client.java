package com.company;

import javafx.application.Application;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
                boolean connect = true;
                while (connect) {

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
                    //while game loop in session.java
                    do {
                        System.out.println("we enter while game loop");
                        //receiving turn order messages


                        boolean correctAction = false;
                        //players turn action items
                        do {
                            //if you are this player
                            boolean yourTurn = inputStream.readBoolean();
                            if (yourTurn) {
                                System.out.println("It is your turn");
                                System.out.println("You can use commands: print dice, increase, lift");
                                String action = keyboard.nextLine();
                                outputStream.writeUTF(action);
                                outputStream.flush();

                                if (action.equalsIgnoreCase("print dice")) {
                                    System.out.println("print dice command");
                                    correctAction = true;
                                }

                                if (action.equalsIgnoreCase("increase")) {
                                    System.out.println("increase command");
                                   do {
                                        // receiving enter amount message
                                        String amountMes = inputStream.readUTF();
                                        System.out.println(amountMes);
                                        // sending amount
                                        int amount = keyboard.nextInt();
                                        outputStream.writeInt(amount);
                                        //receiving enter number message
                                        String numberMes = inputStream.readUTF();
                                        System.out.println(numberMes);
                                        //sending number
                                        int number = keyboard.nextInt();
                                        outputStream.writeInt(number);
                                        //receiving correct/false increase message
                                        String correctIncreaseMes = inputStream.readUTF();
                                        System.out.println(correctIncreaseMes);

                                        Boolean liftMes = inputStream.readBoolean();
                                        System.out.println(liftMes);
                                        lift = liftMes;
                                    }while (lift);

                                    correctAction = true;
                                }

                                if (action.equalsIgnoreCase("lift")) {
                                    System.out.println("lift command");

                                    correctAction = true;
                                    game = false;
                                    //send message for game resulting to server
                                }
                            }

                            } while (!correctAction) ;

                    } while (game);
                    System.out.println("we exit while game loop");

                    //game result from server received here
                }
            } catch (IOException e) {
                System.out.println("connection error");
            }
    }
}








/*public class Client extends JFrame {

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
                            System.out.println("connect loop");
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
           lobby.start();


            //THREAD FOR FLOW OF THE GAME
            Thread read = new Thread(() -> {
                System.out.println("read thread");
                boolean connect = true;
                while (connect) {
                    try {
                        String message = isFromServer.readUTF();
                        jta.append(message + '\n');
                        if (message.equalsIgnoreCase("Game started")) {
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
                        System.out.println("Game started");
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
        player.shuffle();
        System.out.println("printing 4 dice values");
        player.printDice();
        System.out.println("now player loose a die");
        player.looseDie();
        player.shuffle();
        System.out.println("printing 3 dice values");
        player.printDice();

        player.increase();
 */
