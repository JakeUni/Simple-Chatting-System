/**
 * This is a class to hold the elements that compose a socket
 * simply IP address and server port
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
import java.net.InetAddress;



public class Address {
    static int ServerPort;
    static InetAddress ip;

    /**
     * A constructor to add values to the field
     * @param ip - the ip address
     * @param ServerPort - the server port
     */
    public Address(InetAddress ip, int ServerPort){
        this.ip = ip;
        this.ServerPort = ServerPort;

    }

}
