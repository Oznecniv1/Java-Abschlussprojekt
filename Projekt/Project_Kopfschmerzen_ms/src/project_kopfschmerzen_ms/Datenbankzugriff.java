package project_kopfschmerzen_ms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenbankzugriff
{

    public static final String DATENBANKNAME = "spielesammlung";
    private static Datenbankzugriff instance = null;
    public static boolean debugmodeActive = true;
    /**
     * Der Treiber, der genutzt werden soll, um auf die Datenbank zugreifen zu
     * koennen.
     */
    private String treiber;

    /**
     * Die Adresse des Servers. Er kann im Internet oder auch lokal liegen.
     * Ebenfalls ist ein eingebetteter SQLite-Server moeglich.
     */
    private String dbURL;

    /**
     * Verbindung zur Datenbank.
     */
    private Connection cn;

    /**
     * Das Statement, mit dem alle Datenbankbefehle ausgefuehrt werden.
     */
    private Statement st;

    // ------------------------------------------------------------------
    private Datenbankzugriff()
    {
        treiber = "com.mysql.cj.jdbc.Driver";

        dbURL = "jdbc:mysql://localhost:3306"
                + "?useUnicode=true"
                + "&useJDBCCompliantTimezoneShift=true"
                + "&useLegacyDatetimeCode=false"
                + "&serverTimezone=UTC";

        try
        {
            /*
             * Bindet den Treiber ein: holt Klasse und legt Objekt in der
             * virtuellen Maschine ab
             */
            Class.forName(treiber);

            // bereite mit dem Treiber eine DB-Verbindung vor (URL, Username, Passwort)
            cn = DriverManager.getConnection(dbURL, "root", "");

            // Endgueltige Verbindung zum Datenbankserver erzeugen
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            execute("USE " + DATENBANKNAME);
        } catch (ClassNotFoundException | SQLException ex)
        {
            if (debugmodeActive)
            {
                ex.printStackTrace();
            }
            System.out.println("FEHLER: Laeuft der Server, ist der Treiber installiert?");
        }
    }

    // --------------------------------------------------------------
    public static Datenbankzugriff getInstance()
    {
        if (instance == null)
        {
            instance = new Datenbankzugriff();
        }

        return instance;
    }

    // --------------------------------------------------------------
    public boolean execute(String sql)
    {
        boolean back = true;

        if (Datenbankzugriff.debugmodeActive)
        {
            System.out.println(sql);
        }

        try
        {
            back = st.execute(sql);
        } catch (SQLException ex)
        {
            if (debugmodeActive)
            {
                System.out.println("------> " + sql);
                ex.printStackTrace();
            }
        }
        return back;
    }

    // --------------------------------------------------------------
    /**
     * Das <code>ResultSet</code> ist der Zeiger auf die Fundstellen der
     * Datensaetze, die ausgelesen wurden;
     * @param sql SQL-String
     * @return Fundstellen
     */
    public ResultSet executeQuery(String sql)
    {
        ResultSet rs = null;

        if (debugmodeActive)
        {
            System.out.println(sql);
        }

        try
        {
            rs = st.executeQuery(sql);
        } catch (SQLException ex)
        {
            if (debugmodeActive)
            {
                ex.printStackTrace();
            }
        }
        return rs;
    }

    // ------------------------------------------------------------------
}
