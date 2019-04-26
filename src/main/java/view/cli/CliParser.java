package view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to parse inputs from command line.
 */

public class CliParser {
    private CliPrinter printer;

    public CliParser() {
        this.printer = new CliPrinter ();
    }

    /**
     * Method to parse any integer from command line
     */

    public int parseInt() {
        int n = 0;
        boolean value = false;

        while (!value) {
            Scanner in = new Scanner(System.in);
            try {
                n = in.nextInt();
                value = true;
            } catch (InputMismatchException ex) {
                printer.printInvalidInput ();
                in.next();
            }
        }
        return n;
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
            printer.printInvalidInput ();
        }
    }

    /**
     * Method to parse a player's nickname
     */

    public String parseNickname() {
        String nickname = "";
        boolean value = false;

        while (!value) {
            Scanner in = new Scanner(System.in);
            try {
                nickname = in.next("([A-z]|[0-9]){0,11}");
                value = true;
            } catch (InputMismatchException ex) {
                printer.printInvalidInput();
                in.next();
            }
        }
        return nickname;
    }
}
