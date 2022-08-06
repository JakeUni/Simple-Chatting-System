import java.net.InetAddress;
import java.util.Scanner;
import java.util.regex.*;
/**
 * This is the Class the chatClient will run to figure out which arguments the user wants
 * this will get the ip and ports specified and if multiple have been specified make the user choose
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
public class clientArguments {
    /**
     * the function that will be ran by the ChatClient
     *
     * @param input - the arguments
     * @return - the Ip and port in the form of the object Address
     */
    public static Address getinfo(String input[]){

        int k = 0;
        int j = 0;

        String num[] = new String[input.length + 1];
        String Port;
        //set the initial port as 14001 to be used if the user doesnt specify
        num[j] = "14001";
        j++;
        String IP;
        //this is the initial ip address to be used if one isnt specified
        String[] myStringArray = new String[input.length + 1];myStringArray[k] = "localhost";
        k++;


            for (int i=0;i<input.length;i++)
            {
                switch (input[i]){
                    //if they select cca it is an ip address
                    //validate using regex and check that the next item is not empty
                    //if the next item is empty there is no ip
                    case "-cca" :
                        if (!input[i+1].isEmpty()){
                            boolean b2=Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b").matcher(input[i+1]).matches();
                            if (b2){
                                myStringArray[k] = input[i+1];
                                k++;
                            }else{
                                System.out.println("Sorry the IP " + i + " is in the incorrect format");

                            }

                            i++;

                        }else{
                            //
                            System.out.println("Sorry the argument" + input[i] + "needs another argument");
                        }
                        break;
                    case "-ccp" :
                        //if they have inputted a port we can verify if it is just numbers using regex
                        //also check if the next item in the arguments exhists as if it doesnt then there is no port
                        if (!input[i+1].isEmpty()){

                            boolean b2=Pattern.compile("\\d+").matcher(input[i+1]).matches();
                            if (b2){
                                num[j] = input[i+1];
                                j++;
                            }else{
                                System.out.println("Sorry the port " + input[i] + " is in the incorrect format");
                            }

                            i++;

                        }else{
                            System.out.println("Sorry the argument " + input[i] + "needs another arguemnt");
                        }
                        break;
                    default:
                        System.out.println("Incorrect Argument " + input[i]);

                }
            }
            //the which funciton is used to check if they have inputted multiple IP or Ports
            //if they have then they need to select which one they want to use
            IP = Which(k,myStringArray, "IP addresses ");
            Port = Which(j,num,"Ports ");

            //returns the address the user has selected
            try{
                 Address address = new Address(InetAddress.getByName(IP),Integer.parseInt(Port));
                return(address);
            }catch(java.net.UnknownHostException e){
                System.out.print("invalid arguments");
            }
            return null;
    }

    /**
     * this is the function to check if the user has inputted multiples and make them select only one
     * @param k - counter for amount inputted
     * @param myStringArray - the input
     * @param text - this is text for the output message, either ip or port
     * @return - returns one of the selected items
     */
    public static String Which(int k, String[] myStringArray, String text){
        //creates a scanner to retrieve the next item inputted
        Scanner s = new Scanner(System.in);
        //if there are 2 items, this means only one really has been made as the initial count is 1 because
        //of the initial IP or Port, so use the one they have inputted
        if (k==2){
            return( myStringArray[1]);
        }
        //if there are more then output them and ask the user to select which one he would like
        //take his input validate it and then return it
        if (k>2){
            System.out.println("You have entered multiple" + text + " , please enter the number you would like: ");
            for (int m=1;m<k;m++){
                System.out.println(m + ": " + myStringArray[m]);
            }
            boolean test = false;
            int i = 0;
            while(test == false){
                i = s.nextInt();
                if ((i <= k)&&i>0){
                    test = true;
                }

            }
            return( myStringArray[i]);

        }

        return( myStringArray[0]);

    }



}
