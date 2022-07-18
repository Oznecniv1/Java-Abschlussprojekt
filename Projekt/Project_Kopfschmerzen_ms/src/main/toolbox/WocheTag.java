package main.toolbox;

public class WocheTag
{

    private int woche, tag;
    private boolean ueberEingabe;

    // ------------------------------------------------------------------
    public WocheTag(int woche, int tag, boolean ueberEingabe)
    {
        this.woche = woche;
        this.tag = tag;
        this.ueberEingabe = ueberEingabe;
    }

    // ------------------------------------------------------------------
    public int getWoche()
    {
        return woche;
    }

    // ------------------------------------------------------------------
    public int getTag()
    {
        return tag;
    }

    // ------------------------------------------------------------------
    public boolean getUeberEingabe()
    {
        return ueberEingabe;
    }

    // ------------------------------------------------------------------
}
