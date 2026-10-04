package arbreb;

/**
 * Prints a readable ASCII representation of the B-tree.
 */
final class BTreePrinter {

    private BTreePrinter() {
        // Utility class
    }

    static void print(Noeud root) {
        if (root == null) {
            System.out.println("[empty tree]");
            return;
        }

        System.out.println("ROOT " + formatNode(root));

        if (!root.estFeuille) {
            for (int i = 0; i <= root.taille; i++) {
                boolean last = i == root.taille;
                printRecursive(root.enfants[i], "", last);
            }
        }
    }

    private static void printRecursive(
            Noeud node,
            String prefix,
            boolean isTail) {

        String connector = isTail ? "\\-- " : "|-- ";

        System.out.println(
                prefix
                        + connector
                        + formatNode(node));

        if (node.estFeuille) {
            return;
        }

        String childPrefix =
                prefix + (isTail ? "    " : "|   ");

        for (int i = 0; i <= node.taille; i++) {
            boolean last = i == node.taille;

            printRecursive(
                    node.enfants[i],
                    childPrefix,
                    last);
        }
    }

    private static String formatNode(Noeud node) {
        String type = node.estFeuille
                ? "LEAF "
                : "NODE ";

        return type + formatKeys(node);
    }

    private static String formatKeys(Noeud node) {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < node.taille; i++) {
            result.append(node.cles[i]);

            if (i < node.taille - 1) {
                result.append(", ");
            }
        }

        return result.append("]").toString();
    }
}