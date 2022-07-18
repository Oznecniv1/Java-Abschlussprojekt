package project_kopfschmerzen_ms;

import java.sql.SQLException;

public class Startklasse {

    // die startklasse!
    
    public static void main(String[] args) throws SQLException {

        //GrafikRegistrierung wird in der datenbank eingetragen
        //GrafikRegistrierung gr = new GrafikRegistrierung();
        //gr.los();

        //Grafikuebersichtsseite wo alle einträge angezeigt werden
        //Grafikuebersichtsseite gue = new Grafikuebersichtsseite();
        //gue.los();
        
        //GrafikSpieleeintrag wird in der datenbank eingetragen
        //GrafikSpieleeintrag gse = new GrafikSpieleeintrag();
        //gse.los();
        
        //GrafikLogin
        GrafikLogin gl = new GrafikLogin();
        gl.los();
    }

}
