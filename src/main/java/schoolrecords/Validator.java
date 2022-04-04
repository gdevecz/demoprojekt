package schoolrecords;

import java.util.List;
import java.util.Random;

public class Validator {

    public boolean isClassRecordsValid(String name, Random random, List<Student> students) {
        if (random == null) {
            throw new IllegalArgumentException("Random is null.");
        }
        if (students == null) {
            throw new IllegalArgumentException("Students is null.");
        }
        return isNameValid(name);
    }

    public boolean isSubjectValid(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Subject is null.");
        }
        return isNameValid(subject.getName());
    }

    public boolean isTutorValid(Tutor tutor) {
        if (tutor == null) {
            throw new IllegalArgumentException("Tutor is null.");
        }
        if (tutor.getTaughtSubjects() == null || tutor.getTaughtSubjects().isEmpty()) {
            throw new IllegalArgumentException(tutor.getName() + "'s taughtSubjects cannot be null or empty.");
        }
        return isNameValid(tutor.getName());
    }

    public boolean isStudentValid(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student is null.");
        }
        return isNameValid(student.getName());
    }

    public boolean isNameValid(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name cannot be null or empty.");
        }
        return true;
    }

    public boolean isStudentsExist(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Students is null.");
        }
        return true;
    }
}
