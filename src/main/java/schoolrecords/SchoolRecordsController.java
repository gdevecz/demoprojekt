package schoolrecords;

import schoolrecords.consoUi.SchoolRecordsUI;
import schoolrecords.dbservice.SchoolRecordsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SchoolRecordsController {

    private SchoolRecordsUI schoolRecordsUI;

    private SchoolRecordsService schoolRecordsService;

    public SchoolRecordsController(SchoolRecordsUI schoolRecordsUI, SchoolRecordsService schoolRecordsService) {
        this.schoolRecordsUI = schoolRecordsUI;
        this.schoolRecordsService = schoolRecordsService;
    }

    public void menu(String className) {
        int option;
        int max = getListOfOptions().size();
        do {
            schoolRecordsUI.printOrderedList(className + " menüponjai:", getListOfOptions());
            option = schoolRecordsUI.requestNumber("Kérem válasszon a menüpontok közül: ", max);
            Validator.isOptionValid(option, max);
            executeOption(option);
        } while (option != max);
    }

    private void executeOption(int option) {
        switch (option) {
            case 1:
                listStudentNames();
                return;
            case 2:
                findStudentByName();
                return;
            case 3:
                addStudent();
                return;
            case 4:
                deleteStudent();
                return;
            case 5:
                repetition();
                return;
            case 6:
                calculateClassAverage();
                return;
            case 7:
                calculateClassAverageBySubject();
                return;
            case 8:
                listStudyResults();
                return;
            case 9:
                studentStudyResult();
                return;
            case 10:
                studentStudyResultBySubject();
        }
    }

    private void listStudentNames() {
        schoolRecordsUI.printOrderedList("List of students: ", schoolRecordsService.listAllStudentNames());
        schoolRecordsUI.waitForEnter();
    }

    private void findStudentByName() {
        String name = schoolRecordsUI.requestText("Kérem a keresendő tanuló nevét: ");
        Student student = schoolRecordsService.findStudentByName(name);
        schoolRecordsUI.printText(student.toString());
        schoolRecordsUI.waitForEnter();
    }

    private void addStudent() {
        String name = schoolRecordsUI.requestText("Kérem az új tanuló nevét: ");
        schoolRecordsService.saveStudent(new Student(name));
        schoolRecordsUI.printText(name + " tanuló rögzítve.");
        schoolRecordsUI.waitForEnter();
    }

    private void deleteStudent() {
        String name = schoolRecordsUI.requestText("Kérem a törlendő tanuló nevét: ");
        schoolRecordsService.deleteStudent(new Student(name));
        schoolRecordsUI.printText(name + " tanuló törölve.");
        schoolRecordsUI.waitForEnter();

    }

    private void repetition() {
        List<String> studentNames = schoolRecordsService.listAllStudentNames();
        String studentName = studentNames.get(new Random().nextInt(studentNames.size()));
        schoolRecordsUI.printText("A feleltetett tanuló neve: " + studentName);
        MarkType markType = MarkType.of(schoolRecordsUI.requestText("Kérem a felelet érdemjegyét: "));
        String subjectName = schoolRecordsUI.requestText("A számonkért tantágy: ");
        String tutorName = schoolRecordsUI.requestText("Számonkérő tanár neve: ");
        schoolRecordsService.findTutorByName(tutorName);
        schoolRecordsService.gradingStudent(markType, studentName, subjectName);
        schoolRecordsUI.printText(studentName + " " + subjectName + " " + markType.getGrade() + " érdemjegye elmentve.");
        schoolRecordsUI.waitForEnter();
    }

    private void calculateClassAverage() {
        double avg = schoolRecordsService.calculateClassAverage();
        schoolRecordsUI.printText("Az osztály tanulmányi átlaga: " + avg);
        schoolRecordsUI.waitForEnter();
    }

    private void calculateClassAverageBySubject() {
        String subjectName = schoolRecordsUI.requestText("Kérem a keresett tantárgy nevét: ");
        double subjectAvg = schoolRecordsService.calculateClassAverageBySubjectName(subjectName);
        schoolRecordsUI.printText("Az osztály átlaga " + subjectName + " tantárgyból: " + subjectAvg);
        schoolRecordsUI.waitForEnter();
    }

    private void listStudyResults() {
        List<StudyResultByName> studentAverages = schoolRecordsService.listStudyResults();
        schoolRecordsUI.printOrderedList("A diákok átlaga:", studentAverages.stream()
                .map(StudyResultByName::toString)
                .toList());
        schoolRecordsUI.waitForEnter();
    }

    private void studentStudyResult() {
        String name = schoolRecordsUI.requestText("Kérem a keresendő tanuló nevét: ");
        StudyResultByName studyResult = schoolRecordsService.findStudyResultByName(name);
        schoolRecordsUI.printText(studyResult.toString());
        schoolRecordsUI.waitForEnter();
    }

    private void studentStudyResultBySubject() {
        String studentName = schoolRecordsUI.requestText("Kérem a keresendő tanuló nevét: ");
        String subjectname = schoolRecordsUI.requestText("Kérem a keresendő tanrárgy nevét: ");
        double subjectAverage = schoolRecordsService.studentAverageBySubject(studentName, subjectname);
        schoolRecordsUI.printText(studentName + " tanulmányi átlaga " + subjectname + " tárgyból: " + subjectAverage);
        schoolRecordsUI.waitForEnter();
    }

    private List<String> getListOfOptions() {
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("Diákok nevének listázása");
        menuOptions.add("Diák név alapján keresése");
        menuOptions.add("Diák létrehozása");
        menuOptions.add("Diák név alapján törlése");
        menuOptions.add("Diák feleltetése");
        menuOptions.add("Osztályátlag kiszámolása");
        menuOptions.add("Tantárgyi átlag kiszámolása");
        menuOptions.add("Diákok átlagának megjelenítése");
        menuOptions.add("Diák átlagának kiírása");
        menuOptions.add("Diák tantárgyhoz tartozó átlagának kiírása");
        menuOptions.add("Kilépés");
        return menuOptions;
    }
}
