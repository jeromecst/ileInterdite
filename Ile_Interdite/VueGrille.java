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
    private Ile ile;
    /* Définition d'une taille (en pixels) pour l'affichage des zones. */
    private final static int TAILLE = 30;

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
                TAILLE*Ile.HAUTEUR);
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
        for(int i=0; i<=Ile.LARGEUR; i++) {
            for(int j=0; j<=Ile.HAUTEUR; j++) {
                /*
                  ... Appeler une fonction d'affichage auxiliaire.
                  On lui fournit les informations de dessin [g] et les
                  coordonnées du coin en haut à gauche.
                 */
                paint(g, ile.getZone(i, j), (i)*TAILLE, (j)*TAILLE);
            }
        }
        paintPlayer(g, ile.joueur);
    }
    /*
      Fonction auxiliaire de dessin d'une cellule.
      Ici, la classe [Zone] ne peut être désignée que par l'intermédiaire
      de la classe [Ile] à laquelle elle est interne, d'où le type
      [Ile.Zone].
      Ceci serait impossible si [Zone] était déclarée privée dans [Ile].
     */
    private void paintPlayer(Graphics g, Joueur j){
        int offset = 7;
        int x = j.x*TAILLE;
        int y = j.y*TAILLE;
        g.setColor(Color.RED);
        g.fillOval(x+offset, y+offset, TAILLE- offset, TAILLE -offset);
    }
    private void paint(Graphics g, Zone z, int x, int y) {
        int offset = 7;
        /* Sélection d'une couleur. */
        g.setColor(Color.BLUE.darker());
        g.fillRect(x, y, TAILLE, TAILLE);
        switch (z.getEtat()){
            case NORMAL: g.setColor(Color.GREEN.darker()); break;
            case INNONDEE: g.setColor(Color.CYAN.darker()); break;
            case SUBMERGEE: g.setColor(Color.BLUE.darker()); break;
        }
        if(z.isHelico()){
            g.setColor(Color.BLACK);
        }
        /* Coloration d'un rectangle. */
        g.fillRect(x + offset, y + offset, TAILLE - offset, TAILLE  - offset);
    }
}
