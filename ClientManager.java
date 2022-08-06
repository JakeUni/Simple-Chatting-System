import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * This is the class which manages all the client inputs
 * its purpose is to validate the input along with doing what it needs to do
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
public  class ClientManager {
    //these two maps hold the client information
    //who is a map from username to ID
    //clients is a map from ID to instances of ClientHandler
    //This means we can easily get the client from just their name (unique)
    private static  Map<String, Integer> who = new HashMap<String, Integer>();
    private static Map<Integer, ClientHandler> clients = new HashMap<Integer, ClientHandler>();
    static int i = 0;
    //this is a lock to ensure thread safety and is used whenever the maps are used
    private static final Object lock = new Object();

    /**
     * this is how a new client is added, the client is passed here from the ChatServer
     * @param Client - the new client
     */
    public static void addClient(ClientHandler Client){
        synchronized (lock) {
            clients.put(i, Client);
            who.put(Client.name, i);
        }
        //increments the amount of clients (id)
        i++;

    }

    /**
     * This function allows the user to change their name on the system to allow people to recognize who they are
     * @param oldname - the users previous name (usually preset as client : i
     * @param newName - the new name the user has requested
     */
    public static void setClient(String oldname, String newName){
        synchronized (lock) {
            int id = who.get(oldname);
            ClientHandler change = clients.get(id);

            //if a record already exists with the new name they requested then inform them and do not change the name
            if(who.containsKey(newName)){
                try {
                    change.dos.writeUTF("Name already taken");

                } catch (IOException e) {

                }

            }else{
                //if it does not exist then remove the instances of the oldname and add the new name
                who.remove(oldname);
                who.put(newName, id);

                change.name = newName;
                sendAllClients("SERVER", "Client : " + newName + " has changed their name");

            }

        }
    }


    /**
     * this is the method to remove clients from the server, what is run when they type QUIT
     * @param name - the name of the client that wishes to quit
     */
    public static void removeClient(String name){
        synchronized (lock) {

            ClientHandler client = clients.get(who.get(name));
            try {
                //send the word "die" to the client, this is a signal for the client to terminate itself
                //this can never happen unless it is send here as every other message to the client will include ":"
                client.dos.writeUTF("die");
                //stops the thread
                client.test = false;
            } catch (IOException e) {
                System.out.println("unable to disconnect client, they mightve disconnected themselves");
            }
            //remove the client from the maps
            clients.remove(who.get(name));
            who.remove(name);
        }
    }

    /**
     * This is used to Disconnect all the clients connected to the server
     */
    public static void disconnectAll(){
        synchronized (lock) {
            //this will loop through the clients, inform them that they have been disconnected and send them the
            //terminating message, this will then clear the maps
            for (Map.Entry<Integer, ClientHandler> entry : clients.entrySet()) {

                ClientHandler client = entry.getValue();
                try {
                    // client.dos.writeUTF("SERVER : You have been disconnected");
                    client.dos.writeUTF("You have been kicked");
                    client.dos.writeUTF("die");

                    client.test = false;
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

            clients.clear();
            who.clear();
            System.out.print("cleared");
        }
    }

    /**
     * this is the function to pm (personal message) a client from another client
     * @param nameFrom - who sent the message
     * @param nameTo - who the message is to
     * @param message - the actual message
     */
    public static void sendClient(String nameFrom, String nameTo, String message){
        synchronized (lock) {
            //if the names are the same the user is trying to message themselves
            //inform them that this is wrong
            if(nameFrom.equals(nameTo)){
                try {
                    ClientHandler sender = clients.get(who.get(nameFrom));
                    sender.dos.writeUTF("Cannot Pm yourself please try again");

                } catch (IOException e) {
                    System.out.println("Message failed to send");

                }

                //if one of the names does not exist in our map then the client is trying
                //to message a user that isnt real
                //inform them
            }else if(!who.containsKey(nameFrom) ||!who.containsKey(nameTo) ){
                try {
                    ClientHandler sender = clients.get(who.get(nameFrom));
                    sender.dos.writeUTF("That User Doesnt Exist Sorry");

                } catch (IOException e) {
                    System.out.println("Message failed to send");

                }

            }else{
                //get both clients and send the message to the reciever and send
                //a confirmation message to the sender
                ClientHandler sender = clients.get(who.get(nameFrom));
                ClientHandler reciever = clients.get(who.get(nameTo));

                try {
                    reciever.dos.writeUTF("PM from " + nameFrom + " : " + message);
                    sender.dos.writeUTF("sent");

                } catch (IOException e) {
                    System.out.println("Message failed to send");

                }
            }

        }
    }

    /**
     * This will send the message to every client in the map
     * @param name -sender name
     * @param message - message
     */
    public static void sendAllClients(String name, String message){

            //loop through every client in the map and send them the message
            //this is a very simple operation
        for (Map.Entry<Integer, ClientHandler> entry : clients.entrySet()) {
            synchronized (lock) {
                ClientHandler client = entry.getValue();
                try {
                    client.dos.writeUTF(name + " : " + message);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }



    }

}








