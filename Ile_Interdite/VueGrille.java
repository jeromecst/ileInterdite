package Ile_Interdite;

import javax.swing.*;
import java.awt.*;

/*
  Une classe pour représenter la zone d'affichage des zones.

  JPanel est une classe d'éléments graphiques, pouvant comme JFrame contenir
  d'autres éléments graphiques.

  Cette vue va être un observateur du modèle et sera mise à jour à chaque
  nouvelle génération des zones.
 */
class VueGrille extends JPanel implements Observer {
    /* On maintient une référence vers le modèle. */
    private final Ile ile;
    /* Définition d'une taille (en pixels) pour l'affichage des zones. */
    private final static int TAILLE = 60;
    private final static int offset = ((int) (TAILLE*7./30.));
    Color[] colorList = {Color.RED, Color.BLUE, Color.GREEN, Color.CYAN.brighter(), Color.GREEN.darker()};

    /* Constructeur. */
    public VueGrille(Ile ile) {
        this.ile = ile;
        /* On enregistre la vue [this] en tant qu'observateur de [ile]. */
        ile.addObserver(this);
        /*
          Définition et application d'une taille fixe pour cette zone de
          l'interface, calculée en fonction du nombre de zones et de la
          taille d'affichage.
         */
        Dimension dim = new Dimension(TAILLE*Ile.LARGEUR + 39*this.ile.joueur.length,
                TAILLE*(Ile.HAUTEUR + 2*this.ile.joueur.length));
        this.setPreferredSize(dim);
    }

    /*
      L'interface [Observer] demande de fournir une méthode [update], qui
      sera appelée lorsque la vue sera notifiée d'un changement dans le
      modèle. Ici on se content de réafficher toute la grille avec la méthode
      prédéfinie [repaint].
     */
    public void update() { repaint(); }

    /*
      Les éléments graphiques comme [JPanel] possèdent une méthode
      [paintComponent] qui définit l'action à accomplir pour afficher cet
      élément. On la redéfinit ici pour lui confier l'affichage des zones.

      La classe [Graphics] regroupe les éléments de style sur le dessin,
      comme la couleur actuelle.
     */
    public void paintComponent(Graphics g) {
        super.repaint();
        /* Pour chaque cellule... */
        paintActionsRestantes(g, this.ile.compteur);
        paintGrille(g);
        paintClefs(g);
        paintArte(g);
        for(int i=0; i<=Ile.LARGEUR-1; i++) {
            for(int j=0; j<=Ile.HAUTEUR-1; j++) {
                /*
                  ... Appeler une fonction d'affichage auxiliaire.
                  On lui fournit les informations de dessin [g] et les
                  coordonnées du coin en haut à gauche.
                 */
                paint(g, ile.getZone(i, j), (i)*TAILLE, (j)*TAILLE);
            }
        }
        paintPlayers(g);
        if(this.ile.isLost){
            paintMessage(g, "YOU LOSE");
        }
        if(this.ile.isWin){
            paintMessage(g, "YOU WIN");
        }
    }

    private void paintMessage(Graphics g, String msg) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, TAILLE*Ile.LARGEUR*10, TAILLE*Ile.HAUTEUR*10);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE));
        g.drawString(msg,(int)( TAILLE*Ile.LARGEUR/4.), (int)( TAILLE*Ile.HAUTEUR/2.) );
    }

    private void paintGrille(Graphics g) {
        g.setColor(Color.BLUE.darker());
        g.fillRect(0, 0, TAILLE*Ile.HAUTEUR + offset, TAILLE*Ile.LARGEUR +offset);
    }

    /**
     * Fonction paintClefs
     * Affiche les clefs de chaque joueur si il en possède
     * @param g le graphique
     */
    private void paintClefs(Graphics g){
        for(Joueur j: ile.joueur){
            if(j.hasKey()){
                paintClefsJoueur(g, j);
            }
        }
    }

    /**
     * Fonction paintClefsJoueurs
     * Affiche l'ensemble des clefs que le joueur possède
     * @param g le graphique
     * @param j le joueur
     */
    private void paintClefsJoueur(Graphics g, Joueur j){
        int beginningY = (int) (TAILLE*(Ile.HAUTEUR + 1./2.) + 4*offset);
        int beginningX = 2*offset;
        int beginningXAfterText = beginningX + TAILLE*2;
        int beginningXFontAfterText = beginningXAfterText + offset/4;
        int positionY = beginningY + TAILLE*j.getNum()/2;
        int positionYBoule = positionY - 2*offset;
        int positionYtext = (int)(positionYBoule + 1.4*offset);
        int tailleBoule = TAILLE/2;
        int tailleFont = TAILLE/6;
        g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE/2));
        g.setColor(Color.BLACK);
        g.drawString(j.toString() +" clefs : ", 1, positionY);
        g.setFont(new Font("TimesRoman", Font.BOLD, tailleFont));
        for(Clef c: j.getAllKeys()){
            switch (c.getType()){
                case EAU:
                    g.setColor(Color.BLUE);
                    g.fillOval(beginningXAfterText, positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.WHITE);
                    g.drawString("EAU", beginningXFontAfterText, positionYtext);
                    break;
                case TERRE:
                    g.setColor(Color.RED.darker());
                    g.fillOval((int) (beginningXAfterText*1.2), positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.WHITE);
                    g.drawString("TER", (int) (beginningXFontAfterText*1.2), positionYtext);
                    break;
                case FEU:
                    g.setColor(Color.YELLOW);
                    g.fillOval((int) (beginningXAfterText*1.4), positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.BLACK);
                    g.drawString("FEU", (int) (beginningXFontAfterText*1.4), positionYtext);
                    break;
                case AIR:
                    g.setColor(Color.GRAY);
                    g.fillOval((int) (beginningXAfterText*1.6), positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.WHITE);
                    g.drawString("AIR", (int) (beginningXFontAfterText*1.6), positionYtext);
                    break;
            }
        }
    }

    /**
     * Fonction paintArte
     * Affiche les artefacts de chaque joueur si il en possède
     * @param g le graphique
     */
    private void paintArte(Graphics g){
        for(Joueur j: ile.joueur){
            if(j.hasArtefact()){
                paintArteJoueurs(g, j);
            }
        }
    }

    /**
     * Fonction paintArteJoueurs
     * Affiche l'ensemble des artefacts que le joueur possède
     * @param g le graphique
     * @param j le joueur
     */
    private void paintArteJoueurs(Graphics g, Joueur j){
        int beginningY = TAILLE*(Ile.HAUTEUR +1) + 3*offset;
        int beginningX = 2*offset;
        int beginningXAfterText = beginningX + TAILLE*3;
        int beginningXFontAfterText = beginningXAfterText + offset/4;
        int positionY = beginningY + TAILLE*(ile.joueur.length/2);
        int positionYBoule = positionY - 2*offset;
        int positionYtext = (int)(positionYBoule + 1.4*offset);
        int tailleBoule = TAILLE/2;
        int tailleFont = TAILLE/6;
        g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE/2));
        g.setColor(Color.BLACK);
        g.drawString("Artefacts : ", 1, positionY);
        g.setFont(new Font("TimesRoman", Font.BOLD, tailleFont));
        for(Element e: j.getAllArtefacts()){
            switch (e){
                case EAU:
                    g.setColor(Color.BLUE);
                    g.fillOval(beginningXAfterText, positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.WHITE);
                    g.drawString("EAU", beginningXFontAfterText, positionYtext);
                    break;
                case TERRE:
                    g.setColor(Color.RED.darker());
                    g.fillOval((int) (beginningXAfterText*1.2), positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.WHITE);
                    g.drawString("TER", (int) (beginningXFontAfterText*1.2), positionYtext);
                    break;
                case FEU:
                    g.setColor(Color.YELLOW);
                    g.fillOval((int) (beginningXAfterText*1.4), positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.BLACK);
                    g.drawString("FEU", (int) (beginningXFontAfterText*1.4), positionYtext);
                    break;
                case AIR:
                    g.setColor(Color.GRAY);
                    g.fillOval((int) (beginningXAfterText*1.6), positionYBoule, tailleBoule, tailleBoule);
                    g.setColor(Color.WHITE);
                    g.drawString("AIR", (int) (beginningXFontAfterText*1.6), positionYtext);
                    break;
            }

        }
    }

    /**
     * Fonction paintActionsRestantes
     * Affiche le numero du joueur et le nombre d'action qu'il lui reste à faire
     * @param g le graphic
     * @param compteur d'actions restantes
     */
    private void paintActionsRestantes(Graphics g, int compteur){
        int beginningY = (TAILLE*(Ile.HAUTEUR ) + 3*offset);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (Ile.LARGEUR +5)*TAILLE, (Ile.HAUTEUR + 5)*TAILLE);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, (int) (TAILLE/2.5)));
        g.drawString(this.ile.getJoueurActuel().toString() +" : " + (Ile.MAXACTIONS - compteur) + " actions restantes" , offset, beginningY);
    }

    /**
     * Fonction paintPlayers
     * Affiche les joueurs sur la grille
     * @param g le graphique
     */
    private void paintPlayers(Graphics g){
        for(int i = 0; i < Ile.nbJoueurs; i++){
            int x = this.ile.getJoueur(i).x*TAILLE;
            int y = this.ile.getJoueur(i).y*TAILLE;
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE/2-offset));
            g.drawString(this.ile.getJoueur(i).toString(), x+offset, y+offset);
            g.setColor(colorList[i]);
            g.fillOval(x+offset, y+offset, TAILLE- offset, TAILLE -offset);
        }
    }

    /**
     * Fonction paint
     * Affiche la grille, les artefacts, les états...
     * @param g le graphique
     * @param z la zone à afficher
     * @param x la position x
     * @param y la position y
     */
    private void paint(Graphics g, Zone z, int x, int y) {
        g.setColor(Color.BLUE.darker());
        g.fillRect(x, y, TAILLE, TAILLE);
        switch (z.getEtat()){
            case NORMAL: g.setColor(Color.GREEN.darker()); break;
            case INNONDEE: g.setColor(Color.CYAN.darker()); break;
            case SUBMERGEE: g.setColor(Color.BLUE.darker()); break;
        }
        g.fillRect(x + offset, y + offset, TAILLE - offset, TAILLE  - offset);
        if(z.isHelico()){
            g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE-offset));
            g.setColor(Color.BLACK);
            g.drawString("H", x+offset + TAILLE*4/30, y+offset + TAILLE*21/30);
            g.setColor(Color.WHITE);
            g.drawString("H", x+offset + 2*TAILLE/30, (y+offset + 19*TAILLE/30));
        }
        switch (z.getArtefact()){
            case TERRE:
                g.setColor(Color.RED.darker());
                g.fillOval(x+offset, y+offset, TAILLE/2-offset, TAILLE/2 -offset);
                break;
            case EAU:
                g.setColor(Color.BLUE.brighter());
                g.fillOval(x+offset, y+offset, TAILLE/2-offset, TAILLE/2 -offset);
                break;
            case FEU:
                g.setColor(Color.YELLOW);
                g.fillOval(x+offset, y+offset, TAILLE/2-offset, TAILLE/2 -offset);
                break;
            case AIR:
                g.setColor(Color.LIGHT_GRAY.brighter());
                g.fillOval(x+offset, y+offset, TAILLE/2-offset, TAILLE/2 -offset);
                break;
        }
    }
}
