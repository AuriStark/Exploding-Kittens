package common;

import exceptions.ZuWenigKartenException;
import server.Spielraum;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Wunsch extends Karte implements Serializable {
    @Override
    public void action(Spielraum sp) {
        try {
            int naechsterSpieler = (sp.getAmZug()+1)%sp.getAnzahlSpieler();
            while(sp.imSpiel[naechsterSpieler] == false)  //suche nächst möglichen Spieler
            {
                naechsterSpieler = naechsterSpieler+1 %sp.getAnzahlSpieler();
            }
            sp.chat.nachrichten.add(new String[]{"[Wunsch]", "Wähle eine Karte aus, " + sp.getSpielerNamen().get(naechsterSpieler)});
            Karte k=sp.spielerList.get(naechsterSpieler).zug(naechsterSpieler,true);
            sp.zustand.handKarten.get(sp.getAmZug()).addkarte(k);    //füge die Karte beim aktuellen Spieler hinzu
            sp.zustand.handKarten.get(naechsterSpieler).removeKarte(k);   //entferne die Karte beim Spieler dem sie entnommen wurde
            System.out.println(sp.chat.nachrichten);
            System.out.println(sp.getSpielerNamen().get(naechsterSpieler));
            System.out.println(naechsterSpieler);

        } catch (RemoteException | ZuWenigKartenException e) {
            e.printStackTrace();
        }
    }

}
