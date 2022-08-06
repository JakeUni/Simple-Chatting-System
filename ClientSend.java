

/**
 * This is the function which sends from the Client to the Server
 * it tskes the next user input and sends it to them
 * @Version 1.0
 * @release15/03/2019
 * @see Runnable.java.lang
 *
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.lang.System;

public class ClientSend implements Runnable {

    static Scanner scn = new Scanner(System.in);
    static volatile boolean run = true;
    final DataOutputStream dos;
    static String msg = "";
    static boolean end = false;

    /**
     * this is the constructor to initiate the fields
     * @param dos this is the data output stream to output the data from clients terminal
     */
    public ClientSend(DataOutputStream dos){

        this.dos = dos;
    }

    @Override
    /**
     * this run() is overriding the run in runnable, this will run until test = true
     */
    public void run() {
        while (!end) {
            // read the message to deliver.
            if(end||scn.hasNext()){
                if(end){
                    return;
                }else{
                    msg = scn.nextLine();
                }
            }
            //if they type quit
            if(msg.equals("QUIT")){
                end = true;
            }
            try {
                    // write on the output stream
                    dos.writeUTF(msg);
                } catch (IOException e) {
                    end = true;
                    System.out.println("Couldnt send message");
            }

        }

    }

}
