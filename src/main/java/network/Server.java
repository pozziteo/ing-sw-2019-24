package network;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

    private int port;
    private ServerSocket ss;

    /**
     * This Method creates the server
     * @param port is the port required for creating the socket
     */
    public Server(int port){
        this.port = port;
    }




    /**
     * This method starts the server;
     * It also handles every new client that joins the server
     * and allows bidirectional communication between client and server
     * @throws IOException
     */
    public void startServer() throws IOException {

        int i = 1;

        try {
           ss = new ServerSocket(port);
           System.out.println("the server is ready on port: " + port);

           while (i<6) {

               Socket s = ss.accept();
               Scanner input = new Scanner(s.getInputStream());
               //InputStream in = s.getInputStream();
               PrintWriter output = new PrintWriter(s.getOutputStream());
               //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
               /*Scanner stdin = new Scanner(System.in);*/
               System.out.println("A new client is here: Client" +i);
               String text;

               /*

               /*-----------------------------------------------------------------------------------------------------
               /*This is the connection selector*/
               /*-----------------------------------------------------------------------------------------------------
                output.println("Choose your connection by pressing the corresponding number:\n" +
                        "0 - RMI\n" +
                        "1 - Socket\n");
                output.flush();
                text = input.next();
                System.out.println("From Client" + i + ": " + text);
                if (text.equals("0")){output.println("You chose RMI connection");output.flush();}
                if (text.equals("1")){output.println("You chose Socket connection");output.flush();}


               /*-----------------------------------------------------------------------------------------------------
               /*This is the map selector*/
               /*-----------------------------------------------------------------------------------------------------
                output.println("Which arena will be your battlefield?\n"
                        +"1 - Small arena\n"
                        +"2 - Medium arena (v1)\n"
                        +"3 - Medium arena (v2)\n"
                        +"4 - Large arena\n");
                output.flush();
                text = input.next();
                System.out.println("From Client" + i + ": " + text);
                if (text.equals("1")){output.println("You chose Small arena");}
                if (text.equals("2")){output.println("You chose Medium arena (v1)");}
                if (text.equals("3")){output.println("You chose Small arena");}
                if (text.equals("4")){output.println("You chose Medium arena (v1)");}


               /*-----------------------------------------------------------------------------------------------------
               /*This is the action selector*/
               /*-----------------------------------------------------------------------------------------------------
               output.println("What will you do next?\n"
                        +"1 - Move\n"
                        +"2 - Move and grab\n"
                        +"3 - Shoot an opponent\n"
                        +"4 - Pass this turn\n");
               output.flush();
               text = input.next();
               System.out.println("From Client" + i + ": " + text);
               if (text.equals("1")){output.println("You chose to move");}
               if (text.equals("2")){output.println("You chose to move and grab");}
               if (text.equals("3")){output.println("You chose to Shoot an opponent");}
               if (text.equals("4")){output.println("You chose to pass this turn");}

               output.println("The game is starting...." +
                       "get ready!");
               output.flush();

               */


               i = i+1;

           }
           /* input.close();
            in.close();
            output.close();
            s.close();*/
       } catch (IOException e){
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
        ss.close();
    }

    /**
     * Main Method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{

        Server server = new Server(6666);
        System.out.println("the server is running...");

        server.startServer();

    }
}