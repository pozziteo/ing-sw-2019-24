package network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private int port;
    private String serverAddress;

    /**
     * constructor of client socket
     * @param port represents the socket port of the server
     * @param serverAddress represents the IP address (on the same machine it might be called localhost)
     */
    public Client(String serverAddress, int port){
        this.port = port;
        this.serverAddress = serverAddress;
    }



    /**
     * This Method starts the client
     */
    public void startClient() throws NullPointerException, IOException {


        Socket socket = new Socket("localhost", 6666);
       /* OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner stdin = new Scanner(System.in);
        try {

            while(true) {

                while(true){
                    String text = reader.readLine();
                    if (text.equals("")){break;}
                    System.out.println(text);
                }
                String inputText = stdin.nextLine();
                writer.println(inputText);

                if(inputText.equals("end")){break;}
                writer.flush();
            }


        } catch (IOException e){
            System.err.println(e.getMessage());
        } finally {
            socket.close();
            reader.close();
            writer.close();
            in.close();
            output.close();
        }
        socket.close();
        reader.close();
        writer.close();
        in.close();
        output.close(); */
    }

/*
    /**
     * Main method
     * @param args
     * @throws NullPointerException
     * @throws IOException

    public static void main(String[] args) throws NullPointerException, IOException {

        Client client = new Client("localhost", 6666);
        System.out.println("The client is now running. " +
                "Connecting to the server...");
        System.out.println("Connection established\n");

        client.startClient();
        System.out.println("Connection lost");

    }*/
}
