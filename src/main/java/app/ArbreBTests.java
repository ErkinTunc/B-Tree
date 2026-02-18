package app;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import arbreb.ArbreB;

/**
 * Contient des scénarios de démonstration et de test permettant
 * d'observer le comportement de l'arbre B en utilisant uniquement
 * son API publique.
 */
public final class ArbreBTests {

    /**
     * Constructeur privé afin d'empêcher l'instanciation de cette
     * classe utilitaire contenant uniquement des méthodes statiques.
     */
    private ArbreBTests() {
        // Utility class
    }

    /**
     * Construit un petit arbre B via une série d'insertions et affiche
     * l'évolution de la structure après chaque ajout. Un exemple de
     * recherche par intervalle est ensuite exécuté.
     *
     * @return l'arbre B construit pour le scénario de test simple
     */
    public static ArbreB testSimple() {
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

        System.out.println("\n=== Interval query: [c, d] ===");
        List<String> intervalResults = tree.rechercheIntervalle("c", "d");
        System.out.println(intervalResults);
        System.err.println("");

        return tree;
    }

    /**
     * Insère une paire (clé, valeur) dans l'arbre B puis affiche
     * la structure actuelle de l'arbre après l'insertion.
     *
     * @param tree  l'arbre B modifié
     * @param key   la clé à insérer
     * @param value la valeur associée à la clé
     */
    private static void insertAndPrint(ArbreB tree, String key, String value) {
        tree.ajouter(key, value);

        System.out.println("\nB-Tree === Insert <" + key + "> ===");
        tree.prettyPrint();

        // If you want the verbose representation too, uncomment:
        // System.out.println(tree);

        System.out.println("--------------------------------");
    }

    /**
     * Charge le fichier data/communes.txt dans un arbre B,
     * affiche des statistiques d'indexation puis exécute
     * quelques recherches de démonstration.
     *
     * @return l'arbre B construit à partir du dataset
     * @throws Exception si le fichier est introuvable ou illisible
     */
    public static ArbreB testCommunes() throws Exception {

        ArbreB tree = new ArbreB();

        Path datasetPath = Paths.get("data", "communes.txt");
        if (!Files.exists(datasetPath)) {
            throw new FileNotFoundException("Dataset not found: " + datasetPath);
        }

        int recordCount = 0;
        long buildStart = System.currentTimeMillis();

        try (Scanner scanner = new Scanner(datasetPath.toFile())) {
            while (scanner.hasNext()) {
                tree.ajouter(
                        scanner.nextLine(),
                        String.format("F1.%s.%s",
                                recordCount / 1024,
                                recordCount % 1024));
                recordCount++;
            }
        }

        long buildEnd = System.currentTimeMillis();

        /* ----- Build report ----- */
        System.out.println();
        System.out.println("=== B-Tree Index Report ===");
        System.out.printf("Dataset: %s%n", datasetPath.toAbsolutePath().normalize());
        System.out.printf("Records indexed: %d%n", recordCount);
        System.out.printf("Build time: %d ms%n", buildEnd - buildStart);

        /* ----- Lookup demo ----- */
        long lookupStart = System.currentTimeMillis();

        String chinon = tree.recherche("Chinon");
        String mars = tree.recherche("Mars");

        long lookupEnd = System.currentTimeMillis();

        System.out.printf("Lookup 'Chinon': %s%n", chinon);
        System.out.printf("Lookup 'Mars'  : %s%n", mars);
        System.out.printf("Lookup time (2 queries): %d ms%n",
                lookupEnd - lookupStart);

        /* ----- Prefix search demo ----- */
        System.out.println("---------------------------");

        String prefix = "ch";
        int displayLimit = 20;

        List<String> results = tree.recherchePrefixe(prefix);

        System.out.printf(
                "Prefix search '%s': %d matches (showing first %d)%n",
                prefix,
                results.size(),
                Math.min(displayLimit, results.size()));

        for (int i = 0; i < Math.min(displayLimit, results.size()); i++) {
            System.out.printf("  %2d) %s%n", i + 1, results.get(i));
        }

        if (results.size() > displayLimit) {
            System.out.printf("  ... (%d more)%n",
                    results.size() - displayLimit);
        }

        return tree;
    }
}