package schoolrecords.Console;

import schoolrecords.Controller.OptionResult;
import schoolrecords.MarkType;
import schoolrecords.StudyResultByName;

import java.util.List;
import java.util.Scanner;

public class ConsolService {
    public static final String GRADE_CHARACTERS = "ABCDF";

    public static final String GRADE_NUMBERS = "54321";

    private ConsoleInputValidator validator = new ConsoleInputValidator();

    public int menu(List<String> menuOptions) {
        int max = printOrderedList(menuOptions);
        int option = askNumber("Kérem a választott menüpont számát: ", max);
        return option;
    }

    public void repetitionStudent(String name) {
        System.out.println("A felelésre kijelölt tanuló: " + name);
    }

    public void listStudentNames(String studentNames) {
        System.out.println(studentNames);
    }

    public MarkType askMarkType() {
        String label = "Kérem a felelet érdemjegyét: ";
        System.out.print(label);
        Scanner sc = new Scanner(System.in);
        String mark;
        while (!isTextMark(mark = sc.nextLine())) {
            System.out.println("Érvénytelen osztályzat! " + label);
        }
        return MarkType.of(mark.strip());
    }

    private boolean isTextMark(String text) {
        String tmp = text.strip();
        if (GRADE_CHARACTERS.contains(tmp) || GRADE_NUMBERS.contains(tmp)) {
            return true;
        }
        return false;
    }

    public String findStudentByName() {
        return askName("Kérem a keresendő tanuló nevét: ");
    }

    public String addStudent() {
        return askName("Kérem az új tanuló nevét: ");
    }

    public void waitForStepOver() {
        System.out.println("Tovább: ENTER");
        new Scanner(System.in).nextLine();
    }

    public void printLine(String line) {
        System.out.println(line);
    }

    public String deleteStudent() {
        return askName("Kérem a törölni kívánt tanuló nevét: ");
    }

    private int printOrderedList(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            System.out.printf("%2d. %s\n", i + 1, lines.get(i));
        }
        return lines.size();
    }

    private int askNumber(String label, int max) {
        Scanner sc = new Scanner(System.in);
        String numberString;
        System.out.print(label);
        while (!validator.isTextNumber(numberString = sc.nextLine())) {
            System.out.print("A megadott adat nem szám! " + label);
        }
        return Integer.parseInt(numberString.strip());
    }

    private String askName(String label) {
        Scanner sc = new Scanner(System.in);
        System.out.print(label);
        return sc.nextLine().strip();
    }

    public String askTutorName() {
        return askName("Kérem a tanár nevét: ");
    }

    public String askSubjectName() {
        Scanner sc = new Scanner(System.in);
        return askName("Kérem a tantárgy nevét: ");
    }

    public void printClassAverage(double average) {
        System.out.print("Az osztály tanulmányi átlaga: ");
        System.out.println(Math.round(average * 100) / 100.0);
    }

    public void printClassAverageBySubject(double average, String subjectName) {
        System.out.print("Az osztály tanulmányi átlaga " + subjectName + "tantárgyból:  ");
        System.out.println(Math.round(average * 100) / 100.0);
    }

    public void printStudyResults(List<StudyResultByName> studentAverages) {
        printOrderedList(studentAverages.stream().map(StudyResultByName::toString).toList());
    }
    public void printStudentAverageBySubject(double average, String studentName, String subjectName) {
        System.out.print(studentName + " tanulmányi átlaga " + subjectName + "tantárgyból:  ");
        System.out.println(Math.round(average * 100) / 100.0);

    }

    public void resultOption(OptionResult result) {
        System.out.println(result.getMessage());
    }
}
