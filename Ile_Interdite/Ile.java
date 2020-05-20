package Ile_Interdite;

import java.util.Random;

class Ile extends Observable {
    // On fixe la taille de la grille.
    public static final int HAUTEUR=20, LARGEUR=20;
    // On fixe le pourcentage de zones associées à un élément.
    public static final double SPECIAL=.3;
    // On stocke un tableau de zones.
    private final Zone[][] zones;
    Joueur joueur;
    private final Random rd = new Random();

    /* Construction : on initialise un tableau de zones. */
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
        this.joueur = new Joueur(this, x, y);
    }

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


    /*
      Une méthode pour renvoyer la zone aux coordonnées choisies (sera
      utilisée par la vue).
     */
    public Zone getZone(int x, int y) {
        return zones[x][y];
    }
}
