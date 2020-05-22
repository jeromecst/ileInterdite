package Ile_Interdite;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    // Liste des clée que le joueur possède
    private final ArrayList<Clef> clefs;
    // Liste des artefacts que le joueur possède
    private final ArrayList<Element> artefacts;
    // On rattache le joueur à l'Ile
    private final Ile ile;
    // Les coordonnées du joueur
    int x;
    int y;
    // Le nom du joueur
    private final String name;
    // Le numéro du joueur
    private final int num;

    /**
     * Constructeur
     */
    public Joueur(Ile ile, int x, int y, String s, int n){
        this.x = x;
        this.y = y;
        this.ile = ile;
        this.name = s;
        this.clefs = new ArrayList<>();
        this.artefacts = new ArrayList<>();
        this.num = n;
    }

    /**
     * Fonction prendreArtefact qui ajoute l'artefact de la zone au joueur
     * @return vrai si l'artefact a été ajouté au joueur, faux sinon
     */
    boolean prendreArtefact(){
        if(artefactMatchKey()){
            this.artefacts.add(this.getZone().getArtefact());
            this.getZone().removeArtefact();
            return true;
        }
        return false;
    }

    /**
     * Fonction artefactMatchKey
     * @return vrai si un type de clef du joueur correspond au type d'artefact de la zone du joueur
     */
    private boolean artefactMatchKey(){
        for (Clef c : this.clefs){
            if(this.getZone().getArtefact() == c.getType()){
                return true;
            }
        }
        return false;
    }

    /**
     * Fonction getNum
     * @return le numéro du joueur
     */
    public int getNum() {
        return num;
    }

    /**
     * Fonction getAllKeys
     * @return la liste des clefs du joueur
     */
    public List<Clef> getAllKeys(){
        return this.clefs;
    }

    /**
     * Fonction getAllArtefacts
     * @return la liste des artefacts que le joueur possède
     */
    public ArrayList<Element> getAllArtefacts(){
        return  this.artefacts;
    }

    /**
     * Fonction hasKey
     * @return vrai si le joueur a une ou plusieurs clefs, faux sinon
     */
    boolean hasKey(){
        return this.clefs.size() > 0;
    }

    /**
     * Fonction hasArtefact
     * @return vrai si le joueur a un ou plusieurs artefacts, faux sinon
     */
    boolean hasArtefact(){
        return this.artefacts.size() > 0;
    }

    /**
     * Fonction ajouteClefs
     * @param clef la clée a ajouter au joueur
     */
    void ajouteClefs(Clef clef){
        this.clefs.add(clef);
    }

    /**
     * Fonction canMove
     * Teste si le joueur peut se déplacer aux coordonnées données
     * Si il peut se déplacer, on le déplace
     * Il peut se déplacer si:
     * - Les coordonnées sont dans la grille
     * - Les coordonnées ne concernent pas une zone submergée
     * @param x la position x
     * @param y la position y
     * @return vrai si il peut se déplacer, faux sinon
     */
    boolean canMove(int x, int y) {
        if (x < 0 || y < 0 || x >= Ile.LARGEUR  || y >= Ile.HAUTEUR || ile.getZone(x,y).estSubmerge()){
            return false;
        }
        this.x = x;
        this.y = y;
        return true;
    }

    /**
     * Fonction getZone
     * @return la zone où le joueur se situe
     */
    public Zone getZone(){
        return this.ile.getZone(this.x, this.y);
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Fonction move
     * @param direction la direction par rapport à la position du joueur
     * @return vrai si il s'est délpacé, faux sinon
     */
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
