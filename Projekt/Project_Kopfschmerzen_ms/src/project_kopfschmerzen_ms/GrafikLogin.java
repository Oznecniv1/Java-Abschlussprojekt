package project_kopfschmerzen_ms;

//für das layout
import java.awt.BorderLayout;
import java.awt.Color;

// für den submit button
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

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

//==============================================================================
// inizialiesieren und deklarieren der sachen, die ich im programm brauche
public class GrafikLogin {

    private final JFrame frame;
    
    //das panel
    private final JPanel panel;

    //die unterpanels
    private final JPanel center;
    private final JPanel footer;

    //die labels
    private final JLabel e_maillabel;
    private final JLabel passwortlabel;

    //die textfelder 
    private final JTextField e_mailtextfeld;
    private final JTextField passwordField;

    //der Login butten 
    private final JButton Login;

    //der registrierung butten 
    private final JButton registrierung;

    //Wenn auf senden gedrückt wurde passiert das alles! 
    //jetzt noch abfragen, ob alles in den feldern korrekt eingegeben wurde und fertig!
    //==============================================================================
    private class MyALregistrierung implements ActionListener {

        // der ActionListener der zu spieleeintrag führt
        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if (sender == registrierung) {
                GrafikRegistrierung gr = new GrafikRegistrierung();
                gr.los();
            }
            frame.dispose();
        }
    }
//==============================================================================    

    private class MyAL implements ActionListener {
// der ActionListener der überprüft, ob der user überhaupt vorhaden ist.

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if ((sender == Login)
                    && (e_mailtextfeld.getText().isEmpty())
                    || (passwordField.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!");
            } else {
                try {
                    String sql = ("SELECT user_id, e_mail, rechte_id, passwort "
                            + "FROM user "
                            + "WHERE e_mail= '" + e_mailtextfeld.getText()
                            + "' AND passwort='" + passwordField.getText()
                            + "';");

                    ResultSet rs = Datenbankzugriff.getInstance().executeQuery(sql);
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "SIE HABEN SICH ERFOLGREICH EINGELOGGT!");
                        frame.dispose();
                        Grafikuebersichtsseite gue = new Grafikuebersichtsseite();
                        gue.los();
                    } else {
                        JOptionPane.showMessageDialog(null, "GIBT EIN RICHTIGES PASSWORT EIN!");
                    }
                } catch (Exception ex) {
                    System.out.println("Exception: ");
                    System.out.println("  --> " + ex.getMessage());
                    System.out.println("  --> " + ex.getClass());
                    ex.printStackTrace();
                }
            }
        }
    }

    //======================================================================
    public GrafikLogin() {

        //der constucter
        // gibt der seite den namen
        frame = new JFrame("Login");
        
        // der jpanel. wo sich alles darstellen lässt
        panel = new JPanel();
        
        // unterkategorien des jpanels
        center = new JPanel();
        footer = new JPanel();

        //Die labels
        e_maillabel = new JLabel("E-Mail: ");
        passwortlabel = new JLabel("passwort: ");

        //die textfelder
        e_mailtextfeld = new JTextField();
        passwordField = new JPasswordField();

        //der login button
        Login = new JButton("Login");
        panel.add(Login);

        //der login button
        registrierung = new JButton("registrierung");
        panel.add(registrierung);

    }
//======================================================================

    void los() {

        //schliesst das fenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setzt das automatische layout aus von jframe
        frame.setLayout(new BorderLayout());

        // Das layout
        footer.setLayout(new BorderLayout());
        frame.add(footer, BorderLayout.PAGE_END);
        frame.add(center, BorderLayout.CENTER);
        center.setLayout(null);
        
        //farbgebung ddes inhalts
        center.setBackground(Color.green);
        footer.setBackground(Color.yellow);
        
        //die größe des layout's
        panel.setBounds(10, 10, 2500, 1400);
        
        //labelposition und textfeldposition auf dem frame
        e_maillabel.setSize(500, 150);
        e_maillabel.setLocation(1000, 500);
        e_mailtextfeld.setSize(250, 30);
        e_mailtextfeld.setLocation(1100, 560);
        center.add(e_maillabel);
        center.add(e_mailtextfeld);

        //labelposition und textfeldposition auf dem frame
        passwortlabel.setSize(500, 150);
        passwortlabel.setLocation(1000, 600);
        passwordField.setSize(250, 30);
        passwordField.setLocation(1100, 660);
        center.add(passwortlabel);
        center.add(passwordField);

        //======================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        //position des buttons
        Login.setBounds(450, 320, 150, 30);
        Login.addActionListener(new GrafikLogin.MyAL());
        footer.add(Login, BorderLayout.LINE_END);

        //======================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        //position des buttons
        registrierung.setBounds(0, 320, 150, 30);
        registrierung.addActionListener(new MyALregistrierung());
        footer.add(registrierung, BorderLayout.LINE_START);

        //======================================================================
        //der ort, wo sich das fenster öffnet
        frame.setLocation(1500, 1500);

        //Welche größe das fenster hat
        frame.setSize(1920, 1080);

        //Ermittelt die fenstergröße automatisch
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //======================================================================
        //lässt das fenster erscheinen
        frame.setVisible(true);

    }

}
