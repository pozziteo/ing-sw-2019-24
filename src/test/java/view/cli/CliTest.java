package view.cli;

import org.junit.jupiter.api.Test;

public class CliTest {
    private CliUserInterface cli = new CliUserInterface ();

    @Test
    public void testTitle() {
        CliPrinter printer = this.cli.getPrinter ();
        printer.printTitle ();
        printer.printConnectionOptions ();
        printer.printActionOptions ();
    }
}
