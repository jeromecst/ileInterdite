package Ile_Interdite;

import java.util.ArrayList;
import java.util.Collections;

public class PaquetCartes<E> {
    private final ArrayList<E> paquet;
    private final ArrayList<E> defausse;
    private Ile ile;

    PaquetCartes(Ile ile){
        this.paquet = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.ile = ile;
    }

    public void add(E carte){
        this.paquet.add(carte);
    }

    public void melangerPaquet(){
        Collections.shuffle(this.paquet);
    }

    public void poserCarteDefausse(E carte){
        this.defausse.add(carte);
    }

    @Override
    public String toString() {
        String str = "";
        for(E carte: this.paquet){
            str += " " + carte.toString();
        }
        return str;
    }

    public E tirerCarte(){
        E carte = this.paquet.get(0);
        poserCarteDefausse(carte);
        this.paquet.remove(carte);
        if(this.paquet.isEmpty()){
            this.paquet.addAll(this.defausse);
            this.defausse.clear();
            this.melangerPaquet();
        }
        return carte;
    }

    public static void main(String[] args) {
        //tests
        PaquetCartes<Element> cartes = new PaquetCartes<>(new Ile());
        cartes.paquet.add(Element.FEU);
        cartes.paquet.add(Element.AIR);
        cartes.paquet.add(Element.FEU);
        cartes.paquet.add(Element.EAU);
        cartes.paquet.add(Element.TERRE);
        cartes.paquet.add(Element.FEU);
        cartes.paquet.add(Element.EAU);
        cartes.paquet.add(Element.TERRE);
        cartes.paquet.add(Element.FEU);
        cartes.paquet.add(Element.AUCUN);
        cartes.paquet.add(Element.MONTEEDESEAUX);
        System.out.println("Paquet de cartes : " + cartes.toString());
        cartes.melangerPaquet();
        System.out.println("Paquet mélangé : "  + cartes.toString());
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        cartes.tirerCarte();
        System.out.println("Paquet après tirages : " + cartes.toString());
        cartes.tirerCarte();
        System.out.println("Paquet avec defausse automatique : " + cartes.toString());

    }
}


