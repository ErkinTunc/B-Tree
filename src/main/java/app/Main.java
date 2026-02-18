package app;

/**
 * Program entry-point.
 * Test/demo scenarios live in ArbreBTests to keep Main minimal.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            ArbreBTests.testCommunes();
            return;
        }

        switch (args[0].toLowerCase()) {
            case "simple":
                ArbreBTests.testSimple();
                break;
            case "communes":
                ArbreBTests.testCommunes();
                break;
            default:
                System.out.println("Usage: java app.Main [simple|communes]");
                break;
        }
    }
}