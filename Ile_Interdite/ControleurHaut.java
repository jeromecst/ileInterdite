package Ile_Interdite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe pour notre contrôleur rudimentaire.
 *
 * Le contrôleur implémente l'interface [ActionListener] qui demande
 * uniquement de fournir une méthode [actionPerformed] indiquant la
 * réponse du contrôleur à la réception d'un événement.
 */
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
        ile.joueur.move(Direction.DROITE);
        ile.fin_de_tour();
    }
}

