package Ile_Interdite;


/**
 * Définition d'une classe pour les zones.
 * Cette classe fait encore partie du modèle.
 */
class Zone {
    /** On conserve un pointeur vers la classe principale du modèle. */
    private Ile ile;
    private Etat etat;
    private Element element;
    private boolean helico;
    private final int x, y;

    public Zone(Ile ile, int x, int y, Element elem) {
        this.ile = ile;
        this.etat = Etat.NORMAL;
        this.x = x; this.y = y;
        this.helico = false;
        this.element = elem;
    }

    public Zone(Ile ile, int i, int j) {
        this(ile, i, j, Element.AUCUN);
    }

    void setElem(Element elem){
        this.element = elem;
    }

    Element getElement(){
        return this.element;
    }

    void innonde(){
        switch (this.etat){
            case NORMAL:
                this.etat = Etat.INNONDEE; break;
            case INNONDEE:
            case SUBMERGEE:
                this.etat = Etat.SUBMERGEE; break;
        }
    }

    void printCoord(){
        System.out.println("x: " + this.x + ", y: " + this.y);
    }

    void setHelico(){
        this.helico = true;
    }

    boolean isHelico(){
        return this.helico;
    }

    boolean asseche(){
        if(this.etat == Etat.INNONDEE){
            this.etat = Etat.NORMAL;
            return true;
        }
        return false;
    }

    boolean estSubmerge(){
        return this.etat == Etat.SUBMERGEE;
    }

    public Etat getEtat() {
        return this.etat;
    }
}
