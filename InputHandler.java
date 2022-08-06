/**
 * This will recieve input from the clients, and decide what to do with them
 * it forwards this decision to ClientManager
 * All things go through client manager which implements Syncronized methods to ensure thread safety
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
public class InputHandler {
    /**
     *
     * @param name - the name of the Client who inputted the message
     * @param message - the actual message that has been inputted
     */
    public static void input(String name,String message){
        //send the message to the server
        System.out.println(name + " : " + message);
        //if the first character is a / it is a command
        // /setuser sets the name of the client ,
        // /pm allows them to personal message someone
        char c = message.charAt(0);
        if(c == '/'){
            String[] data = message.split(" ",3);
            switch (data[0]){
                case "/setuser" :
                    ClientManager.setClient(name , data[1]);
                    break;
                case "/pm" :
                    ClientManager.sendClient(name,data[1],data[2]);
                    break;
                default :
                    //if it is neither it is a message
                    ClientManager.sendAllClients(name, message);
                    break;
            }
        }else{
            //if the user types QUIT they want to disconnect so disconnect them
            if(message.equals("QUIT")){
                ClientManager.removeClient(name);
                ClientManager.sendAllClients("SERVER", "User '" + name + "' has disconnected");

                return;
            }else{
                //if it is a message then send to all clients
                ClientManager.sendAllClients(name, message);
            }
        }
    }

}
