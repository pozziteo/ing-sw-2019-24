package view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CliParser {
    private CliPrinter printer;

    public CliParser() {
        this.printer = new CliPrinter ();
    }

    /**
     * Method to parse any integer from command line
     */

    public int parseInt() {
        int parsed = 0;
        boolean flag = false;
        while (!flag) {
            Scanner in = new Scanner(System.in);
            try {
                parsed = in.nextInt();
                flag = true;
            } catch (InputMismatchException ex) {
                //printer.showInputNotValid();
                in.next();
            }
        }
        return parsed;
    }

    /**
     * Method to parse one integer from command line not exceeding maxInt
     * @param maxInt maximum value that can be parsed
     */
    public int parseInt(int maxInt) {
        int n;
        boolean value = false;

        do {
            n = parseInt();
            if (n >= 0 && n <= maxInt) {
                value = true;
            } else {
                printer.printInvalidInput ();
            }
        } while(!value);

        return n;
    }

    /**
     * Method to parse enter from command line
     */

    public void parseEnter() {
        try {
            System.in.read();
        } catch(Exception e) {
            System.out.println ("error"); //change
        }
    }
}
