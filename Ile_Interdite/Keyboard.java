package Ile_Interdite;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard extends JPanel implements KeyListener {
    private final Ile ile;

    public Keyboard(Ile ile){
        this.ile = ile;
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    public void keyPressed(KeyEvent keyEvent) {
        ControleurDirection ctrlDroite = new ControleurDirection(ile, Direction.DROITE);
        ControleurDirection ctrlGauche = new ControleurDirection(ile, Direction.GAUCHE);
        ControleurDirection ctrlHaut = new ControleurDirection(ile, Direction.HAUT);
        ControleurDirection ctrlBas = new ControleurDirection(ile, Direction.BAS);
        Controleur ctrl5 = new Controleur(ile);
        switch (keyEvent.getKeyCode()){
            case 38: ctrlHaut.action(); break;
            case 37: ctrlGauche.action(); break;
            case 39: ctrlDroite.action(); break;
            case 40: ctrlBas.action(); break;
            case 32: ctrl5.action(); break;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
