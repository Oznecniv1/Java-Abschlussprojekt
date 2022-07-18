package project_kopfschmerzen_ms;
// für das layout
import java.awt.BorderLayout;
// für die farbgebung
import java.awt.Color;
// für die buttons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
// um auf den frame zuzugreiffen
import javax.swing.JFrame;
// um auf den frame panels zu kreieren
import javax.swing.JPanel;
//==============================================================================
// inizialiesieren und deklarieren der sachen, die ich im programm brauche
public class Grafikuebersichtsseite {
    
    private final JFrame frame;
    
    //das panel
    private final JPanel footer;
    private final JPanel panel;

    // der button der zu spieleeintrag führt
    private final JButton spieleeintrag;

    //der button der wieder zum login führt
    private final JButton ausloggen;
//==============================================================================

    private class MyAL implements ActionListener {

        // der ActionListener der zu spieleeintrag führt
        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if (sender == spieleeintrag) {
                GrafikSpieleeintrag gse = new GrafikSpieleeintrag();
                gse.los();
            }
        }
    }
//==============================================================================    

    private class MyALogout implements ActionListener {
        // der ActionListener der dich ausloggt

        @Override
        public void actionPerformed(ActionEvent ae) {
            Object sender = ae.getSource();
            if (sender == ausloggen) {
                GrafikLogin gl = new GrafikLogin();
                gl.los();
            }
            frame.dispose();
        }
    }
//==============================================================================

    public Grafikuebersichtsseite() {
        // der constucter
// namensgebung der seite
        frame = new JFrame("uebersichtsseite");
// wird der panel constuiert
        panel = new JPanel();
        // wird der footer vom panel construiert
        footer = new JPanel();
// der button spieleeintrag
        spieleeintrag = new JButton("Spieleeintrag");
// der button auslogen
        ausloggen = new JButton("auslogen");

        panel.add(spieleeintrag);
    }
//==============================================================================

    void los() {

        //schliesst das fenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setzt das automatische layout aus von jframe
        frame.setLayout(new BorderLayout());

        //für das layout
        footer.setLayout(new BorderLayout());
        frame.add(footer, BorderLayout.PAGE_END);
        frame.add(panel, BorderLayout.CENTER);
        
        // farbgebung des layouts
        panel.setBackground(Color.green);
        
        //größe des panels, wenn man es aus dem vollbildmodus holt
        panel.setBounds(10, 10, 1500, 800);
        frame.add(panel);

        //======================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        spieleeintrag.setBounds(450, 320, 150, 30);
        spieleeintrag.addActionListener(new MyAL());
        footer.add(spieleeintrag, BorderLayout.LINE_END);

        //======================================================================
        //button kombi aus setLocation und setSize(x,y,breite,hoehe)
        ausloggen.setBounds(50, 320, 150, 30);
        ausloggen.addActionListener(new MyALogout());
        footer.add(ausloggen, BorderLayout.LINE_START);

        //======================================================================
        //der ort, wo sich das fenster öffnet
        frame.setLocation(100, 100);

        //Welche größe das fenster hat
        frame.setSize(700, 500);

        //Ermittelt die fenstergröße automatisch
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //======================================================================
        //lässt das fenster erscheinen
        frame.setVisible(true);

    }
}
