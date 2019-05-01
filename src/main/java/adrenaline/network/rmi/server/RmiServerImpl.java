package adrenaline.network.rmi.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.map.Square;
import adrenaline.network.rmi.commoninterface.CommonInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.cli.CliPrinter;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class RmiServerImpl implements CommonInterface, UserInterface {

    private CliPrinter printer;


    public RmiServerImpl(){}

    public String mapSelectorRmi(){
        return "ciao";
    }


    public void sendMethod(CliPrinter printer){
        System.out.println(printer);
    }

    public void send(String msg){
        System.out.println(msg);
    }

    @Override
    public void updateView(DataForClient data) {

    }

    @Override
    public void sendToController(DataForServer data) {

    }

    @Override
    public void setUpAccount() {

    }

    @Override
    public void setFirstPlayer(boolean value) {

    }

    @Override
    public void loginStatus(boolean value, String message) {

    }

    @Override
    public void printMap(Square[] arena) {

    }
}
