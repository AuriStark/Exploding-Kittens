package gui;

import javafx.beans.property.SimpleStringProperty;

public class BestenListe {

    private final SimpleStringProperty spielerName = new SimpleStringProperty("");
    private final SimpleStringProperty spielerPunkt = new SimpleStringProperty("0");

    public BestenListe(){
        this("","0");
    }

    public BestenListe(String spielerName, String spielerPunkt) {
        setSpielerName(spielerName);
        setSpielerPunkt(spielerPunkt);
    }

    public void setSpielerName(String sName) {
        spielerName.set(sName);
    }

    public String getSpielerName() {
        return spielerName.get();
    }

    public void setSpielerPunkt( String sPunkt) {
        spielerPunkt.set(sPunkt);
    }

    public String getspielerPunkt() {
        return spielerPunkt.get();
    }

    }


