/**
 * This Class is used to store client information, and to recieve their messages
 * threads of this are created in client manager
 *
 * @Version 1.0
 * @release15/03/2019
 * @see Runnable.java.lang
 *
 */
import java.io.*;
import java.net.*;

class ClientHandler implements Runnable
{
    public String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    int id;


    volatile boolean test = true;

    // constructor

    /**
     *Constructor - sets the field values
     * @param s this is the socket the user is connected to
     * @param name this is the users usernamde
     * @param id this is a unique ID of the user
     * @param dis the data input stream, where data comes in from the user
     * @param dos the data output stream, where data is sent to the user
     */
    public ClientHandler(Socket s, String name,int id, DataInputStream dis, DataOutputStream dos) {
        this.id = id;
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
    }

    /**
     * This is the run() method which is overriding the runnable run method,
     * This will run until it reaches the end and then the thread will close. i use the flag "test" to close the thread
     * this will  continuously scan for new input from the user and then forward it to the client manager
     */
    @Override
    public void run() {

        String received = "";
        try{
            received = dis.readUTF();
        }
        catch(IOException e){

            test = false;
        }

        while (test == true)
        {
                if (!received.isEmpty()){
                    //print the message to the Server and send it to the inputHandler
                    InputHandler.input(this.name,received);
                }

            try{
                //try to read a new input, if this fails then the client has disconnected
                received = dis.readUTF();
            }
            catch(IOException e){
               // closing resources
                    test = false;
            }
        }

    }
} 