package Ile_Interdite;


/**
 * Définition d'une classe pour les zones.
 * Cette classe fait encore partie du modèle.
 */
class Zone {
    // Les coordonnées de la zone
    final int x, y;
    // On conserve un pointeur vers la classe principale du modèle
    private final Ile ile;
    // L'état actuel de la zone
    private Etat etat;
    // Vrai si la zone est une zone helico
    private boolean helico;
    // L'artefact correspondant à la zone
    private Element artefact = Element.AUCUN;

    /**
     * Constructeur
     */
    public Zone(Ile ile, int x, int y) {
        this.ile = ile;
        this.etat = Etat.NORMAL;
        this.x = x; this.y = y;
        this.helico = false;
    }

    /**
     * Fonction removeArtefact
     * Retire l'artefact de la zone
     */
    void removeArtefact(){
        this.artefact = Element.AUCUN;
    }

    /**
     * Fonction getArtefact
     * @return l'artefact de la zone
     */
    public Element getArtefact() {
        return artefact;
    }

    /**
     * Fonction setArtefacts
     * Permet d'attribuer un artefact à la zone
     * - Si elle ne possède pas déjà d'artefact
     * - Si elle n'est pas une zone helico
     * @param elt l'artefact
     * @return vrai si on a pu attribuer l'artefact à la zone, faux sinon
     */
    boolean setArtefacts(Element elt){
        if(this.artefact == Element.AUCUN && ! this.isHelico()){
            this.artefact = elt;
            return true;
        }
        return false;
    }

    /**
     * Fonction innonde
     * Innonde la zone si
     * - Elle est normal
     * Submerge la zone si
     * - Elle est innondée
     * @return vrai si l'état de la zone a changé
     */
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

    /**
     * Fonction setHelico
     * Attribue la zone comme une zone helico
     */
    void setHelico(){
        this.helico = true;
    }

    /**
     * Fonction isHelico
     * @return vrai si la zone est une zone helico, faux sinon
     */
    boolean isHelico(){
        return this.helico;
    }

    /**
     * Fonction asseche
     * Asseche une zone si elle est innondée
     * @return vrai si l'état de la zone à changé
     */
    boolean asseche(){
        if(this.etat == Etat.INNONDEE){
            this.etat = Etat.NORMAL;
            return true;
        }
        return false;
    }

    /**
     * Fonction estSubmerge
     * @return vrai si la zone est submergée, faux sinon
     */
    boolean estSubmerge(){
        return this.etat == Etat.SUBMERGEE;
    }

    /**
     * Fonction getEtat
     * @return l'état actuel de la zone
     */
    public Etat getEtat() {
        return this.etat;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "ile=" + ile +
                ", etat=" + etat +
                ", helico=" + helico +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
