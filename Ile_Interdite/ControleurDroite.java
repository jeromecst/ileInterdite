package Ile_Interdite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Controleur implements ActionListener{
   Ile ile;
   public Controleur(Ile ile){
       this.ile = ile;
   }

    public void actionPerformed(ActionEvent actionEvent) {
        this.ile.fin_de_tour();
        this.ile.compteur = 0;
        this.ile.joueurActuel+=1;
        this.ile.joueurActuel%=this.ile.nbJoueurs;
    }
}
class ControleurDroite implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    Ile ile;
    public ControleurDroite(Ile ile) {
        this.ile = ile;
    }

    /**
     * Action effectuée à réception d'un événement : appeler la
     * méthode [fin_de_tour] du modèle.
     */
    public void actionPerformed(ActionEvent e) {
        if(this.ile.compteur < 3 && ile.getJoueurActuel().move(Direction.DROITE)) {
            this.ile.compteur += 1;
        }
    }
}

class ControleurBas implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    Ile ile;
    public ControleurBas(Ile ile) { this.ile = ile; }

    /**
     * Action effectuée à réception d'un événement : appeler la
     * méthode [fin_de_tour] du modèle.
     */
    public void actionPerformed(ActionEvent e) {
        if(this.ile.compteur < 3 && ile.getJoueurActuel().move(Direction.BAS)) {
            this.ile.compteur += 1;
        }
    }
}

class ControleurHaut implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    Ile ile;
    public ControleurHaut(Ile ile) { this.ile = ile; }

    /**
     * Action effectuée à réception d'un événement : appeler la
     * méthode [fin_de_tour] du modèle.
     */
    public void actionPerformed(ActionEvent e) {
        if(this.ile.compteur < 3 && ile.getJoueurActuel().move(Direction.HAUT)) {
            this.ile.compteur += 1;
        }
    }
}

class ControleurGauche implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    Ile ile;
    public ControleurGauche(Ile ile) { this.ile = ile; }

    /**
     * Action effectuée à réception d'un événement : appeler la
     * méthode [fin_de_tour] du modèle.
     */
    public void actionPerformed(ActionEvent e) {
        if(this.ile.compteur < 3 && ile.getJoueurActuel().move(Direction.GAUCHE)) {
            this.ile.compteur += 1;
        }
    }
}
