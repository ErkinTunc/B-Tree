import java.io.*;
import java.util.*;

public class ArbreB {
    // M >= 2
    public static int M = 3; // le nombre de clé max dans un noueud
    public Noeud racine;

    private static final class Noeud {
        public final boolean estFeuille;
        public final String[] cles;
        public final String[] valeurs;
        public final Noeud[] enfants;
        public int taille = 0;

        public Noeud(boolean feuille) {
            this.estFeuille = feuille;
            this.cles = new String[M];
            if (estFeuille) {
                this.valeurs = new String[M];
                this.enfants = null;
            } else {
                this.valeurs = null;
                this.enfants = new Noeud[M + 1];
            }
        }

        public String toString() {
            StringBuffer b = new StringBuffer();
            if (this.estFeuille)
                b.append("Feuille(");
            else
                b.append("Noeud(");

            if (!this.estFeuille)
                b.append(this.enfants[0]).append(" | ");

            for (int i = 0; i < this.taille; i++) {
                b.append(this.cles[i]);
                b.append((this.estFeuille) ? ": " : " | ");
                if (this.estFeuille)
                    b.append(this.valeurs[i]);
                else
                    b.append(this.enfants[i + 1]);
                if (i + 1 < this.taille)
                    b.append((this.estFeuille) ? ", " : " | ");
            }

            b.append(")");

            return b.toString();
        }
    }

    // classe utile pour le retour de valeur
    // dans les méthodes où un split est effectué
    public static final class Paire {
        public final String cle;
        public final Noeud noeud;

        public Paire(String c, Noeud n) {
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
     * Elle retourne l’indice auquel un clé devrait être insérer dans un noeud
     * interne ou une feuille.
     * <p>
     * On renverra M dans le cas, on devrait insérer dans un noeud plein après la
     * dernière clé
     * </p>
     * 
     * @param n   la noeud dans lequel on veurt insérer la clé
     * @param cle le valeur de la clé à insérer
     * @return l'indice où insérer la clé dans la noeud
     * 
     * @throws NullPointerException     si noeud ou clé est null
     * @throws IllegalArgumentException si noeud ou clé est null, ou si taille est
     *                                  incohérente
     */
    private int positionPour(Noeud n, String cle) {

        // Error handling
        if (n == null) {
            throw new NullPointerException("Erreur: le noeud est null.");
        }
        if (cle == null) {
            throw new NullPointerException("Erreur: la clé est null.");
        }
        if (n.taille < 0 || n.taille > M) {
            throw new IllegalArgumentException(
                    "Erreur: taille du noeud invalide (" + n.taille + "), M=" + M);
        }

        // Main logic
        if (n.taille == M) {
            return M; // le noeud est plein, on insère après la dernière clé
        }

        for (int i = 0; i < n.taille; i++) {

            int cmp = cle.compareTo(n.cles[i]); // cle - n.cles[i]
            if (cmp <= 0)
                return i; // on insère avant la clé i
        }
        return n.taille;
    }

    /**
     * Elle décale dans un noeud non plein les clés d’une case vers la droite pour
     * laisser la position passée en argument vide
     * <p>
     * On décalera les valeurs
     * dans une feuille de la même manière. Pour les noeuds internes, seules les
     * enfants à droite des clés décalés sont déplacés.
     * </p>
     * 
     * @param n   le noeud dans lequel on décale les clés
     * @param pos la position à libérer
     * 
     * @throws NullPointerException     si noeud est null
     * @throws IllegalArgumentException si la position est invalide ou si le noeud
     *                                  est plein
     */
    private void decalerDeUn(Noeud n, int pos) {

        // Error handling
        if (n == null) {
            throw new NullPointerException("Erreur: le noeud est null.");
        }
        if (pos < 0 || pos > n.taille) {
            throw new IllegalArgumentException(
                    "Erreur: position " + pos + " invalide pour un noeud de taille " + n.taille);
        }
        if (n.taille >= M) {
            throw new IllegalArgumentException(
                    "Erreur: impossible de décaler, le noeud est déjà plein (taille=" + n.taille + ", M=" + M + ")");
        }

        // Main logic
        if (n.taille == M) {
            System.err.println("Noeud est plein");
        }

        for (int i = n.taille - 1; i >= pos; i--) {
            n.cles[i + 1] = n.cles[i];
            if (n.estFeuille) {
                n.valeurs[i + 1] = n.valeurs[i];
            }
        }

        if (!n.estFeuille) {
            for (int i = n.taille; i >= pos + 1; i--) {
                n.enfants[i + 1] = n.enfants[i];
            }
            // Not: enfants[pos] yerinde kalır; yeni çocuk pos+1'e konur.
        }
    }

    /**
     * Elle insère, dans une feuille, une clé et une valeur à une position donnée et
     * dans
     * <p>
     * un noeud interne , insère une clé à une position donnée et un enfant à
     * sa droite.
     * </p>
     * 
     * @param n      le noeud dans lequel on insère
     * @param pos    la position dans le noeud où insérer
     * @param cle    la clé à insérer
     * @param valeur la valeur à insérer (null si n n'est pas une feuille)
     * @param enfant l'enfant à insérer (null si n est une feuille)
     * 
     * @throws NullPointerException     si noeud ou clé est null
     * @throws IllegalArgumentException si la position est invalide ou si le noeud
     *                                  est plein, ou si valeur/enfant est null
     *                                  dans un contexte inapproprié
     */
    private void insererA(Noeud n, int pos, String cle, String valeur, Noeud enfant) {

        // Error handling
        if (n == null) {
            throw new NullPointerException("Erreur: noeud est null.");
        }
        if (cle == null) {
            throw new NullPointerException("Erreur: clé est null.");
        }
        if (pos < 0 || pos > n.taille) {
            throw new IllegalArgumentException(
                    "Erreur: position " + pos + " invalide pour un noeud de taille " + n.taille);
        }
        if (n.taille >= M) {
            throw new IllegalArgumentException(
                    "Erreur: le noeud est déjà plein (taille=" + n.taille + ", M=" + M + ")");
        }

        // Vérification cohérence feuille/interne
        if (n.estFeuille && valeur == null) {
            throw new IllegalArgumentException("Erreur: valeur ne peut pas être null dans une feuille.");
        }
        if (!n.estFeuille && enfant == null) {
            throw new IllegalArgumentException("Erreur: enfant ne peut pas être null dans un noeud interne.");
        }

        // Main logic
        decalerDeUn(n, pos);
        n.cles[pos] = cle;

        if (n.estFeuille) {
            n.valeurs[pos] = valeur;
        } else {
            n.enfants[pos + 1] = enfant;
        }
        n.taille++;
    }

    /**
     * Elle ajoute une association clé, valeur dans un arbre. Elle applique des
     * splits sur les feuilles et les noeuds internes quand cela est nécessaire.
     * 
     * <p>
     * Elle fait appel à une nouvelle
     * méthode auxiliaire récursive ajouterRec(Noeud n, String cle, String valeur).
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
        Paire split = ajouterRec(racine, cle, valeur); // si noeud est plein, on split
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
     * Elle ajoute une association clé, valeur dans le sous-arbre dont la racine est
     * Elle applique des splits sur les feuilles et les noeuds internes quand cela
     * est nécessaire
     * 
     * <p>
     * si la clé est déjà dans l’arbre, on remplace la valeur associée à la clé
     * </p>
     * 
     * @param n      noeud racine du sous-arbre
     * @param cle    la clé a ajouter
     * @param valeur la valeur associée a la clé
     * @return une paire (clé médiane, nouveau noeud droit) si un split a eu lieu
     *         null sinon
     */
    private Paire ajouterRec(Noeud n, String cle, String valeur) {
        if (n.estFeuille) {
            int pos = positionPour(n, cle);

            // Controle de DUPLICATE
            if (pos < n.taille && n.cles[pos].equals(cle)) {
                // si il est déjà dans l'arbre, on remplace la valeur
                n.valeurs[pos] = valeur;
                return null; // pas de split, on arrête là
            }

            insererA(n, pos, cle, valeur, null);

            if (n.taille >= M) {
                return splitFeuille(n, cle, valeur);
            }
            return null;

        } else {
            int i = positionPour(n, cle);
            Paire paire = ajouterRec(n.enfants[i], cle, valeur);

            if (paire != null) {
                insererA(n, i, paire.cle, null, paire.noeud);

                if (n.taille >= M) {
                    return splitInterne(n, paire.cle, paire.noeud);
                }
            }
            return null;
        }

    }

    /**
     * Elle retourne la valeur correspondant à une clé dans
     * l’arbre B
     * 
     * @param cle la clé à rechercher
     * @return la valeur associée à la clé ou null si la clé n'existe pas
     */
    public String recherche(String cle) {
        String val = rechercheRec(racine, cle);
        return val;
    }

    /**
     * Elle retourne la valeur correspondant à une clé dans le sous-arbre dont la
     * racine est n.
     * 
     * @param n   le noeud racine du sous-arbre
     * @param cle la clé à rechercher
     * @return la valeur associée à la clé ou null si la clé n'existe pas
     */
    private String rechercheRec(Noeud n, String cle) {
        if (n.estFeuille) {
            for (int i = 0; i < n.taille; i++) {
                if (n.cles[i].equals(cle)) { // REMINDER: "==" for pointers , ".equals()" for objects
                    return n.valeurs[i];
                }
            }
            return null;
        } else {
            int pos = positionPour(n, cle);
            return rechercheRec(n.enfants[pos], cle);

        }

    }

    /**
     * Elle applique un split sur une feuille pleine au moment de l’ajout d’une clé
     * et de sa valeur.
     * <p>
     * La méthode retourne une paire contenant la clé médiane à faire remonter dans
     * le noeud interne “parent” et la nouvelle feuille issus du split à droite de
     * la valeur médiane.
     * </p>
     * On transformera le noeud sur lequel s’applique le split en le noeud à gauche
     * de la valeur médiane.
     * 
     * @param n      la feuille pleine à splitter
     * @param cle    la clé à insérer
     * @param valeur la valeur à insérer
     * @return la paire (clé médiane, nouvelle feuille droite)
     */
    private Paire splitFeuille(Noeud n, String cle, String valeur) {
        int total = n.taille; // déjà M+1 après insertion
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

        // La clé médiane est la première de la feuille droite
        return new Paire(droit.cles[0], droit);
    }

    /**
     * Elle applique un split sur un noeud interne plein au moment de l’ajout d’une
     * clé et d’un enfant.
     * 
     * @param n      le noeud interne plein à splitter
     * @param cle    la clé à insérer
     * @param enfant l'enfant à insérer
     * @return la paire (clé médiane, nouveau noeud droit)
     */
    private Paire splitInterne(Noeud n, String cle, Noeud enfant) {
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

        // retourner la clé médiane et le noeud droit
        return new Paire(cleMediane, droit);
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(this.racine);
        return b.toString();
    }

    /**
     * Affiche l'arbre B de façon lisible et minimaliste avec des flèches.
     */
    public void prettyPrint() {
        prettyPrintRec(racine, "", true);
    }

    private void prettyPrintRec(Noeud n, String prefix, boolean isTail) {
        // Noeud veya feuille anahtarlarını yaz
        System.out.println(prefix + (isTail ? "└── " : "├── ") + formatKeys(n));

        // Çocukları varsa, recursive çağrı
        if (!n.estFeuille) {
            for (int i = 0; i <= n.taille; i++) {
                boolean last = (i == n.taille);
                prettyPrintRec(n.enfants[i], prefix + (isTail ? "    " : "│   "), last);
            }
        }
    }

    // Yardımcı: anahtarları köşeli parantez içinde yazdır
    private String formatKeys(Noeud n) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n.taille; i++) {
            sb.append(n.cles[i]);
            if (i + 1 < n.taille)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        ArbreB arbre = testSimple();
        arbre.toString();
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

        System.out.println("cle <f> est ajoutee");
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

        return a;
    }

    public static ArbreB testCommunes() throws Exception {
        ArbreB a = new ArbreB();
        File f = new File("communes.txt");
        Scanner sc = new Scanner(f);
        int compteur = 0;
        long t0 = System.currentTimeMillis();

        while (sc.hasNext()) {
            // on invente une addresse d'enregistrement pour chaque commune
            a.ajouter(sc.nextLine(), String.format("F1.%s.%s", compteur / 1024, compteur % 1024));
            compteur++;
        }
        sc.close();

        long t1 = System.currentTimeMillis();
        System.out.println(String.format("temps de construction %s ms", t1 - t0));

        System.out.println(String.format("recherche pour 'Chinon' %s", a.recherche("Chinon")));

        System.out.println(String.format("recherche pour 'Mars' %s", a.recherche("Mars")));
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("temps de recherche %s ms", t2 - t1));

        return a;
    }

}
