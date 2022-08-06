import java.util.regex.Pattern;
/**
 * This handles the arguments inputted in the server, its purpose is to get the port the server wants to connect to
 * if no port is specified or an incorrect port is specified the default port will be used
 * @Version 1.0
 * @release15/03/2019
 *
 *
 */
public class ServerArguments {
    /**
     * this will be run by the server and will return the port wanted
     *
     * @param input - the arguments
     * @return - returns port
     */
    public static int getinfo(String input[]) {
        String num[] = new String[input.length + 1];
        String Port = "14001";
        int j = 0;
        num[j] = "14001";
        j++;
        for (int i = 0; i < input.length; i++) {
            switch (input[i]) {
                case "-csp":
                    //simple validation, is it empty? is it only numbers?
                    if (!input[i + 1].isEmpty()) {
                        boolean b2 = Pattern.compile("\\d+").matcher(input[i + 1]).matches();
                        if (b2) {
                            num[j] = input[i + 1];
                            j++;
                        } else {
                            System.out.println("Sorry the port " + input[i] + " is in the incorrect format");
                        }
                        i++;
                    } else {
                        //if they have the wrong amount of arguments
                        System.out.println("Sorry the argument " + input[i] + "needs another arguemnt");
                    }
                    break;
                default:
                    System.out.println("Incorrect Argument " + input[i]);
            }
            //using the function in clientArgument
            //This is used when there are more then one ports specified by the user
            //this will return which port the user wishes to use

            }
        Port = clientArguments.Which(j,num,"Ports ");

        //return the chosen port
        return Integer.parseInt(Port);

    }

    }


