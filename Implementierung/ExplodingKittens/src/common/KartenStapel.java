package common;

import exceptions.KartenStapelZuKleinException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 * @author SEP Gruppe-09
 */
public class KartenStapel implements Serializable {

    public List<Karte> listKarten;

    /**
     * Stellt den Kartenstapel und dessen Funktionen dar, wie zum Beispiel mischen.
     */
    public KartenStapel(){
        this.listKarten = new ArrayList<Karte>();
        initializeStapel();
    }

    public KartenStapel(List<Karte> listKarten){
        this.listKarten = listKarten;
    }

    /**
     * Gibt eine Karte zurück, an eine bestimmte Stelle in einer Liste.
     * @param index  Die Stelle an der die Karte sich befindet. (0 oben, 1 - eins unter oben)
     * @return Die Karte an der Stelle index in der Liste listkarten.
     */
    public Karte getkarte(int index) throws KartenStapelZuKleinException{
        return listKarten.get(listKarten.size() - 1 - index);
    }

    /**
     * Gibt eine Liste der gespielten Karten, die  Im Stapel enthalten sind.
     * @return Liste der Karten im Stapel.
     */
    public List<Karte> getKarten() {
        return new ArrayList<>(listKarten);
    }

    /**
     * Gibt die aktuelle Anzahl der Karten im Kartenstapel an
     * @return Anzahl der Karten
     */
    public int getAnzahlKartenImStapel(){
        return this.listKarten.size();
    }

    /**
     * setzt eine neue Liste von karten in dem Stapel.
     * @param listKarten  List der neuen karten Im Stapel
     */
    public void setKarten(List<Karte> listKarten) {
        this.listKarten = listKarten;
    }

    /**
     * Der Kartenstapel wird gemischt
     */
    public void mischen(){
        Collections.shuffle(listKarten);
    }

    /**
     * Der Spieler sieht die 3 obersten Karten aus dem Kartenstapel
     * @return Liste von den 3 obersten Karten (oder weniger falls nur 1 oder 2 übrig)
     */
    public List<Karte> sieheZukunft(){
        List<Karte> dreiKarten = new ArrayList<>();
        int i = listKarten.size() - 1;
        while(i >= 0 && dreiKarten.size() < 3){
            dreiKarten.add(listKarten.get(i));
            i -= 1;
        }
        // Null einfügen, falls weniger als drei Karten
        while(dreiKarten.size() < 3){
            dreiKarten.add(null);
        }
        return dreiKarten;
    }


    /**
     * Platziert die Karte an eine gewählte Stelle im Stapel, wenn der Kartenstapel
     * kleiner ist als die Stelle an der die Karte eingefügt werden soll wird die Exception KartenstapelZuKlein geworfen
     * @param stelle Die Position an die die Karte gelegt werden soll, (0 - oben, 1 - eins unter oben)
     * @param karte Die karte die gelegt werden soll
     */
    public boolean legen(int stelle, Karte karte) {
        try{
            listKarten.add(max(0, listKarten.size() - stelle), karte);
            return true;

        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    /**
     * Platziert die Karte an eine gewählte Stelle im Stapel, wenn der Kartenstapel
     * kleiner ist als die Stelle an der die Karte eingefügt werden soll wird die Exception KartenstapelZuKlein geworfen
     * @param karte Die karte die gelegt werden soll
     */
    public boolean einfachLegen(Karte karte){
        try{
            listKarten.add(karte);
            return true;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    /**
     * Gibt den letzen Index von dem Stapel,
     * damit wird auf die letze gespielte Karte zugreifen können.
     * @return Letzter Index
     */
    public int getLastIndex(){
        return listKarten.size() - 1;
    }

    /**
     * Ziehen der obersten Karte vom Kartenstapel
     * @return Gibt die oberste Karte zurück
     */
    public Karte ziehen() throws KartenStapelZuKleinException {
        if (listKarten.size() != 0){
            Karte zuZiehen = listKarten.get(getLastIndex());
            listKarten.remove(zuZiehen);
            return zuZiehen;
        } else{
            throw new KartenStapelZuKleinException("Stapel leer");
        }
    }

    /**
     * Löscht ein Karte von einer Liste
     * @param karte, die aus dem Stapel gelösscht werden muss.
     */
    private boolean removeKarte(Karte karte) {
        return listKarten.remove(karte);
    }

    /**
     * erzeugt einen Stapel mit jeweils die Anzahl an  Karten pro Kartentyp.
     * Insgesamt 56 karten, 46 am Anfang.
     * Die Anzahl an Exploding Kittens Karten und Defuse ist dann abhängig von der Anzahl an Spieler.
     * : die funktion initializeExplodeEnscharfung(int anzahlSpieler) benutzen.
     *
     * 4 Exploding kittens
     * 6 Entschaerfung
     * 5 No
     * 4 Angriff
     * 4*5 Katzen-Karten ( 4 von den 5 verschiedenen Sorten er Katzen Karten)
     * 4 Hops!
     * 4 Wunsch
     * 4 Mischen
     * 5 Blick In Die Zukunft.
     */

    public void initializeStapel(){
        if (getAnzahlKartenImStapel() == 0){
            /*
            Die Anzahl an Exploding Kittens Karten und Defuse ist dann abhängig von der Anzahl an Spieler:
             die funktion InitializeExplodeEntscharfung(anzahlSpieler) benutzen
             */
            initializeAngriff();
            initializeBlickInDieZukunft();
            initializeHops();
            initializeMischen();
            initializeNo();
            initializeWunsch();
            initializekatzenKarte();
        }
        mischen();
    }

    private void initializeWunsch() {
        for (int i = 0; i<4; i++){
            Karte k = new Wunsch();
            k.setTyp(i%2);
            listKarten.add(k);
        }
    }

    private void initializekatzenKarte() {
        for (int i = 0; i<20; i++){
            Karte k = new KatzenKarte();
            k.setTyp(i%5);
            listKarten.add(k);
        }
    }

    private void initializeNo() {
        for (int i = 0; i<5; i++){
            Karte k = new No();
            k.setTyp(i%2);
            listKarten.add(k);
        }
    }

    private void initializeMischen() {
        for (int i = 0; i<4; i++){
            Karte k = new Mischen();
            k.setTyp(i%3);
            listKarten.add(k);
        }
    }

    private void initializeHops() {
        for (int i = 0; i<4; i++){
            Karte k = new Hops();
            k.setTyp(i%2);
            listKarten.add(k);
        }
    }

    private void initializeBlickInDieZukunft() {
        for (int i = 0; i<5; i++){
            Karte k = new BlickInDIeZukunft();
            k.setTyp(i%3);
            listKarten.add(k);
        }
    }

    private void initializeAngriff() {
        for (int i = 0; i<4; i++){
            Karte k = new Angriff();
            k.setTyp(i%4);
            listKarten.add(k);
        }
    }

    public  void initializeExplodingKittens(int anzahlSpieler){
        for(int i = 0; i < anzahlSpieler - 1 ; i++){
            Karte k = new ExplodingKitten();
            k.setTyp(i%4);
            listKarten.add(k);
        }
    }

    public void intializeEntscharfung(int anzahlSpieler){
        for (int i = 0;  i< 6 - anzahlSpieler; i++){
            Karte k = new Entscharfung();
            k.setTyp(i%6);
            listKarten.add(k);
        }
    }

    /**
     * Fügt Entschärfungen und Exploding Kittens zum Stapel hinzu und mischt diesen.
     * Hängt von der Anzahl der Spieler ab.
     */
    public void starten(int anzahlSpieler){
        initializeExplodingKittens(anzahlSpieler);
        intializeEntscharfung(anzahlSpieler);
        mischen();
    }
}
