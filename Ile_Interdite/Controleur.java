package Ile_Interdite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Controleur implements ActionListener{
   Ile ile;
   public Controleur(Ile ile){
       this.ile = ile;
   }

   public void action(){
       this.ile.fin_de_tour();
       this.ile.compteur = 0;
       this.ile.joueurActuel+=1;
       this.ile.joueurActuel%=this.ile.nbJoueurs;
   }

    public void actionPerformed(ActionEvent actionEvent) {
       this.action();
    }
}
class ControleurDirection implements ActionListener {
    /**
     * On garde un pointeur vers le modèle, car le contrôleur doit
     * provoquer un appel de méthode du modèle.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du modèle est inutile. On pourrait se contenter de
     * faire directement référence au modèle enregistré pour la classe
     * englobante [VueCommandes].
     */
    Ile ile;
    Direction direction;
    public ControleurDirection(Ile ile, Direction direction) {
        this.ile = ile;
        this.direction = direction;
    }

    public void action(){
        if(this.ile.compteur < 3 && ile.getJoueurActuel().move(this.direction)) {
            this.ile.compteur += 1;
        }
    }

    /**
     * Action effectuée à réception d'un événement : appeler la
     * méthode [fin_de_tour] du modèle.
     */
    public void actionPerformed(ActionEvent e) {
        this.action();
    }
}
