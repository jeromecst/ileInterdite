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
    private final static int TAILLE = 50;
    private final static int offset = ((int) (TAILLE*7./30.));
    Color[] colorList = {Color.RED, Color.BLUE, Color.GREEN, Color.CYAN.brighter()};

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
        Dimension dim = new Dimension(TAILLE*Ile.LARGEUR,
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
        paintClefs(g);
        for(int i=0; i<=Ile.LARGEUR; i++) {
            for(int j=0; j<=Ile.HAUTEUR -1; j++) {
                /*
                  ... Appeler une fonction d'affichage auxiliaire.
                  On lui fournit les informations de dessin [g] et les
                  coordonnées du coin en haut à gauche.
                 */
                paint(g, ile.getZone(i, j), (i)*TAILLE, (j)*TAILLE);
            }
        }
        paintPlayers(g);
    }
    /*
      Fonction auxiliaire de dessin d'une cellule.
      Ici, la classe [Zone] ne peut être désignée que par l'intermédiaire
      de la classe [Ile] à laquelle elle est interne, d'où le type
      [Ile.Zone].
      Ceci serait impossible si [Zone] était déclarée privée dans [Ile].
     */
    private void paintClefs(Graphics g){
        for(Joueur j: ile.joueur){
            if(j.hasKey()){
                paintClefsJoueur(g, j);
            }
        }
    }

    //TODO optimiser
    private void paintClefsJoueur(Graphics g, Joueur j){
        int positionY = TAILLE*(Ile.HAUTEUR + j.getNum() + 1);
        g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE-offset));
        g.setColor(Color.BLACK);
        g.drawString(j.toString() +" : ", 1, positionY);
        for(Clef c: j.getAllKeys()){
            switch (c.getType()){
                case EAU:
                    g.setColor(Color.BLUE);
                    g.fillOval(offset*3 + TAILLE, positionY-offset*4, TAILLE , TAILLE);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE/3));
                    g.drawString("EAU", (int) (offset*3.5 +TAILLE), positionY-offset);
                    break;
                case TERRE:
                    g.setColor(Color.RED.darker());
                    g.fillOval(offset*3 + TAILLE*2, positionY-offset*4, TAILLE , TAILLE);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("TimesRoman", Font.BOLD, (int) (TAILLE/3.5)));
                    g.drawString("TERRE", (int) (offset*3.5 +TAILLE*2), positionY-offset);
                    break;
                case FEU:
                    g.setColor(Color.YELLOW);
                    g.fillOval(offset*3 + TAILLE*3, positionY-offset*4, TAILLE , TAILLE);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE/3));
                    g.drawString("FEU", (int) (offset*3.5 +TAILLE*3), positionY-offset);
                    break;
                case AIR:
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(offset*3 + TAILLE*4, positionY-offset*4, TAILLE , TAILLE);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE/3));
                    g.drawString("AIR", (int) (offset*3.5 +TAILLE*4), positionY-offset);
                    break;
            }

        }
    }

    private void paintActionsRestantes(Graphics g, int compteur){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (Ile.LARGEUR +5)*TAILLE, (Ile.HAUTEUR + 5)*TAILLE);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, TAILLE-offset));
        g.drawString(this.ile.getJoueurActuel().toString() +" : " + (3 - compteur) + " actions restantes" , 0, TAILLE*21);
    }
    private void paintPlayers(Graphics g){
        for(int i = 0; i < this.ile.nbJoueurs; i++){
            int x = this.ile.getJoueur(i).x*TAILLE;
            int y = this.ile.getJoueur(i).y*TAILLE;
            g.setColor(colorList[i]);
            g.fillOval(x+offset, y+offset, TAILLE- offset, TAILLE -offset);
        }
    }
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
