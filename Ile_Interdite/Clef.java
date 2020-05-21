package Ile_Interdite;

public class Clef {
    Element type;

    public Clef(Element elem){
        this.type = elem;
    }

    public Element getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.valueOf(this.type);
    }
}
