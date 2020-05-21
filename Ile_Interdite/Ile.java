package Ile_Interdite;

import java.util.Random;

class Ile extends Observable {
    // On fixe la taille de la grille.
    public static final int HAUTEUR=20, LARGEUR=20;
    // On fixe le pourcentage de zones associées à un élément.
    public static final double SPECIAL=.3;
    // On stocke un tableau de zones.
    private final Zone[][] zones;
    // Nombre de joueurs
    public final int nbJoueurs = 2;
    // Tableau du nombre de joueurs
    Joueur[] joueur = new Joueur[nbJoueurs];
    // Int qui reconnait le joueur actuel, valeur comprise entre 0 et nbJoueurs - 1
    public int joueurActuel = 0;
    private final Random rd = new Random();
    // Compteur qui compte le nombre d'actions du joueurs actuel
    public int compteur = 0;

    /**
     * Constructeur, rempli la grille de zones aléatoires
     */
    public Ile() {
        /*
          Pour éviter les problèmes aux bords, on ajoute une ligne et une
          colonne de chaque côté, dont les zones n'évolueront pas.
         */
        zones = new Zone[LARGEUR+1][HAUTEUR+1];
        for(int i=0; i<LARGEUR+1; i++) {
            for(int j=0; j<HAUTEUR+1; j++) {
                zones[i][j] = new Zone(this,i, j);
                setElem(zones[i][j]);
            }
        }
        setHelico();
    }

    /**
     * Permet d'attribuer un élément à une zone
     * @param z
     */
    private void setElem(Zone z){
        double pourcent = rd.nextDouble();
        if(pourcent < SPECIAL){
            switch (rd.nextInt(4)){
                case 0: z.setElem(Element.EAU); break;
                case 1: z.setElem(Element.AIR); break;
                case 2: z.setElem(Element.TERRE); break;
                case 3: z.setElem(Element.FEU); break;
            }
        }
    }

    private void setHelico() {
        int x = this.rd.nextInt(LARGEUR);
        int y = this.rd.nextInt(HAUTEUR);
        this.zones[x][y].setHelico();
        for(int i = 0; i < nbJoueurs; i++){
            this.joueur[i] = new Joueur(this, x, y, "J" + i);
        }
    }

    /**
     * @return Renvoie le joueur i
     */
    Joueur getJoueurActuel(){
        return this.joueur[joueurActuel];
    }

    /**
     * @param i le numéro du joueur
     * @return renvoie le joueur i
     */
    Joueur getJoueur(int i){
        return this.joueur[i];
    }

    //TODO condition d'arrêt quand toutes les zones sont submergées
    /**
     * Fonction qui innonde 3 zones aléatoires, si la zone est déjà inondé, elle sera submergée
     * On ne submerge pas les zones déjà subermgées
     */
    public void fin_de_tour() {
        int x, y;
        for(int i = 0; i < 3; i++) {
            do {
                x = this.rd.nextInt(LARGEUR);
                y = this.rd.nextInt(HAUTEUR);
            } while(zones[x][y].estSubmerge());
            zones[x][y].innonde();
        }
        notifyObservers();
    }

    /**
     * @param x position x
     * @param y position y
     * @return renvoie la zone à la position demandée
     */
    public Zone getZone(int x, int y) {
        return zones[x][y];
    }
}
