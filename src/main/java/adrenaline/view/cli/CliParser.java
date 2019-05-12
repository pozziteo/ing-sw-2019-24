package adrenaline.view.cli;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to parse inputs from command line.
 */

public class CliParser {
    private CliPrinter printer = new CliPrinter ();
    private AtomicBoolean active = new AtomicBoolean ();

    /**
     * Default constructor.
     */

    public CliParser() {

    }

    /**
     * Constructor for async parser.
     * @param value initial boolean value for active input
     */

    public CliParser(boolean value) {
        this.active.set(value);
    }

    /**
     * Method to parse any integer from command line
     */

    public int parseInt() {
        int n = 0;
        boolean valid = false;

        while (!valid) {
            Scanner in = new Scanner(System.in);
            try {
                n = in.nextInt();
                valid = true;
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
        boolean valid = false;

        do {
            n = parseInt();
            if (n >= 0 && n <= maxInt) {
                valid = true;
            } else {
                printer.printInvalidInput ();
            }
        } while(!valid);

        return n;
    }

    /**
     * Method to parse enter from command line
     */

    public void parseEnter() {
        try {
            System.in.read();
        } catch(Exception e) {
            printer.print(e.getMessage ());
        }
    }

    /**
     * Method to parse a player's nickname
     */

    public String parseNickname() {
        String nickname = "";
        boolean valid = false;

        while (!valid) {
            Scanner in = new Scanner(System.in);
            try {
                nickname = in.next("([A-z]|[0-9]){0,11}");
                valid = true;
            } catch (InputMismatchException ex) {
                printer.printInvalidInput();
                in.next();
            }
        }
        return nickname;
    }

    //ASYNC

    public void setActive(boolean value) {
        this.active.set (value);
    }

    public int asyncParseInt(int maxInt) {
        boolean valid = false;
        int n;
        do {
            n = parseInt();
            if (n == -1) {
                return -1;
            } else if (!(n < 0 || n > maxInt)) {
                valid = true;
            } else {
                printer.printInvalidInput ();
            }
        } while (!valid && active.get());
        if (!active.get()) //time out
            return -1;
        return n;
    }
}
