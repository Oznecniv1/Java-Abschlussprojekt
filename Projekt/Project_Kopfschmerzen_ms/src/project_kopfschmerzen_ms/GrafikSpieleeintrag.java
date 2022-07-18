package project_kopfschmerzen_ms;

//für das layout
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

// für den submit button
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

//für den button
import javax.swing.JButton;

//für das ausgeben, des fensters
import javax.swing.JFrame;

//für das label
import javax.swing.JLabel;

//ausgabefenster, das etwas nicht korrekt eingetragen wurde oder ,ob alles korrekt eingertagen wurde
import javax.swing.JOptionPane;

// für den panel
import javax.swing.JPanel;

//comboboxen
import javax.swing.JComboBox;

//checkboxen
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import java.util.regex.Pattern;
import javax.swing.JComponent;

//für die textfelder
import javax.swing.JTextField;

//textarea
import javax.swing.JTextArea;
//==============================================================================

public class GrafikSpieleeintrag {

    private final JFrame frame;

    private final JPanel panel;

    private final JPanel maske;

    //die labels
    private final JLabel spielnamelabel;
    private final JLabel preislabel;
    private final JLabel linklabel;
    private final JLabel datumlabel;
    private final JLabel beschreibunglabel;
    private final JLabel publisherlabel;
    private final JLabel entwicklerlabel;
    private final JLabel ratinglabel;
    private final JLabel genrelabel;
    private final JLabel plattformlabel;
    private final JLabel stylelabel;

    //die textfelder 
    private final JTextField spielnametextfeld;
    private final JTextField preistextfeld;
    private final JTextField linktextfeld;
    private final JTextField datumtextfeld;

    //Textarea
    private final JTextArea beschreibungtextarea;

    //zum scrollen
    private final JScrollPane scroller;

    //selectboxen
    private final JComboBox publishercomboBox;
    private final JComboBox entwicklercomboBox;
    private final JComboBox ratingcomboBox;

    //checkboxen
    private final JPanel centerpanel, footerpanel;
    private final JPanel pnlgenre, pnlstyle, pnlplattform;
    private int cbzaehler;

    private int xleft, xright, yleft, yright, widthleft, rowheight, widthright;
    private int cbwidth;
    //der submit butten 
    private final JButton eintragen;

    private final JButton Grafikuebersichtsseite;

    //=========================================================================
    private class MyWL implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent ce) {
            int fh = frame.getHeight() - frame.getInsets().top - frame.getInsets().bottom;
            int positionX = (frame.getWidth() - centerpanel.getWidth()) / 2;
            int positionY = (fh - centerpanel.getHeight()) / 2;
            centerpanel.setLocation(positionX, positionY);
        }

        @Override
        public void componentMoved(ComponentEvent ce) {

        }

        @Override
        public void componentShown(ComponentEvent ce) {

        }

        @Override
        public void componentHidden(ComponentEvent ce) {

        }

    }
//==============================================================================
//regular expression für spielnametextfeld. groß und klein schreibung wird überprüft namensbegrenser(50 zeichen)

    public boolean checkSpielename(String spielnametextfeld) {
        return (Pattern.matches("^([A-Z]{1})([A-Za-z]{1,49})", spielnametextfeld));
    }
//==============================================================================
//regular expression für preistextfeld es können nur uahlen eigegeben werden

    public boolean checkPreis(String preistextfeld) {
        return (Pattern.matches("^(d+.d{1,2})$", preistextfeld));
    }
//==============================================================================
//regular expression fürs datumtextfeld ddmmyyyy  https://stackoverflow.com/questions/15491894/regex-to-validate-date-format-dd-mm-yyyy-with-leap-year-support

    public boolean checkDatum(String datumtextfeld) {
        return (Pattern.matches("^(((0[1-9]|[12][0-9]|30)[-/]?(0[13-9]|1[012])"
                + "|31[-/]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-/]?02)[-/]"
                + "?[0-9]{4}|29[-/]?02[-/]?([0-9]{2}(([2468][048]|[02468][48])|"
                + "[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$",
                datumtextfeld));
    }
//==============================================================================
//regular expression für linktextfeld https://stackoverflow.com/questions/3809401/what-is-a-good-regular-expression-to-match-a-url

    public boolean checkURL(String linktextfeld) {
        return (Pattern.matches("^(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]"
                + "[a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]"
                + "[a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|"
                + "(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]"
                + "{2,}) $", linktextfeld));
    }
//==============================================================================
//zieht alles aus der datenbank

    private int liesid(String tabelle, String spalte, String whereSpalte, String wert) {
        String sqlNeu = String.format("SELECT %s from %s where %s = '%s';", spalte, tabelle, whereSpalte, wert);
        ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sqlNeu);
        int id = 0;
        try {
            rs.beforeFirst();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return id;
    }

    //======================================================================
    private class MyALue implements ActionListener {
// für den butten, damit ich auf die übersichtsseite kann

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if (sender == Grafikuebersichtsseite) {
                Grafikuebersichtsseite gue = new Grafikuebersichtsseite();
                gue.los();
            }
            frame.dispose();
        }
    }
//==============================================================================

    private class MyAL implements ActionListener {

        private void checkboxenspeichern(JPanel pnl, String tabelle, int game_ID) throws SQLException {
            String sqlNeu = "";
            ResultSet rs;
            TreeMap<String, Integer> map = new TreeMap<>();
            sqlNeu = String.format("select %s_ID,%s from %s;", tabelle, tabelle, tabelle);
            rs = Datenbankzugriff.getInstance().executeQuery(sqlNeu);
            rs.beforeFirst();
            while (rs.next()) {
                map.put(rs.getString(2), rs.getInt(1));
            }

            for (Component comp : pnl.getComponents()) {
                JCheckBox cb = (JCheckBox) comp;
                if (cb.isSelected()) {
                    Integer id = map.get(cb.getText());
                    if (id == null) {
                        System.out.println("######### --> " + cb.getText());
                    }
                    sqlNeu = String.format("insert into game%s( game_id,%s_id)values(%d,%d);",
                            cb.getActionCommand(), cb.getActionCommand(),
                            game_ID, id);
                    Datenbankzugriff.getInstance().execute(sqlNeu);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if ((sender == eintragen)
                    && (spielnametextfeld.getText().isEmpty())
                    || (preistextfeld.getText().isEmpty())
                    || (datumtextfeld.getText().isEmpty())
                    || (linktextfeld.getText().isEmpty())
                    || (beschreibungtextarea.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!");
            } else {
                //hier hin mit den überprüfungen!
                if (checkSpielename(spielnametextfeld.getText())
                        && checkPreis(preistextfeld.getText())
                        && checkDatum(datumtextfeld.getText())
                        && checkURL(linktextfeld.getText())) {
                    JOptionPane.showMessageDialog(null, "Alle Daten wurden korrekt übergeben. "
                            + "bitte zum login!");
                }
                frame.dispose();
                try {
                    String sql = String.format("INSERT INTO `beschreibung`("
                            + "`Beschreibung`)"
                            + "VALUES ('%s'); ",
                            (beschreibungtextarea.getText()));
                    Datenbankzugriff.getInstance().execute(sql);
//==============================================================================
//ließt das rating aus der datenbank
                    int rating_id = liesid("rating", "rating_id", "rating",
                            ratingcomboBox.getSelectedItem().toString());
//==============================================================================
//ließt den publisher aus der datenbank
                    int publisher_id = liesid("publisher", "publisher_id", "publisher",
                            publishercomboBox.getSelectedItem().toString());
//==============================================================================
//ließt den entwickler aus der datenbank
                    int entwickler_id = liesid("entwickler", "entwickler_id", "entwickler",
                            entwicklercomboBox.getSelectedItem().toString());
//==============================================================================
//ließt die beschreibung aus der datenbank
                    int beschreibung_id = liesid("beschreibung", "beschreibung_id", "beschreibung",
                            beschreibungtextarea.getText());
//==============================================================================
                    String sqlNeu = String.format("SELECT user_id from user where concat(Vorname,' "
                            + "',Nachname) like '%s';",
                            "Mario Sigismondi");
                    ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sqlNeu);
                    int user_id = 0;

                    rs.beforeFirst();

                    user_id = 1;
//==============================================================================
//schreibt alles in die dantenbank
                    Datenbankzugriff.getInstance().execute(sql);
                    sql = String.format("INSERT INTO game("
                            + "Game, "
                            + "Preis, "
                            + "Sitelink, "
                            + "Releasedate, "
                            + "Rating_ID, "
                            + "Publisher_ID, "
                            + "Entwickler_ID, "
                            + "Beschreibung_ID, "
                            + "User_ID) "
                            + "VALUES ('%s','%s','%s','%s',%d,%d,%d,%d,%d);",
                            spielnametextfeld.getText(),
                            preistextfeld.getText(),
                            linktextfeld.getText(),
                            datumtextfeld.getText(),
                            rating_id,
                            publisher_id,
                            entwickler_id,
                            beschreibung_id,
                            user_id);

                    Datenbankzugriff.getInstance().execute(sql);
//=============================================================================
// schreibt die angeklickten checkboxen in die datenbank und setzt systematisch die verlinkung mit den zwischentabellen
                    int game_ID = liesid("game", "Game_ID", "game", spielnametextfeld.getText());
                    try {
                        checkboxenspeichern(pnlgenre, "genre", game_ID);
                        checkboxenspeichern(pnlplattform, "plattform", game_ID);
                        checkboxenspeichern(pnlstyle, "style", game_ID);
                    } catch (SQLException ex) {
                    }

//==============================================================================
                } catch (Exception ex) {
                    System.out.println("Exception: ");
                    System.out.println("  --> " + ex.getMessage());
                    System.out.println("  --> " + ex.getClass());
                    ex.printStackTrace();
                }
            }
            /*JOptionPane.showMessageDialog(
                    null,
                    "bitte alles korrekt ausfüllen! keine leerzeichen! auf groß "
                    + "und kleinschreibung achten!");*/
        }
    }
//==============================================================================

    public GrafikSpieleeintrag() {
        cbzaehler = 0;

        pnlgenre = new JPanel();
        pnlstyle = new JPanel();
        pnlplattform = new JPanel();

        frame = new JFrame("Spieleeintragen");

        panel = new JPanel();

        //
        maske = new JPanel();

        //hier gebe ich die position an
        xleft = 10;
        yleft = 10;
        widthleft = 150;
        xright = xleft + 10 + widthleft;
        yright = 10;
        //hier geben ich die größen an für die textfelder
        rowheight = 30;
        widthright = 800;
        cbwidth = 194;

        //Die labels
        spielnamelabel = new JLabel("spielname: ");
        preislabel = new JLabel("preis: ");
        linklabel = new JLabel("link: ");
        datumlabel = new JLabel("datum: ");
        beschreibunglabel = new JLabel("beschreibung: ");
        publisherlabel = new JLabel("publisher: ");
        entwicklerlabel = new JLabel("entwickler: ");
        ratinglabel = new JLabel("rating: ");
        genrelabel = new JLabel("genre: ");
        plattformlabel = new JLabel("plattform: ");
        stylelabel = new JLabel("style: ");

        //die textfelder
        spielnametextfeld = new JTextField();
        preistextfeld = new JTextField();
        linktextfeld = new JTextField();
        datumtextfeld = new JTextField();

        //die comboboxen
        publishercomboBox = new JComboBox(ziehenAusDerDatenbank("publisher", false, null));
        entwicklercomboBox = new JComboBox(ziehenAusDerDatenbank("entwickler", false, null));
        ratingcomboBox = new JComboBox(ziehenAusDerDatenbank("rating", false, null));

        //die checkboxen
        centerpanel = new JPanel();
        footerpanel = new JPanel();

        //die checkboxen
        ziehenAusDerDatenbank("Genre", true, pnlgenre);
        ziehenAusDerDatenbank("Plattform", true, pnlplattform);
        ziehenAusDerDatenbank("Style", true, pnlstyle);

        //die textarea mit scollbalken
        beschreibungtextarea = new JTextArea();
        scroller = new JScrollPane();

// der butten eintragen
        eintragen = new JButton("eintragen");
        panel.add(eintragen);

// der buttoen zur übersichtsseite
        Grafikuebersichtsseite = new JButton("Übersichtsseite");
        panel.add(Grafikuebersichtsseite);

    }
//==============================================================================
//dynamische methode, die alle tabellen in der db anspricht und aus der db zieht
//Alle Tabellen müssen wie die id und die Tabelle heissen

    private String[] ziehenAusDerDatenbank(String feld, boolean c, JPanel pnl) {
        ArrayList<String> al = new ArrayList<>();
        try {
            String sql = "SELECT " + feld + " FROM " + feld + " order by " + feld + " asc;";
            ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);
            rs.beforeFirst();
            while (rs.next()) {
                if (c) {
                    cbzaehler++;

                    JCheckBox cb = new JCheckBox(rs.getString(feld));
                    cb.setActionCommand(feld);
                    cb.setPreferredSize(new Dimension(cbwidth, 15));

                    pnl.add(cb);
                }
                al.add(rs.getString(feld));
            }

        } catch (SQLException ex) {
            System.out.println("Exception: ");
            System.out.println("  --> " + ex.getMessage());
            System.out.println("  --> " + ex.getClass());
            ex.printStackTrace();
        }
        String[] a = new String[al.size()];
        al.toArray(a);

        return a;
    }
//==============================================================================
// hier wird die größe der einzelnden checkboxen berechnet

    private void machezeile(JComponent left, JComponent right, int height) {

        left.setSize(widthleft, height);
        left.setLocation(xleft, yleft);
        maske.add(left);

        right.setSize(widthright, height);
        right.setLocation(xright, yright);
        maske.add(right);

        yleft += 10 + height;
        yright += 10 + height;
    }
//==============================================================================
    // berechnet die höhe der checkboxpanels automatisch und hält sie zentriert

    private int berechneHoehe(JPanel pnl) {
        int zeilen, hoehe, cbprozeile, vgap;

        vgap = ((FlowLayout) pnl.getLayout()).getVgap();
        cbprozeile = widthright / cbwidth;
        zeilen = (int) Math.ceil((double) pnl.getComponentCount() / cbprozeile);
        hoehe = zeilen * (15 + vgap) + vgap;
        return hoehe;

    }

    void los() {
        //schliesst das fenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setzt das automatische layout aus von jframe
        frame.setLayout(null);
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        //Keine ahnung wie ich das kommentieren soll!   
        centerpanel.setBounds(10, 10, 1000, 1300);
        centerpanel.setLayout(new BorderLayout());
        footerpanel.setLayout(new BorderLayout());

        frame.add(centerpanel);
        centerpanel.add(maske, BorderLayout.CENTER);
        centerpanel.add(footerpanel, BorderLayout.PAGE_END);

        maske.setLayout(null);
        maske.setBackground(Color.yellow);
//==============================================================================
//das layout der checkboxen
        pnlgenre.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlplattform.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlstyle.setLayout(new FlowLayout(FlowLayout.LEFT));

//==============================================================================
        //labelposition und textfeldposition auf dem panel
        machezeile(spielnamelabel, spielnametextfeld, rowheight);

        //labelposition und textfeldposition auf dem panel
        machezeile(preislabel, preistextfeld, rowheight);

        //labelposition und textfeldposition auf dem panel
        machezeile(linklabel, linktextfeld, rowheight);

        //labelposition und textfeldposition auf dem panel
        machezeile(datumlabel, datumtextfeld, rowheight);

        //für die textarea zum scrollen
        machezeile(beschreibunglabel, beschreibungtextarea, 200);

        //die farbgebung der textfelder 
        spielnametextfeld.setBackground(Color.green);
        preistextfeld.setBackground(Color.white);
        entwicklercomboBox.setBackground(Color.red);
        linktextfeld.setBackground(Color.red);
        datumtextfeld.setBackground(Color.green);
        beschreibungtextarea.setBackground(Color.white);

//====================ab hier fangen die comboboxen an!=========================
        //labelposition und textfeldposition auf dem panel
        machezeile(entwicklerlabel, entwicklercomboBox, rowheight);

        //labelposition und textfeldposition auf dem panel
        machezeile(publisherlabel, publishercomboBox, rowheight);

        //labelposition und textfeldposition auf dem panel
        machezeile(ratinglabel, ratingcomboBox, rowheight);

        // die farbgebung der comboboxen
        ratingcomboBox.setBackground(Color.green);
        publishercomboBox.setBackground(Color.white);
        entwicklercomboBox.setBackground(Color.red);

//====================ab hier fangen die checkboxen an!=========================
        //labelposition und textfeldposition auf dem panel
        machezeile(genrelabel, pnlgenre, berechneHoehe(pnlgenre));

        //labelposition und textfeldposition auf dem panel
        machezeile(plattformlabel, pnlplattform, berechneHoehe(pnlplattform));

        //labelposition und textfeldposition auf dem panel
        machezeile(stylelabel, pnlstyle, berechneHoehe(pnlstyle));

//==============================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        eintragen.setBounds(50, 20, 150, 30);
        eintragen.addActionListener(new MyAL());
        footerpanel.add(eintragen, BorderLayout.LINE_END);
//==============================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        Grafikuebersichtsseite.setBounds(50, 20, 150, 30);
        Grafikuebersichtsseite.addActionListener(new MyALue());
        footerpanel.add(Grafikuebersichtsseite, BorderLayout.LINE_START);
//==============================================================================
        //die farbgebung der checkboxen
        pnlstyle.setBackground(Color.green);
        pnlplattform.setBackground(Color.white);
        pnlgenre.setBackground(Color.red);

        // die farbgebung des footers
        footerpanel.setBackground(Color.blue);

        //der ort, wo sich das fenster öffnet
        frame.setLocation(1000, 600);

        //Welche größe das fenster hat
        frame.setSize(1100, 1300);

        //Ermittelt die fenstergröße automatisch
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//==============================================================================
        //lässt das fenster erscheinen
        frame.setVisible(true);

        // keine ahunung
        frame.addComponentListener(new MyWL());

        // automatische anpassung des panels
        int hoehe = yleft + 10 + footerpanel.getHeight();
        centerpanel.setSize(centerpanel.getWidth(), hoehe);
    }

}
