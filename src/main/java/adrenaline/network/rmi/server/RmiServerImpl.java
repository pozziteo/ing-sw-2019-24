package adrenaline.network.rmi.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.map.Square;
import adrenaline.network.rmi.commoninterface.CommonInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.cli.CliPrinter;
import adrenaline.view.cli.CliUserInterface;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class RmiServerImpl implements CommonInterface {
    private CliPrinter printer;

    public RmiServerImpl() {
    }

    public String Hello() {
        return "Hello, the RmiServer is now running correctly (more or less)";
    }

    public String addedClient(int n){
        return "A new client is here: Client" +n;
    }

    public void send(String msg) {
        System.out.println(msg);
    }
}
