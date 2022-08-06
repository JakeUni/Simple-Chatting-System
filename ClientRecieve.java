/**
 * This is a class to recieve messages from the server and either output them to the user
 * or if they are a terminating command use them to temninate the user
 * @Version 1.0
 * @release15/03/2019
 * @see Runnable.java.lang
 *
 */
import java.io.DataInputStream;
import java.io.IOException;

public class ClientRecieve implements Runnable {
    final DataInputStream dis;

    /**
     * this is the constructor used to set fields
     * @param dis - the data input stream from the server
     */
    public ClientRecieve(DataInputStream dis) {
        this.dis = dis;
    }

    /**
     * this is overriding the run method from runnable
     * this will run until its end condition is met and then it will terminate the thread
     */
    @Override
    public void run() {
        String msg = "";
        //attempt to input
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            //if input fails terminate
            msg = "die";
        }
        while (!msg.equals("die")) {
            // read the message sent to this client
            System.out.println(msg);
            try {
                msg = dis.readUTF();
            } catch (IOException e) {

            }
        }
        ClientSend.end = true;
        ChatClient.kill();
    }
}




