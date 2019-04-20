package controller;

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
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner stdin = new Scanner(System.in);
        try {

            while(true) {
                String inputText = stdin.nextLine();
                if(inputText.equals("end")){break;}
                writer.println(inputText);
                writer.flush();
                String text = reader.readLine();
                System.out.println(text);
            }


        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }


    /**
     * Main method
     * @param args
     * @throws NullPointerException
     * @throws IOException
     */
    public static void main(String[] args) throws NullPointerException, IOException {

        Client client = new Client("localhost", 6666);
        System.out.println("The client is now running. " +
                "Connecting to the server...");
        System.out.println("Connection established");
        client.startClient();
        System.out.println("Connection lost");

    }
}
