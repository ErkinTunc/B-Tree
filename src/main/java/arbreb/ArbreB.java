/**
 * TP1 - Arbre B (Base de données avancée)
 *
 * Implémentation en mémoire primaire d’un arbre B tel que vu en cours.
 * Ce programme fournit les fonctionnalités principales suivantes :
 *
 *  - Insertion d’une SplitResult (clé, valeur)
 *  - Recherche d’une valeur par sa clé
 *  - Recherche efficace des valeurs appartenant à un intervalle de clés
 *  - Recherche de valeurs par préfixe de clé
 *  - Affichage lisible de la structure de l’arbre
 *
 * La classe ArbreB repose sur une structure interne Noeud qui peut être soit un
 * noeud interne, soit une feuille. Le paramètre M (M >= 2) représente le nombre
 * maximum de clés qu’un noeud peut contenir.
 *
 * Ce travail s’appuie sur les consignes du TP1 (Base de données avancée) et
 * reprend les étapes d’implémentation abordées dans le sujet :
 * méthodes outils, ajout simple, splits (feuilles et nœuds internes),
 * et extensions (recherche par intervalle et préfixe).
 *
 * Auteur   : Erkin Tunc BOYA
 * Version  : 2.0
 * Date     : 27/09/2025
 */
package arbreb;

import java.util.*;

public class ArbreB {

    // M >= 2
    public static int M = 3; // le nombre de clé max dans un noueud
    private Noeud racine;

    // classe utile pour le retour de valeur
    // dans les méthodes où un split est effectué
    private static final class SplitResult {

        public final String cle;
        public final Noeud noeud;

        public SplitResult(String c, Noeud n) {
            this.cle = c;
            this.noeud = n;
        }

        public String toString() {
            return "(" + this.cle + ", " + this.noeud + ")";
        }
    }

    public ArbreB() {
        this.racine = new Noeud(true);
    }

    /**
     * Elle ajoute une association clé, valeur dans un arbre. Elle applique des
     * splits sur les feuilles et les noeuds internes quand cela est nécessaire.
     *
     * <p>
     * Elle fait appel à une nouvelle méthode auxiliaire récursive
     * ajouterRec(Noeud n, String cle, String valeur).
     * </p>
     *
     * @param cle    la clé a ajouter
     * @param valeur la valeur associée a la clé
     *
     * @throws IllegalArgumentException si clé ou valeur est null
     */
    public void ajouter(String cle, String valeur) {
        // Error handling
        if (cle == null || valeur == null) {
            throw new NullPointerException("Erreur: clé ou valeur ne doivent pas être null.");
        }

        // Main logics
        SplitResult split = ajouterRec(racine, cle, valeur); // si noeud est plein, on split
        if (split != null) { // la racine a été splittée
            Noeud newRoot = new Noeud(false);
            newRoot.cles[0] = split.cle;
            newRoot.enfants[0] = racine;
            newRoot.enfants[1] = split.noeud;
            newRoot.taille = 1;
            racine = newRoot;
        }
    }

    /**
     * Elle ajoute une association clé, valeur dans le sous-arbre dont la racine
     * est Elle applique des splits sur les feuilles et les noeuds internes
     * quand cela est nécessaire
     *
     * <p>
     * si la clé est déjà dans l’arbre, on remplace la valeur associée à la clé
     * </p>
     *
     * @param n      noeud racine du sous-arbre
     * @param cle    la clé a ajouter
     * @param valeur la valeur associée a la clé
     * @return une SplitResult (clé médiane, nouveau noeud droit) si un split a
     *         eu lieu null sinon
     */
    private SplitResult ajouterRec(Noeud n, String cle, String valeur) {

        if (n.estFeuille) {
            int pos = n.positionInsertion(cle);

            // Remplace la valeur si la clé existe déjà.
            if (pos < n.taille && n.cles[pos].equals(cle)) {
                n.valeurs[pos] = valeur;
                return null;
            }

            n.insererA(pos, cle, valeur, null);

            if (n.taille >= M) {
                return splitFeuille(n, cle, valeur);
            }

            n.updateRange();
            return null;
        }

        int childIndex = n.positionEnfant(cle);
        SplitResult splitResult = ajouterRec(n.enfants[childIndex], cle, valeur);

        if (splitResult != null) {
            n.insererA(
                    childIndex,
                    splitResult.cle,
                    null,
                    splitResult.noeud);

            if (n.taille >= M) {
                return splitInterne(
                        n,
                        splitResult.cle,
                        splitResult.noeud);
            }
        }

        n.updateRange();
        return null;
    }

    /**
     * Elle applique un split sur une feuille pleine au moment de l’ajout d’une
     * clé et de sa valeur.
     * <p>
     * La méthode retourne une SplitResult contenant la clé médiane à faire
     * remonter dans le noeud interne “parent” et la nouvelle feuille issus du
     * split à droite de la valeur médiane.
     * </p>
     * On transformera le noeud sur lequel s’applique le split en le noeud à
     * gauche de la valeur médiane.
     *
     * @param n      la feuille pleine à splitter
     * @param cle    la clé à insérer
     * @param valeur la valeur à insérer
     * @return la SplitResult (clé médiane, nouvelle feuille droite)
     */
    private SplitResult splitFeuille(Noeud n, String cle, String valeur) {
        int total = n.taille;
        int mid = total / 2; // position médiane

        Noeud droit = new Noeud(true);

        // Copier la moitié droite dans 'droit'
        for (int i = mid; i < total; i++) {
            droit.cles[i - mid] = n.cles[i];
            droit.valeurs[i - mid] = n.valeurs[i];
        }
        droit.taille = total - mid;

        // Réduire la taille du noeud gauche
        n.taille = mid;

        // Mettre à jour les minKey et maxKey
        n.updateRange();
        droit.updateRange();

        // La clé médiane est la première de la feuille droite
        return new SplitResult(droit.cles[0], droit);
    }

    /**
     * Elle applique un split sur un noeud interne plein au moment de l’ajout
     * d’une clé et d’un enfant.
     *
     * @param n      le noeud interne plein à splitter
     * @param cle    la clé à insérer
     * @param enfant l'enfant à insérer
     * @return la SplitResult (clé médiane, nouveau noeud droit)
     */
    private SplitResult splitInterne(Noeud n, String cle, Noeud enfant) {

        // position médiane dans le noeud avec un élément en plus
        int total = n.taille;
        int posMed = total / 2;

        // la clé médiane est celle qui va remonter au parent
        String cleMediane = n.cles[posMed];

        // création du noeud droit
        Noeud droit = new Noeud(false);

        // copie de la partie droite du noeud (clés et enfants après la médiane)
        for (int i = posMed + 1; i < total; i++) {
            droit.cles[i - (posMed + 1)] = n.cles[i];
            droit.enfants[i - (posMed + 1)] = n.enfants[i];
        }
        // copier le dernier enfant
        droit.enfants[total - (posMed + 1)] = n.enfants[total];
        droit.taille = total - posMed - 1;

        // réduction du noeud gauche : il ne garde que les clés avant la médiane
        n.taille = posMed;

        // Mettre à jour les minKey et maxKey
        n.updateRange();
        droit.updateRange();

        // retourner la clé médiane et le noeud droit
        return new SplitResult(cleMediane, droit);
    }

    public String recherche(String cle) {
        return BTreeSearch.exact(racine, cle);
    }

    public List<String> rechercheIntervalle(String min, String max) {
        return BTreeSearch.range(racine, min, max);
    }

    public List<String> recherchePrefixe(String prefix) {
        return BTreeSearch.prefix(racine, prefix);
    }

    public void prettyPrint() {
        BTreePrinter.print(racine);
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(this.racine);
        return b.toString();
    }

}
