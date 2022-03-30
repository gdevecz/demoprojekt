package schoolrecords;

import java.util.Arrays;
import java.util.List;

public class Validator {

    public static boolean isStudentsValid(List<Student> students) {
        if (students.isEmpty()) {
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }
        return true;
    }

    public static boolean isMarkValid(schoolrecords.Mark mark) {
        if (mark == null) {
            throw new NullPointerException("Mark must not be null!");
        }
        return true;
    }

    public static boolean isSubjectValid(Subject subject) {
        if (subject == null) {
            throw new NullPointerException("Subject must not be null.");
        }
        return true;
    }

    public static boolean isStudentValid(Student student) {
        if (student == null) {
            throw new NullPointerException("Student can not be null.");
        }
        return true;
    }

    public static boolean isStudentNameValid(String name) {
        if (name == null) {
            throw new NullPointerException("Student name must not be null.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        return true;
    }

    public static boolean isNameValid(String name) {
        if (name == null) {
            throw new NullPointerException("The name must not be null.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("The name must not be empty.");
        }
        return true;
    }

    public static boolean isValid(String string, String validationSubject) {
        if (string == null) {
            throw new NullPointerException("The " + validationSubject + " must not be null.");
        }
        if (string.isBlank()) {
            throw new IllegalArgumentException("The " + validationSubject + " must not be empty.");
        }
        return true;
    }

    public static boolean isSubjectAndTutorValid(Subject subject, Tutor tutor) {
        if (subject == null || tutor == null) {
            throw new NullPointerException("Both subject and tutor must be provided!");
        }
        return true;
    }

    public static boolean isMarkTypeTextValid(String markName) {
        return Arrays.asList(MarkType.values()).contains(markName);
    }

    public static boolean isClassAverageCalculationValid(List<Student> students) {
        if (students.stream()
                .flatMap(student -> student.getMarks().stream().map(Mark::getMarkType)).findAny().isEmpty()) {
            throw new ArithmeticException("No marks present, average calculation aborted!");
        }
        return true;
    }

    public static boolean isClassAverageBySubjectCalculationValid(Subject subject, List<Student> students) {
        return isClassAverageCalculationValid(students.stream()
                .filter(student -> student.getMarks().stream()
                        .anyMatch(mark -> subject.equals(mark.getSubject())))
                .toList());
    }

    public static boolean isStudentsExist(List<Student> students) {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to search!");
        }
        return true;
    }

    public static boolean isStudentsExistForRepetition(List<Student> students) {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to select for repetition!");
        }
        return true;
    }

    public static boolean isOptionValid(int option, int max) {
        if (option < 1 || option > max) {
            throw new IllegalArgumentException("Invalid option: " + option);
        }
        return true;
    }
}
