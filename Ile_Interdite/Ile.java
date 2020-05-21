package Ile_Interdite;

import java.util.Random;

class Ile extends Observable {
    // On fixe la taille de la grille.
    public static final int HAUTEUR=7, LARGEUR=7;
    // On fixe le pourcentage de zones associées à un élément.
    public static final double SPECIAL=.9;
    // On stocke un tableau de zones.
    private final Zone[][] zones;
    // Nombre de joueurs
    public final int nbJoueurs = 4;
    // Tableau du nombre de joueurs
    Joueur[] joueur = new Joueur[nbJoueurs];
    // Int qui reconnait le joueur actuel, valeur comprise entre 0 et nbJoueurs - 1
    public int joueurActuel = 0;
    final Random rd = new Random();
    // Compteur qui compte le nombre d'actions du joueurs actuel
    public int compteur = 0;
    // Etat dans lequel les boutons permettent d'assecher une zone adjacente
    boolean modeAssecher = false;

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
            }
        }
        setHelico();
        setArtefacts();
    }

    private void setArtefacts() {
        Element[] arts = {Element.FEU, Element.AIR, Element.EAU, Element.TERRE};
        int x, y, i = 3;
        while(i>=0){
            x = rd.nextInt(LARGEUR);
            y = rd.nextInt(HAUTEUR);
            if(this.getZone(x, y).setArtefacts(arts[i])){
                i--;
            }
        }
    }

    public void ajouteCleeAleatoireJoueurActuel(){
        double chance  = this.getJoueurActuel().getZone().getChanceClef();
        Element elem = this.getJoueurActuel().getZone().getElement();
        if(this.rd.nextDouble() < chance && elem != Element.AUCUN && ! this.getJoueurActuel().hasKey(new Clef(elem))){
            this.getJoueurActuel().ajouteClefs(new Clef(elem));
        }
    }


    /**
     * Fonction zoneAdjacenteInnondee
     * @return vrai si une zone autour du joueur actuelle est innondée, faux sinon
     */
    public boolean zoneAdjacenteInnondee(){
        int x = this.getJoueurActuel().x;
        int y = this.getJoueurActuel().y;
        return (estInondee(x, y-1)
                || estInondee(x, y+1)
                || estInondee(x-1, y)
                || estInondee(x+1, y))
                ;
    }

    /**
     * Fonction estInondee
     * @param x la position x
     * @param y la position y
     * @return vrai si la zone est inondée, faux sinon
     */
    private boolean estInondee(int x, int y){
        if(x > LARGEUR || y>HAUTEUR || x<0 || y<0){
            return false;
        }
        return this.getZone(x, y).getEtat() == Etat.INNONDEE;
    }

    /**
     * Fonction qui permet d'assecher une zone par rapport un position
     * donnée par le joueur actuel
     * @param direction la direction par rapport au joueur actuel
     * @return vrai si on a pu assécher, faux sinon
     */
    public boolean asseche(Direction direction){
        int x = this.getJoueurActuel().x;
        int y = this.getJoueurActuel().y;
        switch (direction){
            case BAS: return this.getZone(x, y+1).asseche();
            case HAUT: return this.getZone(x, y-1).asseche();
            case GAUCHE: return this.getZone(x-1, y).asseche();
            case DROITE: return this.getZone(x+1, y).asseche();
        }
        return false;
    }

    /**
     * Fonction qui choisie une zone helicoptère au hasard
     * et qui attribue ses coordonnées à tous les joueurs
     */
    private void setHelico() {
        int x = this.rd.nextInt(LARGEUR);
        int y = this.rd.nextInt(HAUTEUR);
        this.zones[x][y].setHelico();
        for(int i = 0; i < nbJoueurs; i++){
            this.joueur[i] = new Joueur(this, x, y, "J" + i, i);
        }
    }

    /**
     * Fonction getJoueurActuel
     * @return Renvoie le joueur i
     */
    Joueur getJoueurActuel(){
        return this.getJoueur(joueurActuel);
    }

    /**
     * Fonction getJoueur
     * @param i le numéro du joueur
     * @return renvoie le joueur i
     */
    Joueur getJoueur(int i){
        return this.joueur[i];
    }

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
     * Fonction getZone
     * @param x position x
     * @param y position y
     * @return renvoie la zone à la position demandée
     */
    public Zone getZone(int x, int y) {
        return zones[x][y];
    }
}
