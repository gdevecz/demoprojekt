package schoolrecords.Console;

import java.util.Arrays;

public class ConsoleInputValidator {

    public boolean isTextNumber(String number) {
        String tmp = number.trim();
        if (isTextEmpty(tmp)) {
            return false;
        }
        int i = 0;
        if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-') {
            i++;
        }
        while (i<tmp.length() && Character.isDigit(tmp.charAt(i++))) ;
        return i == number.length();
    }


    private boolean isTextEmpty(String tmp) {
        if (tmp == null || tmp.isBlank()) {
            return true;
        }
        return false;
    }
}
