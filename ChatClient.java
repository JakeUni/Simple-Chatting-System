import java.io.*;
import java.net.*;
/**
 * This is the Class the user runs to start the client side of things
 * this will create two new threads to handle input and output and will send / recieve messages from the server
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
public class ChatClient {
    static int ServerPort ;
    static Socket s;
    static InetAddress ip;

    /**
     * This is the main function that runs at the start of the execution
     * @param args - this is the string the user puts following the execution of the client (passed to ClientArguments)
     * @throws UnknownHostException - if the host trying to connect to cannot be connected to
     * @throws IOException - input / output error
     */
    public static void main(String args[]) throws UnknownHostException, IOException
    {
        //gets the address from the argument using the clientArguments function
        Address address = clientArguments.getinfo(args);

        ip = address.ip;
        ServerPort = address.ServerPort;
        System.out.println("Connected to IP and Port : " + ip + " " + ServerPort);
        //this will attempt to connect to the server, if anything goes wrong it will inform the user and kill the program
        try{
            s = new Socket(ip, ServerPort);
        }catch(java.lang.IllegalArgumentException e){
            System.out.println("Failed to connect");
            kill();
        }catch( java.net.ConnectException j){
            System.out.println("Failed to connect");
            kill();
        }
        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());


        // sendMessage thread
        ClientSend send = new ClientSend(dos);
        Thread sendMessage;
        sendMessage = new Thread(send);

        // readMessage thread
        ClientRecieve recieve = new ClientRecieve(dis);
        Thread readMessage = new Thread(recieve);

        //starting threads
        readMessage.start();
        sendMessage.start();



    }

    /**
     * simple function to end client
     * this happens only if no threads are active (it is never called in an instance where a thread will be running
     */
    public static void kill(){

            System.exit(0);

    }

}
