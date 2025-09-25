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
     * Retourne l’indice auquel un clé devrait être insérer dans un noeud interne ou
     * une feuille.
     * <p>
     * On renverra M dans le cas, on devrait insérer dans un noeud plein après la
     * dernière clé
     * </p>
     * 
     * @param n   la noeud dans lequel on veurt insérer la clé
     * @param cle le valeur de la clé à insérer
     * @return l'indice où insérer la clé dans la noeud
     */
    private int positionPour(Noeud n, String cle) {

        if (n.taille == M) {
            return M; // le noeud est plein, on insère après la dernière clé
        }

        for (int i = 0; i < n.taille; i++) {

            int cmp = cle.compareTo(n.cles[i]); // cle - n.cles[i]
            if (cmp == 0)
                return i; // la clé existe déjà
            else if (cmp < 0)
                return i; // la clé doit être insérée avant n.cles[i]
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
     */
    private void decalerDeUn(Noeud n, int pos) {
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
     */
    private void insererA(Noeud n, int pos, String cle, String valeur, Noeud enfant) {

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
     * Elle fait appel à une nouvelle
     * méthode auxiliaire récursive ajouterRec(Noeud n, String cle, String valeur).
     * 
     * <p>
     * Cette première version doit permettent d’ajouter une association clé, valeur
     * dans un arbre B dont aucune feuille n’est pleine (aucun split ne peut
     * survenir).(mettre M=10 pour tester)
     * </p>
     * 
     * @param cle    la clé a ajouter
     * @param valeur la valeur associée a la clé
     */
    public void ajouter(String cle, String valeur) {
        Paire split = ajouterRec(racine, cle, valeur);
        if (split != null) {
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
     * @param n      noeud racine du sous-arbre
     * @param cle    la clé a ajouter
     * @param valeur la valeur associée a la clé
     */
    private Paire ajouterRec(Noeud n, String cle, String valeur) {
        if (n.estFeuille) {
            int pos = positionPour(n, cle);
            insererA(n, pos, cle, valeur, null);

            if (n.taille > M) {
                return splitFeuille(n, cle, valeur);
            }
            return null;

        } else {
            int i = positionPour(n, cle);
            Paire paire = ajouterRec(n.enfants[i], cle, valeur);

            if (paire != null) {
                insererA(n, i, paire.cle, null, paire.noeud);

                if (n.taille > M) {
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
        Noeud droit = new Noeud(true);

        // position mediane dans le noeud avec un élément en plus
        int posMed = M / 2;
        int posAjout = positionPour(n, cle); // nouveau position de nouvel cle

        // copie de la partie droite du noeud

        // si on insere à gauche de la position mediane,
        // les elt droit commence à la position mediane ie decal = 0
        int decal = (posAjout <= posMed) ? 0 : 1;
        for (int i = 0; posMed + decal + i < n.taille; i++)
            insererA(droit, i, n.cles[posMed + decal + i], n.valeurs[posMed + decal + i],
                    null);
        // quand la clé mediane est la clé insérée, la valeur droite est la valeur
        // insérée
        // sinon c'est l'enfant à droite de la clé médiane
        if (posMed == posAjout)
            droit.valeurs[0] = valeur;
        else
            droit.valeurs[0] = n.valeurs[posMed + decal];

        n.taille = M / 2;
        if (posAjout > posMed)
            insererA(droit, posAjout - posMed - 1, cle, valeur, null);
        else if (posAjout < posMed)
            insererA(n, posAjout, cle, valeur, null);

        String c = (posMed == posAjout) ? cle : n.cles[posMed];
        Paire p = new Paire(c, droit);

        return p; // envoie la clé mediane et le nouveau noeud droit
    }

    private Paire splitInterne(Noeud n, String cle, Noeud enfant) {
        Noeud droit = new Noeud(false);

        // position mediane dans le noeud avec un élément en plus
        int posMed = M / 2;
        int posAjout = positionPour(n, cle);

        // copie de la partie droite du noeud

        // si on insere à gauche de la position mediane,
        // les elt droit commence à la position mediane ie decal = 0
        int decal = (posAjout <= posMed) ? 0 : 1;
        for (int i = 0; posMed + decal + i < n.taille; i++)
            insererA(droit, i, n.cles[posMed + decal + i], null, n.enfants[posMed + decal + i + 1]);
        // quand la clé mediane est la clé insérée, le premier enfant droit est l'enfant
        // insérée
        // sinon c'est l'enfant à droite de la clé médiane
        if (posMed == posAjout)
            droit.enfants[0] = enfant;
        else
            droit.enfants[0] = n.enfants[posMed + decal];

        n.taille = M / 2;
        if (posAjout > posMed)
            insererA(droit, posAjout - posMed - 1, cle, null, enfant);
        else if (posAjout < posMed)
            insererA(n, posAjout, cle, null, enfant);

        String c = (posMed == posAjout) ? cle : n.cles[posMed];
        Paire p = new Paire(c, droit);

        return p;
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(this.racine);
        return b.toString();
    }

    public static void main(String[] args) throws Exception {
        ArbreB arbre = testSimple();
        arbre.toString();
    }

    public static ArbreB testSimple() {
        ArbreB a = new ArbreB();

        a.ajouter("e", "eclat");
        System.out.println(a);

        a.ajouter("a", "ajout");
        System.out.println(a);

        a.ajouter("c", "coucou");
        System.out.println(a);

        a.ajouter("b", "bouh");
        System.out.println(a);

        a.ajouter("d", "doudou");
        System.out.println(a);

        a.ajouter("h", "herbe");
        System.out.println(a);

        a.ajouter("i", "iris");
        System.out.println(a);

        a.ajouter("f", "flot");
        System.out.println(a);

        a.ajouter("g", "girafe");
        System.out.println(a);

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
