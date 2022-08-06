/**
 * This Class is used to get typed commands / messages from the SERVER and decides what to do with them
 * it will redirect to the appropriate class depending on which command the Server typed
 * @Version 1.0
 * @release15/03/2019
 * @see Runnable.java.lang
 *
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Scans for user Input and will implement it
 */
public class ServerGet implements Runnable {
    Scanner scn = new Scanner(System.in);
    static boolean run = true;

    @Override
    public void run() {
        while (run == true) {
            // read the message to deliver.
            String msg = scn.nextLine();
            //check if it is an exit command
            //this closes all client connections and closes the server
            if (msg.equals("EXIT")){
                ClientManager.disconnectAll();
                ChatServer.server = false;
                run = false;
                ChatServer.kill();
            }if (msg.equals("/GetIP")){
                //this will output the local ip
                try{
                    System.out.println(InetAddress.getLocalHost() );

                }catch(UnknownHostException e)  {
                    System.out.println(e);
                }
            }
            else {
                //if they didnt type a command it is a message from the server
                ClientManager.sendAllClients("SERVER",msg);

            }

        }
    }
}
