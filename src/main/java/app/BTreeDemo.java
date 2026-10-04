package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import arbreb.ArbreB;

/**
 * Demonstrates the public B-tree API.
 */
public final class BTreeDemo {

    private static final Path DATASET =
            Path.of("data", "communes.txt");

    private static final int DISPLAY_LIMIT = 10;

    // Half-Life inspired terminal colors
    private static final String ORANGE = "\u001B[38;2;255;102;0m";
    private static final String GREEN = "\u001B[38;2;153;204;51m";
    private static final String GRAY = "\u001B[38;2;160;160;160m";
    private static final String RESET = "\u001B[0m";

    private BTreeDemo() {
        // Utility class
    }

    /**
     * Runs a small insertion and range-search demo.
     */
    public static void runSimpleDemo() {
        ArbreB tree = new ArbreB();

        insertAndPrint(tree, "e", "eclat");
        insertAndPrint(tree, "a", "ajout");
        insertAndPrint(tree, "c", "coucou");
        insertAndPrint(tree, "b", "bouh");
        insertAndPrint(tree, "d", "doudou");
        insertAndPrint(tree, "h", "herbe");
        insertAndPrint(tree, "i", "iris");
        insertAndPrint(tree, "f", "flot");
        insertAndPrint(tree, "g", "girafe");

        header("RANGE SEARCH [c, d]");

        System.out.println(
                tree.rechercheIntervalle("c", "d"));
    }

    /**
     * Runs the demo using the communes dataset.
     */
    public static void runCommunesDemo() throws IOException {
        ArbreB tree = new ArbreB();

        int recordCount = loadDataset(tree);

        header("B-TREE INDEX");

        System.out.printf(
                "%sDataset:%s %s%n",
                GRAY,
                RESET,
                DATASET);

        System.out.printf(
                "%sRecords:%s %,d%n",
                GRAY,
                RESET,
                recordCount);

        lookupDemo(tree);
        prefixDemo(tree, "ch");
    }

    private static int loadDataset(ArbreB tree) throws IOException {
        if (!Files.exists(DATASET)) {
            throw new IOException(
                    "Dataset not found: "
                            + DATASET.toAbsolutePath());
        }

        int count = 0;

        try (Scanner scanner = new Scanner(DATASET)) {
            while (scanner.hasNextLine()) {
                String key = scanner.nextLine();

                tree.ajouter(
                        key,
                        "record-" + count);

                count++;
            }
        }

        return count;
    }

    private static void lookupDemo(ArbreB tree) {
        header("EXACT SEARCH");

        printLookup(
                "Chinon",
                tree.recherche("Chinon"));

        printLookup(
                "Mars",
                tree.recherche("Mars"));
    }

    private static void prefixDemo(
            ArbreB tree,
            String prefix) {

        List<String> results =
                tree.recherchePrefixe(prefix);

        header(
                "PREFIX SEARCH \"" + prefix + "\"");

        int limit =
                Math.min(DISPLAY_LIMIT, results.size());

        for (int i = 0; i < limit; i++) {
            System.out.printf(
                    "%s%2d.%s %s%n",
                    ORANGE,
                    i + 1,
                    RESET,
                    results.get(i));
        }

        if (results.size() > limit) {
            System.out.printf(
                    "%s... %d more%s%n",
                    GRAY,
                    results.size() - limit,
                    RESET);
        }

        if (results.isEmpty()) {
            System.out.println(
                    GRAY + "No matches." + RESET);
        }
    }

    private static void printLookup(
            String key,
            String value) {

        System.out.printf(
                "%s%-10s%s -> %s%n",
                GREEN,
                key,
                RESET,
                value);
    }

    private static void insertAndPrint(
            ArbreB tree,
            String key,
            String value) {

        tree.ajouter(key, value);

        System.out.printf(
                "%n%s[ INSERT %s ]%s%n",
                ORANGE,
                key,
                RESET);

        tree.prettyPrint();
    }

    private static void header(String title) {
        System.out.printf(
                "%n%s=== %s ===%s%n",
                ORANGE,
                title,
                RESET);
    }
}