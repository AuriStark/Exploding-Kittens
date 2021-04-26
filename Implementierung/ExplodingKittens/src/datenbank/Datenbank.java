package datenbank;

import exceptions.DatenBankFehlerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SEP Gruppe-09
 */
public class Datenbank {

    private static Connection con;

    public Datenbank(String dbname, String dbhost, String dbnutzer, String dbpwd) throws DatenBankFehlerException {
        String url = String.format("jdbc:postgresql://%s/%s", dbhost, dbname);
        try {
            this.con = DriverManager.getConnection(url, dbnutzer, dbpwd);
            System.out.println("Connected to The Database");

            // Init Tables if first call to DB
            Statement stmt = con.createStatement();
            stmt.execute("create table if not exists spieler (name varchar primary key, pass varchar, gewonnen integer)");
            stmt.execute("create table if not exists spielraum (name varchar primary key, aktiv boolean,spieler integer,sieger varchar)");
            stmt.close();
        }
        catch (SQLException e){
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
            throw new DatenBankFehlerException();
        }
    }

    // Allow Stubs to easily extend from this class

    public Datenbank(){
        this.con = null;
    }

    /**
     * Gibt die Liste aller Spieler mit ihrer Anzahl an Siegen aus
     *
     * @return Gibt die Bestenliste zurück
     * @throws DatenBankFehlerException
     */

    public List<String[]> getSiege() throws DatenBankFehlerException { //r entfernt

        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM spieler order by gewonnen desc limit 30");
            List<String[]> out = new ArrayList<>();
            while (rset.next()) {

                out.add(new String[]{(String) rset.getString("name"), rset.getString("gewonnen")});
            }
            stmt.close();

            return out;
        } catch (SQLException e) {
            throw new DatenBankFehlerException();
        }
    }

    /**
     * Die Kontodaten vom Spieler werden in der Datenbank angelegt
     *
     * @param name Name vom Kontoinhaber
     * @param pwd  Passwort
     */
    public void SpeicherNutzerDaten(String name, String pwd) throws DatenBankFehlerException {

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO spieler VALUES(?,?,0)");
            stmt.setString(1, name);
            stmt.setString(2, pwd);
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new DatenBankFehlerException();
        }
    }
    
    public void SpeicherSpielraum(String name, int anzahl) {

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO spielraum VALUES(?,true,?,Null)");
            stmt.setString(1, name);
            stmt.setInt(2, anzahl);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Datenbank.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

    /**
     * Die Kontodaten vom Spieler werden von der Datenbank ausgelesen
     *
     * @return Die Daten des Spielers werden zurückgegeben
     * @throws DatenBankFehlerException
     */

    public List<String[]> leseNutzerDaten() throws DatenBankFehlerException {

        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM spieler");
            List<String[]> out = new ArrayList<>();
            while (rset.next()) {

                out.add(new String[]{(String) rset.getString("name"), rset.getString("pass")});
            }
            stmt.close();

            return out;

        } catch (SQLException e) {
            throw new DatenBankFehlerException();
        }
    }


    /**
     * Die Daten vom Spieler werden aus der Datenbank entfernt
     *
     * @param name Name vom Spieler
     * @param pwd  Passwort
     */
    public void loescheNutzerDaten(String name, String pwd) throws DatenBankFehlerException {

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM spieler where name=? and pass=?");
            stmt.setString(1, name);
            stmt.setString(2, pwd);
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new DatenBankFehlerException();
        }
    }

    /**
     * Für den Spieler wird die Anzahl der Siege erhöht
     *
     * @param name Name vom Spieler
     * @throws DatenBankFehlerException
     */
    public void addSieg(String name) throws DatenBankFehlerException {
        try {
            PreparedStatement stmt = con.prepareStatement("update spieler set gewonnen=gewonnen+1 where name=?");
            stmt.setString(1, name);
            stmt.execute();
            stmt.close();
        }
        catch(SQLException e){
            System.out.println(e);
            throw new DatenBankFehlerException();
        }
    }

    /**
     * Das Passwort von dem Spieler wird geändert
     * @param altesPass altes Passwort von dem Spieler
     * @param neuesPass neues Passwort von dem Spieler
     * @throws DatenBankFehlerException
     */
    public static void aenderenutzerDaten(String name ,String altesPass, String neuesPass) throws DatenBankFehlerException {

        try{
            PreparedStatement stmt = con.prepareStatement("UPDATE spieler SET pass = ? WHERE name = ? and pass = ? ");
            stmt.setString(1, neuesPass);
            stmt.setString(2, name);
            stmt.setString(3, altesPass);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e);
            throw new DatenBankFehlerException();
        }
    }

    /**
     * Lese alle Spielräume, die noch aktiv sind
     * @return
     * @throws DatenBankFehlerException
     */
    public List<String[]> getSpielraum() throws DatenBankFehlerException {
        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM spielraum where aktiv = true");
            List<String[]> out = new ArrayList<>();
            while (rset.next()) {
                out.add(new String[]{(String) rset.getString("name"), String.valueOf(rset.getInt("spieler"))});
            }
            stmt.close();

            return out;
        } catch (SQLException e) {
            throw new DatenBankFehlerException();
        }
    }
}
