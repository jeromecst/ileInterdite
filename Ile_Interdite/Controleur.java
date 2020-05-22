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
       this.ile.joueurActuel%= Ile.nbJoueurs;
       this.ile.modeAssecher = false;
       this.ile.ajouteCleeAleatoireJoueurActuel();
       switch (this.ile.testend()){
           case WIN: this.ile.isWin = true; break;
           case LOSE: this.ile.isLost = true; break;
           case NONE: break;
       }
   }

    public void actionPerformed(ActionEvent actionEvent) {
       this.action();
    }
}

class ControleurAss extends Controleur{

    public ControleurAss(Ile ile) {
        super(ile);
    }

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
            System.out.println("Fin du tour");
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        this.action();
    }
}

class ControleurArtefact extends Controleur{

    public ControleurArtefact(Ile ile) {
        super(ile);
    }

    @Override
    public void action() {
        if(this.ile.compteur < Ile.MAXACTIONS && this.ile.getJoueurActuel().prendreArtefact()) {
            this.ile.compteur += 1;
        }
    }

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

    /*
    On sépare actionPerformed et action pour pouvoir récupérer cette méthode pour le clavier
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
            System.out.println("Fin du tour");
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.action();
    }
}
