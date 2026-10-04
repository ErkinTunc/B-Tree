package app;

/**
 * Program entry point. Lance les scénarios de démonstration pour l'arbre B.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            BTreeDemo.runCommunesDemo();
            return;
        }

        switch (args[0].toLowerCase()) {

            // Testing with a simple data set
            case "simple":
                BTreeDemo.runSimpleDemo();
                break;

            // Testing with a more profound dataset
            case "communes":
                BTreeDemo.runCommunesDemo();
                break;

            // 
            default:
                System.out.println("Usage: java app.Main [simple|communes]");
                break;
        }
    }
}
