package main.io;

public class IOTools
{

    // -------------------------------------------------------------------
    public static void print(int i, Object... obj)
    {
        String help = "";
        Object str;
        for (int index = 0; index < obj.length - 1; index++)
        {
            if (obj[index] instanceof Integer)
            {
                str = String.format("%,d", obj[index]);
            } else
            {
                str = obj[index].toString();
            }

            String s1 = str.toString().trim();
            char ch = s1.charAt(s1.length() - 1);

            help += str;
            if ((ch != ':') && (ch != '='))
            {
                help += ", ";
            }
        }
        str = obj[obj.length - 1];
        if (str instanceof Integer)
        {
            help += String.format("%,d", (Integer) str);
        } else
        {
            help += str.toString();
        }

        System.out.printf("(%02d) %s%n", i, help);
    }

    // -------------------------------------------------------------------
}
