package main.toolbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Tools
{

    // ------------------------------------------------------------------
    public static WocheTag autoWocheTag(LocalDate start)
    {
        LocalDate datum = LocalDate.now();
        LocalTime jetzt = LocalTime.now();
        if (jetzt.isBefore(LocalTime.of(8, 30)))
        {
            datum = datum.minusDays(
                    (datum.getDayOfWeek() == DayOfWeek.MONDAY) ? 3
                    : (datum.getDayOfWeek() == DayOfWeek.SATURDAY) ? 1
                    : (datum.getDayOfWeek() == DayOfWeek.SUNDAY) ? 2 : 1);
        }

        int woche = (int) start.until(datum, ChronoUnit.WEEKS) + 1;
        int tag = datum.getDayOfWeek().getValue();

        return new WocheTag(woche, tag, false);
    }

    // ------------------------------------------------------------------
    public static WocheTag holeWocheTag(String[] args)
    {
        WocheTag wt;
        boolean ueberEingabe = (args.length == 0);

        if (!ueberEingabe)
        {
            if (args[0].trim().equals("-auto"))
            {
                wt = autoWocheTag(LocalDate.of(2021, 6, 7));
            } else
            {
                int wtNum = Integer.parseInt(args[0]);
                wt = new WocheTag(wtNum / 10, wtNum % 10, false);
            }
        } else
        {
            Scanner sc = new Scanner(System.in);
            System.out.println('\n' + "Beispiele:");
            System.out.println(rpt('-', 10));
            System.out.println("51  ... Woche  5, Tag 1");
            System.out.println("103 ... Woche 10, Tag 3");
            System.out.println("0   ... Ende");

            System.out.print("Deine Eingabe: ");
            int wtNum = sc.nextInt();
            wt = new WocheTag(wtNum / 10, wtNum % 10, true);
            System.out.println();
        }

        return wt;
    }

    // ------------------------------------------------------------------
    public static String rpt(char ch, int laenge)
    {
        String back = "";
        while (back.length() < laenge)
        {
            back += ch;
        }
        return back;
    }

    // ------------------------------------------------------------------
    private static String toCanPath(File f)
    {
        String back;

        try
        {
            back = f.getCanonicalPath();
        } catch (IOException ex)
        {
            back = f.getAbsolutePath();
        }

        return back;
    }

    // ------------------------------------------------------------------
    public static String[] createNames(WocheTag wt)
    {
        String[] back = new String[3];

        String pn, kn, mn;

        pn = String.format("w%02dt%d", wt.getWoche(), wt.getTag());
        File src = new File("./src/");
        ArrayList<String> packages = new ArrayList<>();
        final String pnf = pn;
        FilenameFilter fnf = (File dir, final String name) ->
        {
            boolean ok;
            File f = new File(toCanPath(src) + File.separatorChar + name);
            if (ok = (f.isDirectory() && (name.startsWith(pnf))))
            {
                packages.add(name);
            }

            return ok;
        };
        src.listFiles(fnf);

        if (packages.size() > 0)
        {
            back[0] = packages.get(0);
            pn = back[0];
        } else
        {
            back[0] = pn;
            System.out.println(pn + " wird erzeugt.");
            new File(src.getAbsolutePath() + File.separatorChar + pn).mkdir();
        }

        kn = String.format("StartklasseW%02dT%d", wt.getWoche(), wt.getTag());
        back[1] = kn;
        File fileK = new File(toCanPath(src) + File.separatorChar + pn
                + File.separatorChar + kn + ".java");
        mn = String.format("startW%02dT%d", wt.getWoche(), wt.getTag());
        back[2] = mn;
        if (!fileK.exists())
        {
            System.out.println(fileK.getAbsolutePath() + " wird erzeugt");
            try
            {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileK)))
                {
                    bw.write("package " + pn + ";\n\n");
                    bw.write("public class " + kn + "\n{\n");
                    bw.write("    // " + rpt('-', 67) + '\n');
                    bw.write("    public static void " + mn + "()\n    {\n    "
                            + "    System.out.println(\"Hallo in " + kn + "\");\n"
                            + "    }\n");
                    bw.write("\n    // " + rpt('-', 67) + "\n}\n");
                }
            } catch (IOException ex)
            {
                System.out.printf("FEHLER beim Erzeugen von \"%s\": %s",
                        fileK.getAbsolutePath(), ex.getMessage());
            }
        }

        return back;
    }

    // ------------------------------------------------------------------
    public static void aufrufen(WocheTag wt) throws Throwable
    {
        String[] names = createNames(wt);

        try
        {
            Class klasse = Class.forName(names[0] + "." + names[1])
                    .newInstance().getClass();
            Class[] argumentTypen = new Class[0];
            Method getMethod = klasse.getMethod(names[2], argumentTypen);

            getMethod.invoke(klasse, new Object[0]);
        } catch (ClassNotFoundException ex)
        {
            System.out.printf("%nFEHLER: Klasse \"%s.%s\" konnte "
                    + "nicht gefunden werden!%n", names[0], names[1]);
        } catch (NoSuchMethodException ex)
        {
            System.out.printf("%nFEHLER: Methode \"%s.%s.%s\" konnte "
                    + "nicht gefunden werden!%n",
                    names[0], names[1], names[2]);
        } catch (InstantiationException ex)
        {
            System.out.printf("%nFEHLER bei der Instanziierung von \"%s.%s\": %s%n",
                    names[0], names[1], ex.getMessage());
        } catch (IllegalAccessException ex)
        {
            System.out.printf("%nFEHLER: Ungueltiger Zugriff von \"%s.%s.%s\": %s%n",
                    names[0], names[1], names[2], ex.getMessage());
        } catch (InvocationTargetException ex)
        {
            if (ex.getCause() != null)
            {
                throw ex.getCause();
            } else
            {
                System.out.printf("%nFEHLER: Unbekannter Fehler: %s%n",
                        ex.getMessage());

            }
        }
        // wochen(wt);
    }

    // ------------------------------------------------------------------
}
