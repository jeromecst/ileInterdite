package Ile_Interdite;

public class Joueur {
    private final Ile ile;
    int x;
    int y;

    public Joueur(Ile ile, int x, int y){
        this.x = x;
        this.y = y;
        this.ile = ile;
    }

    boolean canMove(int x, int y) {
        if (x < 0 || y < 0 || x >= Ile.LARGEUR  || y >= Ile.HAUTEUR || ile.getZone(x,y).isSubmergee()){
            return false;
        }
        this.x = x;
        this.y = y;
        return true;
    }

    boolean move(Direction direction){
        switch (direction){
            case BAS: return canMove(this.x, this.y-1 );
            case HAUT: return canMove(this.x, this.y +1 );
            case GAUCHE: return canMove(this.x - 1 , this.y);
            case DROITE: return canMove(this.x + 1, this.y);
        }
        return false;
    }
}
