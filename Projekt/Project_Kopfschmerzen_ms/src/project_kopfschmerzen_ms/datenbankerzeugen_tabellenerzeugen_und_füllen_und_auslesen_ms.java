package project_kopfschmerzen_ms;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mario am samstag den 2.8.2021 gemacht!
 */
public class datenbankerzeugen_tabellenerzeugen_und_füllen_und_auslesen_ms {

    // =========================================================================
    private static void datenbankErzeugenSpiele() {
        String sql = "CREATE DATABASE IF NOT EXISTS "
                + Datenbankzugriff.DATENBANKNAME
                + " COLLATE utf8_german2_ci;";

        Datenbankzugriff.getInstance().execute(sql);

        // die neu erzeugte Datenbank nutzen
        sql = "USE " + Datenbankzugriff.DATENBANKNAME + ";";
        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenBeschreibungWirdReingeschrieben() {
        String sql = "CREATE TABLE IF NOT EXISTS Beschreibung ("
                + "Beschreibung_ID INTEGER AUTO_INCREMENT, "
                + "Beschreibung VARCHAR(5000), "
                + "PRIMARY KEY(Beschreibung_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenEntwicklerWirdRausgelesen() {
        String sql = "CREATE TABLE IF NOT EXISTS Entwickler ("
                + "Entwickler_ID INTEGER AUTO_INCREMENT, "
                + "Entwickler VARCHAR(255), "
                + "PRIMARY KEY(Entwickler_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenGenreWirdRausgelesen() {
        String sql = "CREATE TABLE IF NOT EXISTS Genre ("
                + "Genre_ID INTEGER AUTO_INCREMENT, "
                + "Genre VARCHAR(255), "
                + "PRIMARY KEY(Genre_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenPlattformWirdRausgelesen() {
        String sql = "CREATE TABLE IF NOT EXISTS Plattform ("
                + "Plattform_ID INTEGER AUTO_INCREMENT, "
                + "Plattform VARCHAR(255), "
                + "PRIMARY KEY(Plattform_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenPublisherWirdRausgelesen() {
        String sql = "CREATE TABLE IF NOT EXISTS Publisher ("
                + "Publisher_ID INTEGER AUTO_INCREMENT, "
                + "Publisher VARCHAR(255), "
                + "PRIMARY KEY(Publisher_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenRatingWirdRausgelesen() {
        String sql = "CREATE TABLE IF NOT EXISTS Rating ("
                + "Rating_ID INTEGER AUTO_INCREMENT, "
                + "Rating VARCHAR(255), "
                + "PRIMARY KEY(Rating_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenGameWirdReingeschrieben() {
        String sql = "CREATE TABLE IF NOT EXISTS Game ("
                + "Game_ID INTEGER AUTO_INCREMENT, "
                + "Game VARCHAR(255), "
                + "Preis VARCHAR(255), "
                + "Sitelink VARCHAR(5000),"
                + "Releasedate VARCHAR(255) ,"
                + "Rating_ID INTEGER,"
                + "Publisher_ID INTEGER,"
                + "Entwickler_ID INTEGER,"
                + "Beschreibung_ID INTEGER,"
                + "User_ID  INTEGER,"
                + "PRIMARY KEY(Game_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenSpieleGenreZwischentabelleWirdReingeschrieben() {
        String sql = "CREATE TABLE IF NOT EXISTS GameGenre ("
                + "GameGenre_ID INTEGER AUTO_INCREMENT, "
                + "Game_ID INTEGER,"
                + "Genre_ID INTEGER,"
                + "PRIMARY KEY(GameGenre_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }
    
    // =========================================================================
    private static void tabelleErzeugenSpieleGameuserZwischentabelleWirdReingeschrieben() {
        String sql = "CREATE TABLE IF NOT EXISTS GameUser ("
                + "GameUser_ID INTEGER AUTO_INCREMENT, "
                + "Game_ID INTEGER,"
                + "User_ID INTEGER,"
                + "PRIMARY KEY(GameUser_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenSpielePlattformZwischentabelleWirdReingeschrieben() {
        String sql = "CREATE TABLE IF NOT EXISTS GamePlattform ("
                + "GamePlattform_ID INTEGER AUTO_INCREMENT, "
                + "Game_ID INTEGER,"
                + "Plattform_ID INTEGER,"
                + "PRIMARY KEY(GamePlattform_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenSpieleStyleZwischentabelleWirdReingeschrieben() {
        String sql = "CREATE TABLE IF NOT EXISTS GameStyle ("
                + "GameStyle_ID INTEGER AUTO_INCREMENT, "
                + "Game_ID INTEGER ,"
                + "Style_ID INTEGER,"
                + "PRIMARY KEY(GameStyle_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenStyleWirdRausgelesen() {
        String sql = "CREATE TABLE IF NOT EXISTS Style ("
                + "Style_ID INTEGER AUTO_INCREMENT, "
                + "Style VARCHAR(255), "
                + "PRIMARY KEY(Style_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenRechteWirdRausgelesenProgrammTechnisch() {
        String sql = "CREATE TABLE IF NOT EXISTS Rechte ("
                + "Rechte_ID INTEGER AUTO_INCREMENT, "
                + "Rechte VARCHAR(255), "
                + "PRIMARY KEY(Rechte_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // =========================================================================
    private static void tabelleErzeugenUserWirdReingeschriebenUndFürLoginGenutzt() {
        String sql = "CREATE TABLE IF NOT EXISTS User ("
                + "User_ID INTEGER AUTO_INCREMENT, "
                + "Vorname VARCHAR(255), "
                + "Nachname VARCHAR(255), "
                + "Postleitzahl INTEGER , "
                + "Ort VARCHAR(255), "
                + "Strasse VARCHAR(255), "
                + "Hausnummer VARCHAR(255), "
                + "E_Mail VARCHAR(255), "
                + "Rechte_ID INTEGER, "
                + "Passwort VARCHAR(255),"
                + "PRIMARY KEY(User_ID));";

        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonEntwickler(String Entwickler) {
        String sql = String.format("INSERT INTO Entwickler (Entwickler) "
                + "VALUES ('%s');", Entwickler);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonEntwickler() {
        datenSchreibenVonEntwickler("Tencent");
        datenSchreibenVonEntwickler("Sony Interactive Entertainment");
        datenSchreibenVonEntwickler("Microsoft Studios");
        datenSchreibenVonEntwickler("Apple");
        datenSchreibenVonEntwickler("Activision Blizzard");
        datenSchreibenVonEntwickler("Google");
        datenSchreibenVonEntwickler("NetEase");
        datenSchreibenVonEntwickler("Electronic Arts");
        datenSchreibenVonEntwickler("Nintendo");
        datenSchreibenVonEntwickler("Bandai Namco Games");
        datenSchreibenVonEntwickler("Paradox Interactive");
        datenSchreibenVonEntwickler("Sega");
        datenSchreibenVonEntwickler("Take-Two Interactive");
        datenSchreibenVonEntwickler("Warner Bros. Interactive Entertainment");
        datenSchreibenVonEntwickler("Bethesda Softworks");
        datenSchreibenVonEntwickler("Team17");
        datenSchreibenVonEntwickler("Degica");
        datenSchreibenVonEntwickler("Devolver Digital");
        datenSchreibenVonEntwickler("Daedalic Entertainment");
        datenSchreibenVonEntwickler("Curve Digital");
        datenSchreibenVonEntwickler("tinyBuild Games");
        datenSchreibenVonEntwickler("NIS America");
        datenSchreibenVonEntwickler("Aksys Games");
        datenSchreibenVonEntwickler("505 Games");
        datenSchreibenVonEntwickler("Gambitious Digital Entertainment");
        datenSchreibenVonEntwickler("Focus Home Interactive");
        datenSchreibenVonEntwickler("Artifex Mundi");
        datenSchreibenVonEntwickler("Koei Tecmo");
        datenSchreibenVonEntwickler("10tons Entertainment");
        datenSchreibenVonEntwickler("Idea Factory");
        datenSchreibenVonEntwickler("Deep Silver");
        datenSchreibenVonEntwickler("Digerati Distribution");
        datenSchreibenVonEntwickler("THQ Nordic GmbH");
        datenSchreibenVonEntwickler("Aerosoft");
        datenSchreibenVonEntwickler("Astragon");
        datenSchreibenVonEntwickler("Bitcomposer");
        datenSchreibenVonEntwickler("Crytek");
        datenSchreibenVonEntwickler("TopWare Interactive");
        datenSchreibenVonEntwickler("rondomedia");
        datenSchreibenVonEntwickler("magnussoft");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonEntwickler() throws SQLException {
        String sql = "SELECT Entwickler FROM Entwickler;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("Entwickler"));
        }
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonGenre(String Genre) {
        String sql = String.format("INSERT INTO Genre (Genre) "
                + "VALUES ('%s');", Genre);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonGenre() {
        datenSchreibenVonGenre("Action");
        datenSchreibenVonGenre("Beat em ups");
        datenSchreibenVonGenre("Ego Shooter");
        datenSchreibenVonGenre("Erotikvideospiele");
        datenSchreibenVonGenre("Geschicklichkeitsspiel");
        datenSchreibenVonGenre("Jump and Runs");
        datenSchreibenVonGenre("Lernspiel");
        datenSchreibenVonGenre("Open World Spiel");
        datenSchreibenVonGenre("Musik");
        datenSchreibenVonGenre("Quiz");
        datenSchreibenVonGenre("Rollenspiel");
        datenSchreibenVonGenre("Puzzle");
        datenSchreibenVonGenre("Shoot em up");
        datenSchreibenVonGenre("Simulation");
        datenSchreibenVonGenre("Western");
        datenSchreibenVonGenre("Sport");
        datenSchreibenVonGenre("Strategie");
        datenSchreibenVonGenre("Abendteuer");
        datenSchreibenVonGenre("Anime");
        datenSchreibenVonGenre("Horror");
        datenSchreibenVonGenre("Survival");
        datenSchreibenVonGenre("SCI FI");
        datenSchreibenVonGenre("Wetlraum");
        datenSchreibenVonGenre("Roguelike");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonGenre() throws SQLException {
        String sql = "SELECT Genre FROM Genre;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("Genre"));
        }
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonPlattform(String Plattform) {
        String sql = String.format("INSERT INTO Plattform (Plattform) "
                + "VALUES ('%s');", Plattform);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonPlattform() {
        datenSchreibenVonPlattform("Switch ");
        datenSchreibenVonPlattform("PC ");
        datenSchreibenVonPlattform("PS 4");
        datenSchreibenVonPlattform("Wii");
        datenSchreibenVonPlattform("Wii U ");
        datenSchreibenVonPlattform("PS 3 ");
        datenSchreibenVonPlattform("PS 2 ");
        datenSchreibenVonPlattform("XBox one ");
        datenSchreibenVonPlattform("XBOX 360 ");
        datenSchreibenVonPlattform("XBox one X ");
        datenSchreibenVonPlattform("PS4 Pro ");
        datenSchreibenVonPlattform("PS 5 ");
        datenSchreibenVonPlattform("PS 5 Pro ");
        datenSchreibenVonPlattform("XBox Scarlett ");
        datenSchreibenVonPlattform("Mobile ");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonPlattform() throws SQLException {
        String sql = "SELECT Plattform FROM Plattform;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("Plattform"));
        }
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonPublisher(String Publisher) {
        String sql = String.format("INSERT INTO Publisher (Publisher) "
                + "VALUES ('%s');", Publisher);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonPublisher() {
        datenSchreibenVonPublisher("Tencent");
        datenSchreibenVonPublisher("Sony Interactive Entertainment");
        datenSchreibenVonPublisher("Microsoft Studios");
        datenSchreibenVonPublisher("Apple");
        datenSchreibenVonPublisher("Activision Blizzard");
        datenSchreibenVonPublisher("Google");
        datenSchreibenVonPublisher("NetEase");
        datenSchreibenVonPublisher("Electronic Arts");
        datenSchreibenVonPublisher("Nintendo");
        datenSchreibenVonPublisher("Bandai Namco Games");
        datenSchreibenVonPublisher("Paradox Interactive");
        datenSchreibenVonPublisher("Sega");
        datenSchreibenVonPublisher("Take-Two Interactive");
        datenSchreibenVonPublisher("Warner Bros. Interactive Entertainment");
        datenSchreibenVonPublisher("Bethesda Softworks");
        datenSchreibenVonPublisher("Team17");
        datenSchreibenVonPublisher("Degica");
        datenSchreibenVonPublisher("Devolver Digital");
        datenSchreibenVonPublisher("Daedalic Entertainment");
        datenSchreibenVonPublisher("Curve Digital");
        datenSchreibenVonPublisher("tinyBuild Games");
        datenSchreibenVonPublisher("NIS America");
        datenSchreibenVonPublisher("Aksys Games");
        datenSchreibenVonPublisher("505 Games");
        datenSchreibenVonPublisher("Gambitious Digital Entertainment");
        datenSchreibenVonPublisher("Focus Home Interactive");
        datenSchreibenVonPublisher("Artifex Mundi");
        datenSchreibenVonPublisher("Koei Tecmo");
        datenSchreibenVonPublisher("10tons Entertainment");
        datenSchreibenVonPublisher("Idea Factory");
        datenSchreibenVonPublisher("Deep Silver");
        datenSchreibenVonPublisher("Digerati Distribution");
        datenSchreibenVonPublisher("THQ Nordic GmbH");
        datenSchreibenVonPublisher("Aerosoft");
        datenSchreibenVonPublisher("Astragon");
        datenSchreibenVonPublisher("Bitcomposer");
        datenSchreibenVonPublisher("Crytek");
        datenSchreibenVonPublisher("TopWare Interactive");
        datenSchreibenVonPublisher("rondomedia");
        datenSchreibenVonPublisher("magnussoft");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonPublisher() throws SQLException {
        String sql = "SELECT Publisher FROM Publisher;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("Publisher"));
        }
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonRating(String FSK) {
        String sql = String.format("INSERT INTO Rating (FSK) "
                + "VALUES ('%s');", FSK);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonRating() {
        datenSchreibenVonRating("FSK 0");
        datenSchreibenVonRating("FSK 6");
        datenSchreibenVonRating("FSK 12");
        datenSchreibenVonRating("FSK 16");
        datenSchreibenVonRating("FSK 18");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonRating() throws SQLException {
        String sql = "SELECT FSK FROM Rating;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("FSK"));
        }
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonRechte(String Rechte) {
        String sql = String.format("INSERT INTO Rechte (Rechte) "
                + "VALUES ('%s');", Rechte);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonRechte() {
        datenSchreibenVonRechte("Superadmin");
        datenSchreibenVonRechte("User");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonRechte() throws SQLException {
        String sql = "SELECT Rechte FROM Rechte;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("Rechte"));
        }
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonStyle(String Style) {
        String sql = String.format("INSERT INTO Style (Style) "
                + "VALUES ('%s');", Style);
        Datenbankzugriff.getInstance().execute(sql);
    }

    // --------------------------------------------------------------
    private static void datenSchreibenVonStyle() {
        datenSchreibenVonStyle("3D");
        datenSchreibenVonStyle("2D");
        datenSchreibenVonStyle("VR");
        datenSchreibenVonStyle("Cellshading");
    }

    // --------------------------------------------------------------
    private static void datenLesenVonStyle() throws SQLException {
        String sql = "SELECT Style FROM Style;";

        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);

        rs.beforeFirst();

        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getString("Style"));
        }
    }

    // =========================================================================
    public static void main(String[] args) throws SQLException {
       /*
        //DIE DATENBANK ERZEUGEN! 
        datenbankErzeugenSpiele();
       
        //DIE TABELLEN ERZEUGEN!
        tabelleErzeugenBeschreibungWirdReingeschrieben();
        tabelleErzeugenEntwicklerWirdRausgelesen();
        tabelleErzeugenGenreWirdRausgelesen();
        tabelleErzeugenPlattformWirdRausgelesen();
        tabelleErzeugenPublisherWirdRausgelesen();
        tabelleErzeugenRatingWirdRausgelesen();
        tabelleErzeugenGameWirdReingeschrieben();
        tabelleErzeugenSpieleGenreZwischentabelleWirdReingeschrieben();
        tabelleErzeugenSpielePlattformZwischentabelleWirdReingeschrieben();
        tabelleErzeugenSpieleStyleZwischentabelleWirdReingeschrieben();
        tabelleErzeugenSpieleGameuserZwischentabelleWirdReingeschrieben();
        tabelleErzeugenStyleWirdRausgelesen();
        tabelleErzeugenRechteWirdRausgelesenProgrammTechnisch();
        tabelleErzeugenUserWirdReingeschriebenUndFürLoginGenutzt();
        */
    /*
        //SCHREIBEN
        datenSchreibenVonEntwickler();
        datenSchreibenVonGenre();
        datenSchreibenVonPlattform();
        datenSchreibenVonPublisher();
        datenSchreibenVonRating();
        datenSchreibenVonRechte();
        datenSchreibenVonStyle();
      */
    /*
        //LESEN
        datenLesenVonRechte();
        datenLesenVonEntwickler();
        datenLesenVonGenre();
        datenLesenVonPlattform();
        datenLesenVonPublisher();
        datenLesenVonRating();
        datenLesenVonStyle();
    */
    } 

}
