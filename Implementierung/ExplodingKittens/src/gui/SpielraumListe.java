package gui;

public class SpielraumListe {

    String name;
    int anzahlSpieler;
    
    public SpielraumListe (String name,int anzahlSpieler){
        super();
        this.name=name;
        this.anzahlSpieler=anzahlSpieler;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        this.name=n;
    }
    
    public int getAnzhal(){
        return anzahlSpieler;
    }
    
    public void setAnzhal(int a){
        this.anzahlSpieler = a;
    }
    
}
