package app;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import arbreb.ArbreB;

/**
 * Manual demo scenarios (tests) for understanding ArbreB.
 * Must use ONLY ArbreB public API.
 */
public final class ArbreBTests {

    private ArbreBTests() {
        // Utility class
    }

    public static ArbreB testSimple() {
        ArbreB a = new ArbreB();

        System.out.println("cle <e> est ajoutee");
        a.ajouter("e", "eclat");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <a> est ajoutee");
        a.ajouter("a", "ajout");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <c> est ajoutee");
        a.ajouter("c", "coucou");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <b> est ajoutee");
        a.ajouter("b", "bouh");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <d> est ajoutee");
        a.ajouter("d", "doudou");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <h> est ajoutee");
        a.ajouter("h", "herbe");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <i> est ajoutee");
        a.ajouter("i", "iris");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <f> est ajoutee");
        a.ajouter("f", "flot");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");

        System.out.println("cle <g> est ajoutee");
        a.ajouter("g", "girafe");
        System.out.println(a);
        a.prettyPrint();

        System.out.println("--------------------");
        System.out.println("Recherche intervalle [c, d] :");
        List<String> intervalResults = a.rechercheIntervalle("c", "d");
        System.out.println(intervalResults);

        return a;
    }

    /**
     * Builds a B-tree from data/communes.txt
     */
    public static ArbreB testCommunes() throws Exception {
        ArbreB a = new ArbreB();

        Path path = Paths.get("data", "communes.txt");
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Dataset not found: " + path);
        }

        Scanner sc = new Scanner(path.toFile());

        int compteur = 0;
        long t0 = System.currentTimeMillis();

        while (sc.hasNext()) {
            a.ajouter(sc.nextLine(), String.format("F1.%s.%s", compteur / 1024, compteur % 1024));
            compteur++;
        }
        sc.close();

        long t1 = System.currentTimeMillis();

        System.out.println();
        System.out.println("=== B-Tree Index Report ===");
        System.out.printf("Dataset: %s%n", path.toAbsolutePath().normalize());
        System.out.printf("Records indexed: %d%n", compteur);
        System.out.printf("Build time: %d ms%n", (t1 - t0));

        long q0 = System.currentTimeMillis();
        String chinon = a.recherche("Chinon");
        String mars = a.recherche("Mars");
        long q1 = System.currentTimeMillis();

        System.out.printf("Lookup 'Chinon': %s%n", chinon);
        System.out.printf("Lookup 'Mars'  : %s%n", mars);
        System.out.printf("Lookup time (2 queries): %d ms%n", (q1 - q0));

        System.out.println("---------------------------");

        System.out.println("--------------------");
        String prefix = "ch";
        int limit = 20;

        List<String> results = a.recherchePrefixe(prefix);

        System.out.printf("Prefix search '%s': %d matches (showing first %d)%n",
                prefix, results.size(), Math.min(limit, results.size()));

        for (int i = 0; i < Math.min(limit, results.size()); i++) {
            System.out.printf("  %2d) %s%n", i + 1, results.get(i));
        }

        if (results.size() > limit) {
            System.out.printf("  ... (%d more)%n", results.size() - limit);
        }

        return a;
    }
}