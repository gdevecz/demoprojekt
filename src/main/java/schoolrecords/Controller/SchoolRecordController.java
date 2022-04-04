package schoolrecords.Controller;

import schoolrecords.*;
import schoolrecords.Console.ConsolService;
import schoolrecords.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SchoolRecordController {

    private ConsolService consolService;

    private ClassRecordRepository classRecordRepository;

    private ClassRecords classRecords;

    private List<Tutor> tutors;

    private List<Subject> subjects;

    public SchoolRecordController(
            ClassRecordRepository classRecordRepository,
            ConsolService consolService,
            String className) {
        this.consolService = consolService;
        this.classRecordRepository = classRecordRepository;
        subjects = classRecordRepository.loadSubjects();
        tutors = classRecordRepository.loadTutors(subjects);
        List<Student> students = classRecordRepository.loadStudents(tutors, subjects);
        this.classRecords = new ClassRecords(className, new Random(), students);
    }

    public void menu() {
        int option, max;
        List<String> options = listMenu();
        max = options.size();
        do {
            option = consolService.menu(options);
            executeOption(option);
        } while (option != max);
    }

    private void repetition() {
        Student student = findStudentForRepetition();
        consolService.repetitionStudent(student.getName());
        MarkType markType = consolService.askMarkType();
        Subject subject = getSubjectFromConsole();
        Tutor tutor = getTutorFromConsole();
        Mark mark = new Mark(markType, subject, tutor);
        classRecordRepository.gradingStudent(student, mark);
        student.grading(mark);
        consolService.resultOption(OptionResult.GRADING);
        consolService.waitForStepOver();
    }

    private Student findStudentForRepetition() {
        List<Student> students = classRecords.getStudents();
        return students.get(new Random().nextInt(students.size()));
    }

    private Subject getSubjectFromConsole() {
        Optional<Subject> subject = Optional.empty();
        while (subject.isEmpty()) {
            String subjectName = consolService.askSubjectName();
            subject = findSubjectByName(subjectName);
            if (subject.isEmpty()) {
                consolService.resultOption(OptionResult.NO_SUBJECT_FOUND);
            }
        }
        return subject.get();
    }

    private Optional<Subject> findSubjectByName(String name) {
        for (Subject subject : subjects) {
            if (name.equals(subject.getName())) {
                return Optional.of(subject);
            }
        }
        return Optional.empty();
    }

    private void listStudentNames() {
        String studentNames = classRecords.listStudentNames();
        if (studentNames.isBlank()) {
            consolService.resultOption(OptionResult.NO_STUDENT_EXISTS);
        }
        consolService.listStudentNames(studentNames);
        consolService.waitForStepOver();
    }

    private void findStudentByName() {
        String name = consolService.findStudentByName();
        Optional<Student> student = classRecords.findStudentByName(name);
        if (student.isEmpty()) {
            consolService.resultOption(OptionResult.NO_STUDENT_EXIST_WITH_THIS_NAME);
            return;
        }
        consolService.printLine(student.get().toString());
        consolService.waitForStepOver();
    }

    private void addStudent() {
        String name = consolService.addStudent();
        Optional<Student> tmp = classRecords.findStudentByName(name);
        if (isStudentExist(tmp)) {
            consolService.resultOption(OptionResult.STUDENT_ALREADY_EXISTS);
            return;
        }
        long id = classRecordRepository.saveStudent(name);
        Student student = new Student(id, name);
        classRecords.addStudent(student);
        consolService.resultOption(OptionResult.ADD_STUDENT);
        consolService.waitForStepOver();
    }

    private void deleteStudent() {
        String name = consolService.deleteStudent();
        Optional<Student> student = classRecords.findStudentByName(name);
        if (!isStudentExist(student)) {
            consolService.resultOption(OptionResult.NO_STUDENT_EXIST_WITH_THIS_NAME);
            return;
        }
        classRecordRepository.deleteStudent(student.get());
        classRecords.removeStudent(student.get());
        consolService.resultOption(OptionResult.REMOVE_STUDENT);
        consolService.waitForStepOver();
    }

    private boolean isStudentExist(Optional<Student> student) {
        return student.isEmpty();
    }

    private Tutor getTutorFromConsole() {
        Optional<Tutor> tutor = Optional.empty();
        while (tutor.isEmpty()) {
            String tutorName = consolService.askTutorName();
            tutor = findTutorByName(tutorName);
            if (tutor.isEmpty()) {
                consolService.resultOption(OptionResult.NO_TUTOR_FOUND);
            }
        }
        return tutor.get();
    }

    private void calculateClassAverage() {
        double avg = classRecords.calculateClassAverage();
        consolService.printClassAverage(avg);
        consolService.waitForStepOver();
    }

    private void calculateClassAverageBySubject() {
        Subject subject = getSubjectFromConsole();
        double subjectAvg = classRecords.calculateClassAverageBySubject(subject);
        consolService.printClassAverageBySubject(subjectAvg, subject.getName());
        consolService.waitForStepOver();
    }

    private void listStudyResults() {
        List<StudyResultByName> studentAverages = classRecords.listStudyResults();
        consolService.printStudyResults(studentAverages);
        consolService.waitForStepOver();
    }

    private void studentStudyResult() {
        String studentName = consolService.findStudentByName();
        Optional<Student> student = classRecords.findStudentByName(studentName);
        if (student.isEmpty()) {
            consolService.resultOption(OptionResult.NO_STUDENT_EXIST_WITH_THIS_NAME);
        }
        consolService.printLine(student.get().getStudyResult().toString());
        consolService.waitForStepOver();
    }

    private void studentStudyResultBySubject() {
        String studentName = consolService.findStudentByName();
        Optional<Student> student = Optional.empty();
        while (student.isEmpty()) {
            student = classRecords.findStudentByName(studentName);
            if (student.isEmpty()) {
                consolService.resultOption(OptionResult.NO_STUDENT_EXIST_WITH_THIS_NAME);
            }
        }
        Subject subject = getSubjectFromConsole();
        consolService.printStudentAverageBySubject(student.get().calculateAverage(), studentName, subject.getName());
        consolService.waitForStepOver();
    }

    private Optional<Tutor> findTutorByName(String name) {
        for (Tutor tutor : tutors) {
            if (name.equals(tutor.getName())) {
                return Optional.of(tutor);
            }
        }
        return Optional.empty();
    }

    private List<String> listMenu() {
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
}
