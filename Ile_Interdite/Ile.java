package Ile_Interdite;

import java.util.Random;

class Ile extends Observable {
    final Random rd = new Random();
    // On fixe le nombre d'actions max
    public static final int MAXACTIONS = 3;
    // On fixe la taille de la grille.
    public static final int HAUTEUR=7, LARGEUR=7;
    // On stocke un tableau de zones.
    private final Zone[][] zones;
    // Nombre de joueurs
    public static final int nbJoueurs = 2;
    // Tableau du nombre de joueurs
    Joueur[] joueur = new Joueur[nbJoueurs];
    // Int qui reconnait le joueur actuel, valeur comprise entre 0 et (nbJoueurs - 1)
    public int joueurActuel = 0;
    // Compteur qui compte le nombre d'actions du joueurs actuel
    public int compteur = 0;
    // Etat dans lequel les boutons permettent d'assecher une zone adjacente
    boolean modeAssecher = false;
    //Zone helicoptère
    private Zone helico;
    //Zones artefacts
    private final Zone[] zoneArte = new Zone[4];
    // Si la partie est gagné, on envoie l'information à partir de ce boolean
    boolean isWin = false;
    // Si la partie est perdue, on envoie l'information à partir de ce boolean
    boolean isLost = false;
    //Paquets de cartes
    PaquetCartes<Zone> cartesZones;
    PaquetCartes<Element> cartesElements;

    /**
     * Constructeur, rempli la grille de zones aléatoires
     */
    public Ile() {
        this.cartesZones = new PaquetCartes<>();
        this.zones = new Zone[LARGEUR+1][HAUTEUR+1];
        for(int i=0; i<LARGEUR+1; i++) {
            for(int j=0; j<HAUTEUR+1; j++) {
                this.zones[i][j] = new Zone(this,i, j);
                this.cartesZones.add(this.zones[i][j]);
            }
        }
        setHelico();
        setArtefacts();
        this.cartesZones.melangerPaquet();
        fillCartesElements();
    }

    /**
     * Rempli le paquet de cartes éléments des éléments suivants
     * - AUCUN
     * - MONTEEDESEAUX
     * - FEU
     * - AIR
     * - EAU
     * - TERRE
     */
    private void fillCartesElements(){
        this.cartesElements = new PaquetCartes<>();
        for(int i = 0; i < 15; i++){
            this.cartesElements.add(Element.AUCUN);
        }
        for(int i = 0; i < 2; i++){
            this.cartesElements.add(Element.MONTEEDESEAUX);
            this.cartesElements.add(Element.FEU);
            this.cartesElements.add(Element.AIR);
            this.cartesElements.add(Element.EAU);
            this.cartesElements.add(Element.TERRE);
        }
        this.cartesElements.melangerPaquet();
    }

    /**
     * Fonction qui choisi 4 zones aléatoires et qui y place les 4 artéfacts
     * Une zone ne peut contenir qu'un seul artefact
     */
    private void setArtefacts() {
        Element[] arts = {Element.FEU, Element.AIR, Element.EAU, Element.TERRE};
        int x, y, i = 3;
        while(i>=0){
            x = rd.nextInt(LARGEUR);
            y = rd.nextInt(HAUTEUR);
            if(this.getZone(x, y).setArtefacts(arts[i])){
                this.zoneArte[i] = this.getZone(x, y);
                i--;
            }
        }
    }

    /**
     * Fonction qui tire une carte à la fin du tour
     * Les issues possibles sont:
     * - La montée des eaux de la zone du joueur actuel
     * - Le joueur actuel trouve une clée
     * - Rien ne se passe
     */
    public void tirageCarteFinDeTour(){
        Element carte = this.cartesElements.tirerCarte();
        switch (carte){
            case AUCUN: break;
            case MONTEEDESEAUX:
                this.getJoueurActuel().getZone().innonde();
                System.out.print("Montée des eaux !");
                break;
            case AIR:
            case FEU:
            case EAU:
            case TERRE: this.getJoueurActuel().ajouteClefs(new Clef(carte));
            System.out.print("trouve une clée !");
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
     * Fonction qui permet d'assecher une zone par rapport à une position
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
        this.helico = this.zones[x][y];
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
     * Fonction qui innonde 3 zones aléatoires.
     * Si la zone est déjà inondé, elle sera submergée
     * On ne submerge pas les zones déjà subermgées
     */
    public void fin_de_tour() {
        int i = 0;
        while(i < 3){
            if(this.cartesZones.tirerCarte().innonde()){
                i++;
            }
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


    /**
     * Fonction qui test si la partie est finie ou non
     * WIN :
     * - tous les joueurs sont sur la zone helico
     * - ils ont les 4 artefacts
     * LOSE :
     * - un joueur est submergé
     * - un artefact est submergé
     * - la zone helico est submergée
     * @return WIN si la partie est gagné, LOSE si elle est perdue, NONE sinon
     */
    public Fin testEnd(){
        if(this.allArtefacts() && allHeliport()){
            return Fin.WIN;
        }
        if(this.helico.estSubmerge() || zoneArtefactSubmergee() || this.joueurSurZoneSubmergee()){
            return Fin.LOSE;
        }
        return Fin.NONE;
    }

    /**
     * Fonction joueurSurZoneSubmergee
     * @return vrai si au moins un joueur est sur une zone submergée, faux si aucun n'est submergé
     */
    private boolean joueurSurZoneSubmergee(){
        for(Joueur j: this.joueur){
            if(j.getZone().estSubmerge()){
                return true;
            }
        }
        return false;
    }

    /**
     * Fonction allArtefacts
     * @return vrai si il y a tous les artefacts au total, faux sinon
     */
    private boolean allArtefacts(){
        int compteur = 0;
        for(Joueur j: this.joueur){
            for(Element ignored : j.getAllArtefacts()){
                compteur += 1;
            }
        }
        return compteur == 4;
    }

    /**
     * Fonction zoneArtefactSubmergee
     * @return vrai si une zone artefact est subermegée, faux sinon
     */
    private boolean zoneArtefactSubmergee(){
        for(Zone z: this.zoneArte){
            if(z.estSubmerge()){
                return true;
            }
        }
        return false;
    }

    /**
     * Fonction allHeliport
     * @return vrai si tous les joueurs sont sur la zone helicoptère, faux sinon
     */
    private boolean allHeliport(){
        for(Joueur j: this.joueur){
            if(j.x != this.helico.x || j.y != this.helico.y){
                return false;
            }
        }
        return true;
    }
}
