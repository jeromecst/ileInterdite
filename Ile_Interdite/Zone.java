package Ile_Interdite;


/**
 * Définition d'une classe pour les zones.
 * Cette classe fait encore partie du modèle.
 */
class Zone {
    /** On conserve un pointeur vers la classe principale du modèle. */
    final int x, y;
    private final Ile ile;
    private Etat etat;
    private Element element;
    private boolean helico;
    private double chanceClef = 0;
    private Element artefact = Element.AUCUN;

    public Zone(Ile ile, int x, int y, Element elem) {
        this.ile = ile;
        this.etat = Etat.NORMAL;
        this.x = x; this.y = y;
        this.helico = false;
        this.element = elem;
        this.setElem();
        this.initChance();
    }

    public Zone(Ile ile, int i, int j) {
        this(ile, i, j, Element.AUCUN);
    }

    void initChance() {
        if(this.getElement() != Element.AUCUN){
            this.chanceClef = this.ile.rd.nextDouble()/2.+ 0.5;
        }
    }

    void removeArtefact(){
        this.artefact = Element.AUCUN;
    }

    public Element getArtefact() {
        return artefact;
    }

    boolean setArtefacts(Element elt){
        if(this.artefact == Element.AUCUN && ! this.isHelico()){
            this.artefact = elt;
            return true;
        }
        return false;
    }

    /**
     * Permet d'attribuer un élément à une zone
     */
    private void setElem(){
        double pourcent = this.ile.rd.nextDouble();
        if(pourcent < Ile.SPECIAL){
            switch (this.ile.rd.nextInt(4)){
                case 0: this.setElem(Element.EAU); break;
                case 1: this.setElem(Element.AIR); break;
                case 2: this.setElem(Element.TERRE); break;
                case 3: this.setElem(Element.FEU); break;
            }
        }
    }

    public double getChanceClef(){
        return this.chanceClef;
    }


    void setElem(Element elem){
        this.element = elem;
    }

    Element getElement(){
        return this.element;
    }

    boolean innonde(){
        switch (this.etat){
            case NORMAL:
                this.etat = Etat.INNONDEE;
                return true;
            case INNONDEE: this.etat = Etat.SUBMERGEE;
            return true;
            default: return false;
        }
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

    @Override
    public String toString() {
        return "Zone{" +
                "ile=" + ile +
                ", etat=" + etat +
                ", element=" + element +
                ", helico=" + helico +
                ", x=" + x +
                ", y=" + y +
                ", chanceClef=" + chanceClef +
                '}';
    }
}
