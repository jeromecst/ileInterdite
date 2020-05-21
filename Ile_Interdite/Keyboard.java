package Ile_Interdite;

import javax.swing.*;
import java.awt.event.ActionEvent;
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
        ControleurDroite ctrlDroite = new ControleurDroite(ile);
        ControleurBas ctrlBas = new ControleurBas(ile);
        ControleurHaut ctrlHaut = new ControleurHaut(ile);
        ControleurGauche ctrlGauche = new ControleurGauche(ile);
        Controleur ctrl5 = new Controleur(ile);
        switch (keyEvent.getKeyCode()){
            case 38: new ControleurHaut(ile).actionPerformed(new ActionEvent());
        }

    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
