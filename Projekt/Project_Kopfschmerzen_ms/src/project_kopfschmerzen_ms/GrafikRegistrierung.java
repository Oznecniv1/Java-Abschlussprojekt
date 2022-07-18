package project_kopfschmerzen_ms;
//für das layout

import java.awt.BorderLayout;
import java.awt.Color;

// für den submit button
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

//für das passwort feld
import javax.swing.JPasswordField;

//für die textfelder
import javax.swing.JTextField;

//überprüfen, ob die eingaben im textfeld korrekt sind und so in der datenbank eingetragen werden können.
import java.util.regex.Pattern;

//==============================================================================
// inizialiesieren und deklarieren der sachen, die ich im programm brauche
public class GrafikRegistrierung {

    private final JFrame frame;
    
    //das panel
    private final JPanel panel;
    
    //die unterepanels
    private final JPanel center;
    private final JPanel footer;

    //die labels
    private final JLabel vornamelabel;
    private final JLabel nachnamelabel;
    private final JLabel plzlabel;
    private final JLabel ortlabel;
    private final JLabel strasselabel;
    private final JLabel hausnummerlabel;
    private final JLabel e_maillabel;
    private final JLabel passwortlabel;

    //die textfelder 
    private final JTextField vornametextfeld;
    private final JTextField nachnametextfeld;
    private final JTextField plztextfeld;
    private final JTextField orttextfeld;
    private final JTextField strassetextfeld;
    private final JTextField hausnummertextfeld;
    private final JTextField e_mailtextfeld;
    private final JTextField passwordField;

    //der Login butten 
    private final JButton login;

    //der submit butten 
    private final JButton submit;

    //==============================================================================
    // abfragen, ob die email korrekt eingegeben wurde!
    public boolean checkMailAdress(String e_mailtextfeld) {
        return (Pattern.matches("^[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})", e_mailtextfeld));
    }

    //==============================================================================
    // abfragen, ob die Postleitzahl korrekt eingegeben wurde!
    public boolean checkPLZ(String plztextfeld) {
        return (Pattern.matches("^[0-9]{5}$", plztextfeld));
    }

    //==============================================================================
    // abfragen, ob die Vorname korrekt eingegeben wurde!
    public boolean checkVorname(String vornametextfeld) {
        return (Pattern.matches("^([A-Z]{1})([A-Za-z]{1,49})", vornametextfeld));
    }
    
//==============================================================================
    // abfragen, ob die Nachname korrekt eingegeben wurde!
    public boolean checkNachname(String Nachnametextfeld) {
        return (Pattern.matches("^([A-Z]{1})+([A-Za-z]{1,49})", Nachnametextfeld));
    }
    
    //==============================================================================
    // abfragen, ob die Strasse korrekt eingegeben wurde!
    public boolean checkStrasse(String strassetextfeld) {
        return (Pattern.matches("^([A-Z]{1})+([A-Za-z]{1,49})", strassetextfeld));
    }

    //==============================================================================
    // abfragen, ob die Ort korrekt eingegeben wurde!
    public boolean checkOrt(String orttextfeld) {
        return (Pattern.matches("^([A-Z]{1})+([A-Za-z]{1,49})", orttextfeld));
    }

    //==============================================================================
    // abfragen, ob die Hausnummer korrekt eingegeben wurde!
    public boolean checkHausnummer(String hausnummertextfeld) {
        return (Pattern.matches("^([0-9]{1,3})+([A-Za-z])?", hausnummertextfeld));
    }

    //Wenn auf senden gedrückt wurde, das passiert das alles! 
    //jetzt noch abfragen, ob alles in den feldern korrekt eingegeben wurde und fertig!
    //======================================================================
    private class MyALogin implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if (sender == login) {
                GrafikLogin gse = new GrafikLogin();
                gse.los();
            }
            frame.dispose();
        }
    }
    //======================================================================    
    // der haup actionlistener
    private class MyAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if ((sender == submit)
                    && (vornametextfeld.getText().isEmpty())
                    || (nachnametextfeld.getText().isEmpty())
                    || (plztextfeld.getText().isEmpty())
                    || (orttextfeld.getText().isEmpty())
                    || (strassetextfeld.getText().isEmpty())
                    || (hausnummertextfeld.getText().isEmpty())
                    || (e_mailtextfeld.getText().isEmpty())
                    || (passwordField.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!");
            } else {
                if (checkVorname(vornametextfeld.getText())) {
                    if (checkNachname(nachnametextfeld.getText())) {
                        if (checkPLZ(plztextfeld.getText())) {
                            if (checkStrasse(strassetextfeld.getText())) {
                                if (checkHausnummer(hausnummertextfeld.getText())) {
                                    if (checkOrt(orttextfeld.getText())) {
                                        if (checkMailAdress(e_mailtextfeld.getText())) {
                                            frame.dispose();
                                            GrafikLogin gl = new GrafikLogin();
                                            gl.los();
                                            try {
                                                String sql = String.format("INSERT INTO `user`("
                                                        + "`Vorname`,"
                                                        + "`Nachname`,"
                                                        + "`Postleitzahl`,"
                                                        + "`Ort`,"
                                                        + "`Strasse`,"
                                                        + "`Hausnummer`,"
                                                        + "`E_Mail`,"
                                                        + "`Rechte_ID`,"
                                                        + "`Passwort`)"
                                                        + "VALUES ('%s','%s',%s,'%s','%s','%s','%s',%d,'%s')",
                                                        vornametextfeld.getText(),
                                                        nachnametextfeld.getText(),
                                                        plztextfeld.getText(),
                                                        orttextfeld.getText(),
                                                        strassetextfeld.getText(),
                                                        hausnummertextfeld.getText(),
                                                        e_mailtextfeld.getText(),
                                                        2,
                                                        passwordField.getText()
                                                );
                                                Datenbankzugriff.getInstance().execute(sql);
                                            } catch (Exception ex) {
                                                System.out.println("Exception: ");
                                                System.out.println("  --> " + ex.getMessage());
                                                System.out.println("  --> " + ex.getClass());
                                                ex.printStackTrace();
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Bitte E-Mail korrekt ausfüllen!");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Bitte Ort korrekt ausfüllen!");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Bitte Hausnummer korrekt ausfüllen!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Bitte Strasse korrekt ausfüllen!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Bitte Postleitzahl korrekt ausfüllen!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte Nachname korrekt ausfüllen!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte Vorname korrekt ausfüllen!");
                }
            }
            JOptionPane.showMessageDialog(null, "Alle Daten wurden korrekt übergeben!");
        }
    }

//======================================================================
    public GrafikRegistrierung() {
        // der constuckter
        // namensgebung der seite
        frame = new JFrame("Registrierung");
        
        //hauptpanel
        panel = new JPanel();
        
        //unterpanels
        center = new JPanel();
        footer = new JPanel();

        //Die labels
        vornamelabel = new JLabel("Vorname: ");
        nachnamelabel = new JLabel("Nachname: ");
        plzlabel = new JLabel("Postleitzahl: ");
        ortlabel = new JLabel("Ort: ");
        strasselabel = new JLabel("Strasse: ");
        hausnummerlabel = new JLabel("Hausnummer: ");
        e_maillabel = new JLabel("E-Mail: ");
        passwortlabel = new JLabel("Passwort: ");

        //die textfelder
        vornametextfeld = new JTextField();
        nachnametextfeld = new JTextField();
        plztextfeld = new JTextField();
        orttextfeld = new JTextField();
        strassetextfeld = new JTextField();
        hausnummertextfeld = new JTextField();
        e_mailtextfeld = new JTextField();
        passwordField = new JPasswordField();

        //der login button
        login = new JButton("Login");
        panel.add(login);

        //der reg button
        submit = new JButton("Registrieren");
        panel.add(submit);

    }

    //======================================================================
    void los() {

        //schliesst das fenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setzt das automatische layout aus von jframe
        frame.setLayout(new BorderLayout());

        // das layout
        footer.setLayout(new BorderLayout());
        frame.add(footer, BorderLayout.PAGE_END);
        frame.add(center, BorderLayout.CENTER);
        // layout manager aus
        center.setLayout(null);
        
        //farbgebung
        center.setBackground(Color.orange);
        footer.setBackground(Color.blue);
        //größe des panels, wenn man es aus dem vollbildmodus holt
        panel.setBounds(10, 10, 2500, 1400);
        
        //labelposition auf dem center
        vornamelabel.setSize(500, 150);
        vornamelabel.setLocation(1000, 100);
        
        //textfeldposition auf dem center
        vornametextfeld.setSize(250, 30);
        vornametextfeld.setLocation(1100, 160);
        
         // hinzufügen zum center
        center.add(vornamelabel);
        center.add(vornametextfeld);

        //labelposition auf dem center
        nachnamelabel.setSize(500, 150);
        nachnamelabel.setLocation(1000, 200);
        
        //textfeldposition auf dem center
        nachnametextfeld.setSize(250, 30);
        nachnametextfeld.setLocation(1100, 260);
        
         // hinzufügen zum center
        center.add(nachnamelabel);
        center.add(nachnametextfeld);

        //labelposition auf dem center
        plzlabel.setSize(500, 150);
        plzlabel.setLocation(1000, 300);
        
        //textfeldposition auf dem center
         plztextfeld.setSize(250, 30);
        plztextfeld.setLocation(1100, 360);
        
         // hinzufügen zum center
        center.add(plzlabel);
        center.add(plztextfeld);

        //labelposition auf dem center
        ortlabel.setSize(500, 150);
        ortlabel.setLocation(1000, 400);
        
        //textfeldposition auf dem center
        orttextfeld.setSize(250, 30);
        orttextfeld.setLocation(1100, 460);
        
         // hinzufügen zum center
        center.add(ortlabel);
        center.add(orttextfeld);

        //labelposition auf dem center
        strasselabel.setSize(500, 150);
        strasselabel.setLocation(1000, 500);
        
        //textfeldposition auf dem center
        strassetextfeld.setSize(250, 30);
        strassetextfeld.setLocation(1100, 560);
        
         // hinzufügen zum center
        center.add(strasselabel);
        center.add(strassetextfeld);

        //labelposition auf dem center
        hausnummerlabel.setSize(500, 150);
        hausnummerlabel.setLocation(1000, 600);
        
        //textfeldposition auf dem center
        hausnummertextfeld.setSize(250, 30);
        hausnummertextfeld.setLocation(1100, 660);
        
        // hinzufügen zum center
        center.add(hausnummerlabel);
        center.add(hausnummertextfeld);

        //labelposition auf dem center
        e_maillabel.setSize(500, 150);
        e_maillabel.setLocation(1000, 700);
        
        //textfeldposition auf dem center
        e_mailtextfeld.setSize(250, 30);
        e_mailtextfeld.setLocation(1100, 760);
        
         // hinzufügen zum center
        center.add(e_maillabel);
        center.add(e_mailtextfeld);

        //labelposition auf dem center
        passwortlabel.setSize(500, 150);
        passwortlabel.setLocation(1000, 800);
        
        //textfeldposition auf dem center
        passwordField.setSize(250, 30);
        passwordField.setLocation(1100, 860);
        
        // hinzufügen zum center
        center.add(passwortlabel);
        center.add(passwordField);

        //======================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        submit.setBounds(460, 320, 150, 30);
        submit.addActionListener(new MyAL());
        footer.add(submit, BorderLayout.LINE_END);

        //======================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        login.setBounds(0, 320, 150, 30);
        login.addActionListener(new MyALogin());
        footer.add(login, BorderLayout.LINE_START);

        //======================================================================
        //der ort, wo sich das fenster öffnet
        frame.setLocation(1000, 1000);

        //Welche größe das fenster hat
        frame.setSize(1920, 1080);

        //Ermittelt die fenstergröße automatisch
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //======================================================================
        //lässt das fenster erscheinen
        frame.setVisible(true);

    }

}
