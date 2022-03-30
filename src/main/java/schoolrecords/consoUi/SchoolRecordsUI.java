package schoolrecords.consoUi;

import java.util.List;
import java.util.Scanner;

public class SchoolRecordsUI {

    public void printText(String text) {
        System.out.println(text);
    }

    public void printOrderedList(String label, List<String> menuOptions) {
        System.out.println('\n' + label);
        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.printf("%2d. %s\n", i + 1, menuOptions.get(i));
        }
    }

    public String requestText(String label) {
        System.out.print(label);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int requestNumber(String label, int max) {
        String numberString;
        Scanner sc = new Scanner(System.in);

        System.out.print(label);
        while (!isStringValidInteger(numberString = sc.nextLine())) {
            System.out.print("A megadott adat nem szám, kérem adja meg újra!\n" + label);
        }
        return Integer.parseInt(numberString.strip());
    }

    private boolean isStringValidInteger(String source) {
        if (source.strip().isBlank()) {
            return false;
        }
        String text = source.strip();
        int i = 0;
        if (source.charAt(0) == '-' || source.charAt(0) == '+') {
            i++;
        }
        for (; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void waitForEnter() {
        System.out.println("Tovább: ENTER");
        new Scanner(System.in).nextLine();
    }
}
