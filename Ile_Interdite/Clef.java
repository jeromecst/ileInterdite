package Ile_Interdite;

public class Clef {
    private final Element type;

    /**
     * Constructeur
     * @param elem le type de clef
     */
    public Clef(Element elem){
        this.type = elem;
    }

    /**
     * Fonction getType
     * @return renvoie le type de la clef
     */
    public Element getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.valueOf(this.type);
    }
}
