package Ile_Interdite;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private final ArrayList<Clef> clefs;
    private final ArrayList<Element> artefacts;
    private final Ile ile;
    int x;
    int y;
    private final String name;
    private final int num;

    public Joueur(Ile ile, int x, int y, String s, int n){
        this.x = x;
        this.y = y;
        this.ile = ile;
        this.name = s;
        this.clefs = new ArrayList<>();
        this.artefacts = new ArrayList<>();
        this.num = n;
    }

    boolean prendreArtefact(){
        if(artefactMatchKey()){
            System.out.println("Artefact Match key !!");
            this.artefacts.add(this.getZone().getArtefact());
            this.getZone().removeArtefact();
            return true;
        }
        return false;
    }

    private boolean artefactMatchKey(){
        for (Clef c : this.clefs){
            if(this.getZone().getArtefact() == c.getType()){
                return true;
            }
        }
        return false;
    }

    public int getNum() {
        return num;
    }

    public List<Clef> getAllKeys(){
        return this.clefs;
    }

    boolean hasKey(Clef clef){
        for(Clef c: this.clefs){
            if (c.getType() == clef.getType())
                return true;
        }
        return false;
    }
    boolean hasKey(){
        return this.clefs.size() > 0;
    }

    void ajouteClefs(Clef clef){
        this.clefs.add(clef);
    }

    boolean canMove(int x, int y) {
        if (x < 0 || y < 0 || x >= Ile.LARGEUR  || y >= Ile.HAUTEUR || ile.getZone(x,y).estSubmerge()){
            return false;
        }
        this.x = x;
        this.y = y;
        return true;
    }

    public Zone getZone(){
        return this.ile.getZone(this.x, this.y);
    }

    @Override
    public String toString() {
        return this.name;
    }

    boolean move(Direction direction){
        switch (direction){
            case BAS: return canMove(this.x, this.y+1 );
            case HAUT: return canMove(this.x, this.y -1 );
            case GAUCHE: return canMove(this.x - 1 , this.y);
            case DROITE: return canMove(this.x + 1, this.y);
        }
        return false;
    }
}
