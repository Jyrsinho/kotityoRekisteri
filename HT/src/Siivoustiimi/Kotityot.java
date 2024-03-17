package Siivoustiimi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * pitää yllä varsinaista kotityö-rekisteriä, eli osaa lisätä ja poistaa kotityön.
 * lukee ja kirjoittaa kotityöt tiedostoon
 * osaa etsiä ja lajitella
 * @author jyrihuhtala
 * version 16.3.2024
 */
public class Kotityot implements Iterable<Kotityo> {

    private String tiedostonNimi = "";
    private final Collection <Kotityo> alkiot = new ArrayList<Kotityo>();

    /**
     * Luodaan Arraylist kotitöistä
     */
    public Kotityot() {

    }


    /**Lisaa uuden kotityon tietorakenteeseen.
     *
     * @param kotityo lisattava kotityo
     */
    public void lisaa (Kotityo kotityo) {
        alkiot.add(kotityo);
    }

    /**
     * Lukee jäsenistön tiedostosta.
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".har";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa jäsenistön tiedostoon.
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa siivoustiimin kotitoiden lukumaaran
     * @return harrastusten lukumaara
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * Iteraattori kaikkien harrastusten läpikäymiseen
     * @return harrastusiteraattori
     *
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     *
     *  Kotityot kotityot = new Kotityot();
     *  Kotityo imurointi1 = new Kotityo(2); kotityot.lisaa(imurointi1);
     *  Kotityo imurointi2 = new Kotityo(1); kotityot.lisaa(imurointi2);
     *  Kotityo imurointi3 = new Kotityo(2); kotityot.lisaa(imurointi3);
     *  Kotityo imurointi4 = new Kotityo(1); kotityot.lisaa(imurointi4);
     *  Kotityo lakaisu = new Kotityo(2); kotityot.lisaa(lakaisu);
     *
     *  Iterator<Kotityo> i2=kotityot.iterator();
     *  i2.next() === imurointi1;
     *  i2.next() === imurointi2;
     *  i2.next() === imurointi3;
     *  i2.next() === imurointi4;
     *  i2.next() === lakaisu;
     *  i2.next() === lakaisu;  #THROWS NoSuchElementException
     *
     * </pre>
     */

    @Override
    public Iterator<Kotityo> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki yhden jasenen kotityot
     * @param id jasenen tunnusnumero jota vastaan kotitoita haetaan.
     * @return tietorakenne, jossa viitteet löydettyihin kotitoihin.
     * @example <pre name="test">
     * #import java.util.*;
     * Kotityot kotityot = new Kotityot();
     * Kotityo imurointi1 = new Kotityo(2); kotityot.lisaa(imurointi1);
     * Kotityo imurointi2 = new Kotityo(1); kotityot.lisaa(imurointi2);
     * Kotityo imurointi3 = new Kotityo(2); kotityot.lisaa(imurointi3);
     * Kotityo imurointi4 = new Kotityo(2); kotityot.lisaa(imurointi4);
     * Kotityo imurointi5 = new Kotityo(2); kotityot.lisaa(imurointi5);
     * Kotityo imurointi6 = new Kotityo(3); kotityot.lisaa(imurointi6);
     *
     * List<Kotityo> loytyneet;
     * loytyneet = kotityot.annaKotityot(5);
     * loytyneet.size() === 0;
     * loytyneet = kotityot.annaKotityot(1);
     * loytyneet.size() === 1;
     * loytyneet = kotityot.annaKotityot(2);
     * loytyneet.size() === 4;
     * loytyneet.get(0) == imurointi1 === true;
     * loytyneet.get(1) == imurointi3 === true;
     * loytyneet = kotityot.annaKotityot(2);
     * loytyneet.size() === 4;
     * </pre>
     *
     */

    public List<Kotityo> annaKotityot (int id) {
        List<Kotityo> loydetyt = new ArrayList<Kotityo>();
        for (Kotityo kottyo : alkiot)
            if (kottyo.getVastuuhenkilonID() == id) loydetyt.add(kottyo);
        return loydetyt;
    }

    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotityot kotityot = new Kotityot();
        Kotityo imurointi = new Kotityo();
        imurointi.taytaKotityo(1);
        Kotityo imurointi2 = new Kotityo();
        imurointi2.taytaKotityo(1);
        Kotityo imurointi3 = new Kotityo();
        imurointi3.taytaKotityo(2);
        Kotityo imurointi4 = new Kotityo();
        imurointi4.taytaKotityo(2);

        kotityot.lisaa(imurointi);
        kotityot.lisaa(imurointi2);
        kotityot.lisaa(imurointi3);
        kotityot.lisaa(imurointi4);

        System.out.println("============= Kotityöt testi =================");

        List<Kotityo> kotityot2 = kotityot.annaKotityot(2);

        for (Kotityo kottyo : kotityot2) {
            System.out.print(kottyo.getVastuuhenkilonID() + " \n");
            kottyo.tulosta(System.out);
        }

    }





}