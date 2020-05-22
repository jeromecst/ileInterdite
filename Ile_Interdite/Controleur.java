package Ile_Interdite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Controleur implements ActionListener{
   Ile ile;
   public Controleur(Ile ile){
       this.ile = ile;
   }

    /**
     * Fonction qui réalise une liste d'action à la fin d'un tour, parmis lesquels
     * - tirage d'une carte de fin de tour
     * - fin de tour
     * - changement de joueur
     * - tester si la partie est finie ou pas
     */
   public void action(){
       System.out.print("\nJoueur " + this.ile.getJoueurActuel().getNum() + " ");
       this.ile.tirageCarteFinDeTour();
       this.ile.fin_de_tour();
       this.ile.compteur = 0;
       this.ile.joueurActuel+=1;
       this.ile.joueurActuel%= Ile.nbJoueurs;
       this.ile.modeAssecher = false;
       switch (this.ile.testEnd()){
           case WIN: this.ile.isWin = true; break;
           case LOSE: this.ile.isLost = true; break;
           case NONE: break;
       }
   }

    /**
     * Réagis au bouton
     * @param actionEvent le bouton activé
     */
    public void actionPerformed(ActionEvent actionEvent) {
       this.action();
    }
}

class ControleurAss extends Controleur{

    public ControleurAss(Ile ile) {
        super(ile);
    }

    /**
     * Fonction qui permet au joueur actuel d'assécher une zone
     * - on asséche toujours la zone du joueur avant de chercher les zones adjacentes
     * - sinon on met le mode modeAssecher pour choisir la zone avec les autres boutons
     */
    @Override
    public void action() {
        if(this.ile.compteur < Ile.MAXACTIONS) {
            if (this.ile.getJoueurActuel().getZone().getEtat() == Etat.INNONDEE) {
                this.ile.getJoueurActuel().getZone().asseche();
                this.ile.compteur += 1;
            } else if (this.ile.zoneAdjacenteInnondee()) {
                this.ile.modeAssecher = true;
            } else {
                System.out.println("Il n'y a pas de zones à assécher !");
            }
        }
        else {
            System.out.print("Fin du tour ");
        }
    }

    /**
     * Réagis au bouton
     * @param actionEvent le bouton activé
     */
    public void actionPerformed(ActionEvent actionEvent) {
        this.action();
    }
}

class ControleurArtefact extends Controleur{

    public ControleurArtefact(Ile ile) {
        super(ile);
    }

    /**
     * Fonction qui prend l'artefact si le joueur actuel
     * - est sur une zone artefact
     * - possède le bon type de cle
     * - a assez d'actions restantes
     */
    @Override
    public void action() {
        if(this.ile.compteur < Ile.MAXACTIONS && this.ile.getJoueurActuel().prendreArtefact()) {
            this.ile.compteur += 1;
        }
    }

    /**
     * Réagis au bouton
     * @param actionEvent le bouton activé
     */
    public void actionPerformed(ActionEvent actionEvent) {
        this.action();
    }
}

class ControleurDirection implements ActionListener {
    Ile ile;
    Direction direction;
    public ControleurDirection(Ile ile, Direction direction) {
        this.ile = ile;
        this.direction = direction;
    }

    /**
     * Fonction qui permet de déplacer le joueur actuel ou de séléctionner la zone à assécher
     * - si il a assez d'action
     * - si il peut se déplacer / assécher
     */
    public void action(){
        if(this.ile.compteur < Ile.MAXACTIONS && this.ile.modeAssecher && this.ile.asseche(this.direction)){
            this.ile.compteur += 1;
            this.ile.modeAssecher = false;
        }
        else if(this.ile.compteur < Ile.MAXACTIONS && ile.getJoueurActuel().move(this.direction)) {
                this.ile.compteur += 1;
        }
        else {
            System.out.print("Fin du tour ");
        }
    }

    /**
     * Réagis au bouton
     * @param actionEvent le bouton activé
     */
    public void actionPerformed(ActionEvent e) {
        this.action();
    }
}
