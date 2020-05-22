package Ile_Interdite;

import java.util.ArrayList;
import java.util.Collections;

public class PaquetCartes<E> {
    // Le paquet principal
    private final ArrayList<E> paquet;
    // Le paquet contenant les cartes défaussées
    private final ArrayList<E> defausse;

    /**
     * Constructeur
     */
    PaquetCartes(){
        this.paquet = new ArrayList<>();
        this.defausse = new ArrayList<>();
    }

    /**
     * Ajoute une carte au paquet
     * @param carte la carte à ajouter
     */
    public void add(E carte){
        this.paquet.add(carte);
    }

    /**
     * Mélange le paquet de cartes
     */
    public void melangerPaquet(){
        Collections.shuffle(this.paquet);
    }

    /**
     * Ajoute une carte à la defausse
     * @param carte la carte à ajouter
     */
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

    /**
     * Tire la première carte du paquet
     * Si la carte est la dernière du paquet:
     *      ajoute la carte à la defausse
     *      ajoute toutes les cartes de la defausse au paquet
     *      mélange le paquet
     * Sinon
     *      ajoute la carte à la defausse
     * @return la carte tirée
     */
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

    /**
     * Tests
     */
    public static void main(String[] args) {
        PaquetCartes<Element> cartes = new PaquetCartes<>();
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


