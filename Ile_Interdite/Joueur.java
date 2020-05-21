package Ile_Interdite;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private ArrayList<Clef> clefs;
    private final Ile ile;
    int x;
    int y;
    private String name;
    private int num;

    public Joueur(Ile ile, int x, int y, String s, int n){
        this.x = x;
        this.y = y;
        this.ile = ile;
        this.name = s;
        this.clefs = new ArrayList<>();
        this.num = n;
    }

    public int getNum() {
        return num;
    }

    public List<Clef> getAllKeys(){
        return this.clefs;
    }

    boolean hasKey(Clef clef){
        for(Clef c: this.clefs){
            if (c.type == clef.type)
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
