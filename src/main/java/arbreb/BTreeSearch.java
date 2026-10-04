package arbreb;

import java.util.ArrayList;
import java.util.List;

final class BTreeSearch {

    private BTreeSearch() {
        // Utility class
    }

    /**
     * Recherche une valeur par clé.
     */
    static String exact(Noeud root, String cle) {
        return rechercheRec(root, cle);
    }

    /**
     * Recherche les valeurs dans l'intervalle [min, max].
     */
    static List<String> range(Noeud root, String min, String max) {
        List<String> result = new ArrayList<>();
        rechercheIntervalleRec(root, min, max, result);
        return result;
    }

    /**
     * Recherche les clés correspondant à un préfixe.
     */
    static List<String> prefix(Noeud root, String prefix) {
        List<String> result = new ArrayList<>();
        String normalizedPrefix = KeyNormalizer.normalize(prefix);

        recherchePrefixeRec(root, normalizedPrefix, result);
        return result;
    }

    /**
     * Recherche récursive d'une clé.
     */
    private static String rechercheRec(Noeud n, String cle) {
        if (n.estFeuille) {
            for (int i = 0; i < n.taille; i++) {
                if (n.cles[i].equals(cle)) {
                    return n.valeurs[i];
                }
            }
            return null;
        }

        int pos = n.positionEnfant(cle);
        return rechercheRec(n.enfants[pos], cle);
    }

    /**
     * Recherche récursive dans un intervalle.
     */
    private static void rechercheIntervalleRec(
            Noeud n,
            String min,
            String max,
            List<String> result) {

        if (n.minKey != null && n.maxKey != null) {
            if (n.maxKey.compareTo(min) < 0
                    || n.minKey.compareTo(max) > 0) {
                return;
            }
        }

        if (n.estFeuille) {
            for (int i = 0; i < n.taille; i++) {
                if (n.cles[i].compareTo(min) >= 0
                        && n.cles[i].compareTo(max) <= 0) {
                    result.add(n.valeurs[i]);
                }
            }
            return;
        }

        for (int i = 0; i <= n.taille; i++) {
            rechercheIntervalleRec(n.enfants[i], min, max, result);
        }
    }

    /**
     * Recherche récursive par préfixe normalisé.
     */
    private static void recherchePrefixeRec(
            Noeud n,
            String normalizedPrefix,
            List<String> result) {

        if (n == null) {
            return;
        }

        if (n.estFeuille) {
            for (int i = 0; i < n.taille; i++) {
                String normalizedKey = KeyNormalizer.normalize(n.cles[i]);

                if (normalizedKey.startsWith(normalizedPrefix)) {
                    result.add(n.cles[i] + " -> " + n.valeurs[i]);
                }
            }
            return;
        }

        for (int i = 0; i <= n.taille; i++) {
            recherchePrefixeRec(
                    n.enfants[i],
                    normalizedPrefix,
                    result);
        }
    }
}