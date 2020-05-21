package Ile_Interdite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Interface des objets observateurs.
 */
interface Observer {
    /*
      Un observateur doit posséder une méthode [update] déclenchant la mise à
      jour.
     */
    void update();
    /*
      La version officielle de Java possède des paramètres précisant le
      changement qui a eu lieu.
     */
}

/**
 * Classe des objets pouvant être observés.
 */
abstract class Observable {
    /*
      On a une liste [observers] d'observateurs, initialement vide, à laquelle
      viennent s'inscrire les observateurs via la méthode [addObserver].
     */
    private final ArrayList<Observer> observers;
    public Observable() {
        this.observers = new ArrayList<>();
    }
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /*
      Lorsque l'état de l'objet observé change, il est convenu d'appeler la
      méthode [notifyObservers] pour prévenir l'ensemble des observateurs
      enregistrés.
      On le fait ici concrètement en appelant la méthode [update] de chaque
      observateur.
     */
    public void notifyObservers() {
        for(Observer o : observers) {
            o.update();
        }
    }
}


/*
  Nous allons commencer à construire notre application, en voici la classe
  principale.
 */
public class Jeu {
    /*
      L'amorçage est fait en créant le modèle et la vue, par un simple appel
      à chaque constructeur.
      Ici, le modèle est créé indépendamment (il s'agit d'une partie autonome
      de l'application), et la vue prend le modèle comme paramètre (son
      objectif est de faire le lien entre modèle et utilisateur).
     */
    public static void main(String[] args) {
        /*
          Pour les besoins du jour on considère la ligne EvenQueue... comme une
          incantation qu'on pourra expliquer plus tard.
         */
        EventQueue.invokeLater(() -> {
            Ile ile = new Ile();
            CVue vue = new CVue(ile);
        });
    }
}

/*
  La vue : l'interface avec l'utilisateur.

  On définit une classe chapeau [CVue] qui crée la fenêtre principale de
  l'application et contient les deux parties principales de notre vue :
   - Une zone d'affichage où on voit l'ensemble des zones.
   - Une zone de commande avec un bouton pour passer à la génération suivante.
 */
class CVue {

    /* Construction d'une vue attachée à un modèle. */
    public CVue(Ile ile) {
        /* Définition de la fenêtre principale. */
        /*
      JFrame est une classe fournie pas Swing. Elle représente la fenêtre
      de l'application graphique.
     */
        JFrame frame = new JFrame();
        frame.setTitle("L'Ile Interdite");
        /*
         On précise un mode pour disposer les différents éléments à
         l'intérieur de la fenêtre. Quelques possibilités sont :
          - BorderLayout (défaut pour la classe JFrame) : chaque élément est
            disposé au centre ou le long d'un bord.
          - FlowLayout (défaut pour un JPanel) : les éléments sont disposés
            l'un à la suite de l'autre, dans l'ordre de leur ajout, les lignes
            se formant de gauche à droite et de haut en bas. Un élément peut
            passer à la ligne lorsque l'on redimensionne la fenêtre.
          - GridLayout : les éléments sont disposés l'un à la suite de
            l'autre sur une grille avec un nombre de lignes et un nombre de
            colonnes définis par le programmeur, dont toutes les cases ont la
            même dimension. Cette dimension est calculée en fonction du
            nombre de cases à placer et de la dimension du contenant.
         */
        frame.setLayout(new FlowLayout());

        /* Définition des deux vues et ajout à la fenêtre. */
        /*
      VueGrille et VueCommandes sont deux classes définies plus loin, pour
      nos deux parties de l'interface graphique.
     */
        VueGrille grille = new VueGrille(ile);
        VueCommandes commandes = new VueCommandes(ile);
        frame.add(grille);
        frame.add(commandes);
        /*
          Remarque : on peut passer à la méthode [add] des paramètres
          supplémentaires indiquant où placer l'élément. Par exemple, si on
          avait conservé la disposition par défaut [BorderLayout], on aurait
          pu écrire le code suivant pour placer la grille à gauche et les
          commandes à droite.
              frame.add(grille, BorderLayout.WEST);
              frame.add(commandes, BorderLayout.EAST);
         */

        /*
          Fin de la plomberie :
           - Ajustement de la taille de la fenêtre en fonction du contenu.
           - Indiquer qu'on quitte l'application si la fenêtre est fermée.
           - Préciser que la fenêtre doit bien apparaître à l'écran.
         */
        Keyboard keys = new Keyboard(ile);
        frame.addKeyListener(keys);
        frame.setFocusable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


