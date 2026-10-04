package arbreb;

final class BTreePrinter {

    private BTreePrinter() {
        // Utility class
    }

    static void print(Noeud root) {
        prettyPrintRec(root, "", true);
    }

    private static void prettyPrintRec(Noeud n, String prefix, boolean isTail) {
        if (n == null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + "null");
            return;
        }

        System.out.println(
                prefix
                        + (isTail ? "└── " : "├── ")
                        + formatKeys(n));

        if (!n.estFeuille) {
            for (int i = 0; i <= n.taille; i++) {
                boolean last = (i == n.taille);

                prettyPrintRec(
                        n.enfants[i],
                        prefix + (isTail ? "    " : "│   "),
                        last);
            }
        }
    }

        /**
     * Formatte les clés d'un noeud pour l'affichage.
     *
     * @param n le noeud à formater
     * @return une chaîne représentant les clés du noeud
     */
    private static String formatKeys(Noeud n) {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < n.taille; i++) {
            sb.append(n.cles[i]);

            if (i + 1 < n.taille) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}