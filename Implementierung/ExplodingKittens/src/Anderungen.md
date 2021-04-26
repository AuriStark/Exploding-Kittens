#Hier wird gespeichert, was abweichen von dem analyseModel und Diagramme ist.

1) Die Exception Klassen wurden unbennannt, sind aber noch klar erkennbar.

2) Umbenennung von einpaar Funktionnen damit, sie Klar zu erkennen sein können:
nach diesem Schema alteFumktion = newFunktion.

3) Klassen die Remote sein sollen implementieren nun ein Remoteinterface (z.B. Spieler/RemoteSpieler)

    #####HandKarten
* karte_add(Karte:karte) = addKarte(Karte karte)
    -> den Typ wurde von Void zu bool geändert, dmait wir add() und remove() 
    auf Listen verwenden können, die jeweils bool zurück geben.
* karte_remove() = removeKarte() // nimmt eine karte alls parameter
    -> den Typ wurde von Void zu bool geändert, dmait wir add() und remove() 
       auf Listen verwenden können, die jeweils bool zurück geben.

    /**
     * Am Anfang des spiels wird  eine Hand für jede Spieler erzeugt,
     * mit einer bestimmte liste von Karten.
     * @param listKartenInHand
     */
    public HandKarten(List<Karte> listKartenInHand){
        this.listKartenInhand = new  ArrayList<Karte>();
    }

    /**
     * Erzeugt die erste handKarte für jede Spieler, die genau 5 Karten enthählt.
     * @param listKarten die gewünschten karten, mit denen die HandKarte erzeugt wird.
     * @return gibt eine Liste von Karten zurück.
     */
    public List<Karte> initialiseHandKarte( List<Karte> listKarten){
        return firstHandKarten;
    }

#####Spielraum  
* starten() = spielStarten() \
* add(Spieler  spieler) = addSPieler(Spieler spieler)
* beenden() stoppt den Spielraum und das Spiel, sollte nur von der Verwaltung augerufen werden können
neues Attribut boolean[] beendet gibt an welche spieler ausgeschieden sind.
---
 #### Verwaltung
 * delete(name) = deleteSpielraum(raumname)
 * getSpielraum() = getSpielraume()
 * List<RemoteSpieler> listeSpieler wird zu Typ Map<String, RemoteSpieler>, damit mehrere von einem Rechner aus spielen können.
 
#### Datenbank
speichert auch alle Räume
z.B. getSpielraum gibt alle noch nich fertigen Räume zurück

#### Zustand
List<Kartenstapel> wird getrennt in Kartenstapel ziehStapel, playedStapel ablegeStapel
zusätzliche Attribute:
* String dran (Name das Spielers, der dran ist)
* number_of_nos (Die Anzahl der gerade gespielten Neins)
#### playedStapel
List<Karte>  neue klasse
#### Karte
action() -> action(Spielraum sp)
*typ um zu speichern welche Karte
#### RemoteSpieler / Spieler
isReady() ist Spieler bereit / hat GUI geladen? - Verwaltung wartet
    
#### Zustand (und Unterklassen)
Serialisierbar