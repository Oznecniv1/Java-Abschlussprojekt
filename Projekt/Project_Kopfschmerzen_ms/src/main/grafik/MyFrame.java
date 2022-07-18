package main.grafik;

import java.awt.LayoutManager;
import javax.swing.JFrame;

public class MyFrame extends JFrame
{

    // ------------------------------------------------------------------
    public MyFrame(LayoutManager lm)
    {
        this("", lm);
    }

    // ------------------------------------------------------------------
    public MyFrame(String title, LayoutManager lm)
    {
        super(title);
        this.setLayout(lm);
    }

    // ------------------------------------------------------------------
    public void showComponent()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBounds(2100, 100, 900, 700);
        this.setVisible(true);
    }

    // ------------------------------------------------------------------
}
