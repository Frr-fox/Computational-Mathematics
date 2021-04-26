package Utils;

import java.util.Arrays;
import java.util.Formatter;
import java.util.IllegalFormatException;

public class StringFormatter {

    public static String center(String fmtStr, Formatter fmt, Object obj, int width) {
        String str;
        try {
            Formatter tmp = new Formatter();
            tmp.format(fmtStr, obj);
            str = tmp.toString();
        } catch(IllegalFormatException exc) {
            System.out.println("Неверный запрос формата\n");
            fmt.format("");
            return String.valueOf(fmt);
        }
        int dif = width - str.length();
        if(dif < 0) {
            fmt.format(str);
            return String.valueOf(fmt);
        }
        char[] pad = new char[dif/2];
        Arrays.fill(pad, ' ');
        fmt.format(new String(pad));
        fmt.format(str);
        pad = new char[width-dif/2-str.length()];
        Arrays.fill(pad, ' ');
        fmt.format(new String(pad));
        return String.valueOf(fmt);
    }

    public static String formatStingCenter (String line, int n) {
        return center("%s", new Formatter(), line, n);
    }
}
