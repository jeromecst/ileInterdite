package Ile_Interdite;

import javax.swing.*;

/*
  Une classe pour représenter la zone contenant le bouton.

  Cette zone n'aura pas à être mise à jour et ne sera donc pas un observateur.
  En revanche, comme la zone précédente, celle-ci est un panneau [JPanel].
 */
class VueCommandes extends JPanel {
    /*
      Pour que le bouton puisse transmettre ses ordres, on garde une
      référence au modèle.
     */
    private Ile ile;

    /* Constructeur. */
    public VueCommandes(Ile ile) {
        this.ile = ile;
        /*
          On crée un nouveau bouton, de classe [JButton], en précisant le
          texte qui doit l'étiqueter.
          Puis on ajoute ce bouton au panneau [this].
         */
        JButton boutonDroite = new JButton(">");
        JButton boutonGauche = new JButton("<");
        JButton boutonHaut = new JButton("^");
        JButton boutonBas = new JButton("v");
        this.add(boutonGauche);
        this.add(boutonDroite);
        this.add(boutonHaut);
        this.add(boutonBas);
        /*
          Le bouton, lorsqu'il est cliqué par l'utilisateur, produit un
          événement, de classe [ActionEvent].

          On a ici une variante du schéma observateur/observé : un objet
          implémentant une interface [ActionListener] va s'inscrire pour
          "écouter" les événements produits par le bouton, et recevoir
          automatiquements des notifications.
          D'autres variantes d'auditeurs pour des événements particuliers :
          [MouseListener], [KeyboardListener], [WindowListener].

          Cet observateur va enrichir notre schéma Modèle-Vue d'une couche
          intermédiaire Contrôleur, dont l'objectif est de récupérer les
          événements produits par la vue et de les traduire en instructions
          pour le modèle.
          Cette strate intermédiaire est potentiellement riche, et peut
          notamment traduire les mêmes événements de différentes façons en
          fonction d'un état de l'application.
          Ici nous avons un seul bouton réalisant une seule action, notre
          contrôleur sera donc particulièrement simple. Cela nécessite
          néanmoins la création d'une classe dédiée.
         */
        ControleurHaut ctrl = new ControleurHaut(ile);
        /* Enregistrement du contrôleur comme auditeur du bouton. */
        boutonDroite.addActionListener(ctrl);
    }
}
