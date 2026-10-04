package arbreb;

final class Noeud {

    final boolean estFeuille;
    final String[] cles;
    final String[] valeurs;
    final Noeud[] enfants;
    int taille = 0;

    String minKey;
    String maxKey;

    // ----------------- Constructor ---------------

    Noeud(boolean feuille) {
        this.estFeuille = feuille;
        this.cles = new String[ArbreB.M];
        if (estFeuille) {
            this.valeurs = new String[ArbreB.M];
            this.enfants = null;
        } else {
            this.valeurs = null;
            this.enfants = new Noeud[ArbreB.M + 1];
        }
    }

    // ------------------- Methods --------------------

    /**
     * Retourne l'enfant à parcourir pour une clé.
     */
    int positionEnfant(String cle) {
        for (int i = 0; i < taille; i++) {
            if (cle.compareTo(cles[i]) < 0) {
                return i;
            }
        }

        return taille;
    }

    /**
     * Retourne la position d'insertion d'une clé dans une feuille.
     * Si la clé existe déjà, retourne sa position.
     *
     * @param cle la clé à insérer
     * @return la position d'insertion
     * @throws NullPointerException     si le noeud ou la clé est null
     * @throws IllegalArgumentException si la taille du noeud est invalide
     */
    int positionInsertion(String cle) {

        if (cle == null) {
            throw new NullPointerException("Erreur: la clé est null.");
        }

        if (this.taille < 0 || this.taille > ArbreB.M) {
            throw new IllegalArgumentException(
                    "Erreur: taille du noeud invalide (" + this.taille + "),ArbreB.M=" + ArbreB.M);
        }

        for (int i = 0; i < this.taille; i++) {
            int cmp = cle.compareTo(this.cles[i]);

            if (cmp <= 0) {
                return i;
            }
        }

        return this.taille;
    }

    /**
     * Elle insère, dans une feuille, une clé et une valeur à une position
     * donnée et dans
     * <p>
     * un noeud interne , insère une clé à une position donnée et un enfant à sa
     * droite.
     * </p>
     *
     * @param n      le noeud dans lequel on insère
     * @param pos    la position dans le noeud où insérer
     * @param cle    la clé à insérer
     * @param valeur la valeur à insérer (null si n n'est pas une feuille)
     * @param enfant l'enfant à insérer (null si n est une feuille)
     *
     * @throws NullPointerException     si noeud ou clé est null
     * @throws IllegalArgumentException si la position est invalide ou si le
     *                                  noeud est plein, ou si valeur/enfant est
     *                                  null dans un contexte
     *                                  inapproprié
     */
    void insererA(int pos, String cle, String valeur, Noeud enfant) {

        // Error handling

        if (cle == null) {
            throw new NullPointerException("Erreur: clé est null.");
        }
        if (pos < 0 || pos > this.taille) {
            throw new IllegalArgumentException(
                    "Erreur: position " + pos + " invalide pour un noeud de taille " + this.taille);
        }
        if (this.taille >= ArbreB.M) {
            throw new IllegalArgumentException(
                    "Erreur: le noeud est déjà plein (taille=" + this.taille + ",ArbreB.M=" + ArbreB.M + ")");
        }

        // Vérification cohérence feuille/interne
        if (this.estFeuille && valeur == null) {
            throw new IllegalArgumentException("Erreur: valeur ne peut pas être null dans une feuille.");
        }
        if (!this.estFeuille && enfant == null) {
            throw new IllegalArgumentException("Erreur: enfant ne peut pas être null dans un noeud interne.");
        }

        // ArbreB.Main logic
        this.decalerDeUn(pos);
        this.cles[pos] = cle;

        if (this.estFeuille) {
            this.valeurs[pos] = valeur;
        } else {
            this.enfants[pos + 1] = enfant;
        }
        this.taille++;
    }

    /**
     * Elle décale dans un noeud non plein les clés d’une case vers la droite
     * pour laisser la position passée en argument vide
     * <p>
     * On décalera les valeurs dans une feuille de laArbreB.MêmeArbreB.Manière. Pour
     * les
     * noeuds internes, seules les enfants à droite des clés décalés sont
     * déplacés.
     * </p>
     *
     * @param n   le noeud dans lequel on décale les clés
     * @param pos la position à libérer
     *
     * @throws NullPointerException     si noeud est null
     * @throws IllegalArgumentException si la position est invalide ou si le
     *                                  noeud est plein
     */
    private void decalerDeUn(int pos) {

        // Error handling

        if (pos < 0 || pos > this.taille) {
            throw new IllegalArgumentException(
                    "Erreur: position " + pos + " invalide pour un noeud de taille " + this.taille);
        }
        if (this.taille >= ArbreB.M) {
            throw new IllegalArgumentException(
                    "Erreur: impossible de décaler, le noeud est déjà plein (taille=" + this.taille + ",ArbreB.M="
                            + ArbreB.M + ")");
        }

        // ArbreB.Main logic
        if (this.taille == ArbreB.M) {
            System.err.println("Noeud est plein");
        }

        for (int i = this.taille - 1; i >= pos; i--) {
            this.cles[i + 1] = this.cles[i];
            if (this.estFeuille) {
                this.valeurs[i + 1] = this.valeurs[i];
            }
        }

        if (!this.estFeuille) {
            for (int i = this.taille; i >= pos + 1; i--) {
                this.enfants[i + 1] = this.enfants[i];
            }
            // Not: enfants[pos] yerinde kalır; yeni çocuk pos+1'e konur.
        }
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        if (this.estFeuille) {
            b.append("Feuille(");
        } else {
            b.append("Noeud(");
        }

        if (!this.estFeuille) {
            b.append(this.enfants[0]).append(" | ");
        }

        for (int i = 0; i < this.taille; i++) {
            b.append(this.cles[i]);
            b.append((this.estFeuille) ? ": " : " | ");
            if (this.estFeuille) {
                b.append(this.valeurs[i]);
            } else {
                b.append(this.enfants[i + 1]);
            }
            if (i + 1 < this.taille) {
                b.append((this.estFeuille) ? ", " : " | ");
            }
        }

        b.append(")");

        return b.toString();
    }

    /**
     * ArbreB.Met à jour les attributsArbreB.MinKey etArbreB.MaxKey d'un noeud.
     */
    void updateRange() {
        if (estFeuille) {
            if (taille > 0) {
                minKey = cles[0];
                maxKey = cles[taille - 1];
            }
        } else {
            if (taille > 0) {
                minKey = enfants[0].minKey;
                maxKey = enfants[taille].maxKey;
            }
        }
    }

}
