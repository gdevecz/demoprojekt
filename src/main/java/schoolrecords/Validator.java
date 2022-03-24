package schoolrecords;

import java.util.List;

public class Validator {

    public static boolean isStudentsValid(List<Student> students) {
        if (students.isEmpty()) {
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }
        return true;
    }

    public static boolean isMarkValid(Mark mark) {
        if (mark == null) {
            throw new NullPointerException("Mark must not be null!");
        }
        return true;
    }

    public static boolean isSubjectValid(Subject subject) {
        if ( subject == null) {
            throw new NullPointerException("Subject must not be null.");
        }
        return true;
    }

    public static boolean isStudentValid(Student student) {
        if ( student == null) {
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
        return isValid(name, "name");
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
}
