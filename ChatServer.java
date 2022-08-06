import java.io.*;
import java.net.*;
/**
 * This is the initial class ran when running the server
 * this will connect to clients
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
public class ChatServer {



    // counter for clients
    static int ServerPort = 14001;
    static int i = 0;
    static boolean server = true;

    /**
     * this is the main method of the server which will loop until it is clossed
     * @param args - the arguments the user inputs
     * @throws IOException - IO error
     */
    public static void main(String[] args) throws IOException
    {
        //if the user has atleast 2 arguments figure out what they want
        if(args.length>=2){

            ServerPort  = ServerArguments.getinfo(args);
        }

        //create the sockets needed
        ServerSocket ss = null;
        try{
             ss = new ServerSocket(ServerPort);
        }catch(java.net.BindException e){
            System.out.println("Failed to set up server, try using a different port please");
            kill();

        }

        Socket s;
        //create the listening thread for inputs
        ServerGet listen = new ServerGet();
        Thread ServerInput = new Thread(listen);
        ServerInput.start();
        // running infinite loop for getting
        // client request

        while (server)
        {
            // Accept the incoming request
            s = ss.accept();
            //print the address for the server user
            try{
                System.out.println(InetAddress.getLocalHost() );

            }catch(UnknownHostException e)  {
                System.out.println("could not get host address");
            }

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler match = new ClientHandler(s,"client " + i, i, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(match);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            ClientManager.addClient(match);
            ClientManager.sendAllClients("SERVER","Please Welcome client " + i + " to the server!" );
            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;
        }

    }
    //used as a kill method
    //will not happen if threads are active
    public static void kill(){
        System.exit(0);
    }
    }


