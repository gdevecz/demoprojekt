package schoolrecords;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class ClassRecords {

    private String className;

    private List<Student> students;

    private Random random;

    private Validator validator;

    public ClassRecords(String className, Random random, List<Student> students) {
        validator = new Validator();
        validator.isClassRecordsValid(className, random, students);
        this.className = className;
        this.random = random;
        this.students = students;
    }

    public ClassRecords(String className, List<Student> students) {
        this(className, new Random(), students);
    }

    public boolean addStudent(Student student) {
        validator.isStudentValid(student);
        Optional<Student> tmp = findStudentByName(student.getName());
        if (tmp.isPresent()) {
            return false;
        }
        return students.add(student);
    }

    public boolean removeStudent(Student student) {
        validator.isStudentValid(student);
        Optional<Student> tmp = findStudentByName(student.getName());
        return tmp.map(s -> students.remove(s)).orElse(false);
    }

    public double calculateClassAverage() {
        return students.stream()
                .flatMap(student -> student.getMarks().stream())
                .mapToInt(mark -> mark.getMarkType().getGrade())
                .average().orElse(0.0);
    }

    public double calculateClassAverageBySubject(Subject subject) {
        validator.isSubjectValid(subject);
        return students.stream()
                .flatMap(student -> student.getMarks().stream())
                .filter(mark -> subject.equals(mark.getSubject()))
                .mapToInt(mark -> mark.getMarkType().getGrade())
                .average().orElse(0.0);
    }

    public Optional<Student> repetition() {
        if (students.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(students.get(random.nextInt(students.size())));
    }

    public List<StudyResultByName> listStudyResults() {
        return students.stream().map(Student::getStudyResult).toList();
    }

    public String listStudentNames() {
        return students.stream().map(Student::getName).sorted().collect(Collectors.joining(", "));
    }

    public String getClassName() {
        return className;
    }

    public List<Student> getStudents() {
        return students;
    }


    public Optional<Student> findStudentByName(String name) {
        for (Student student : students) {
            if (name.equals(student.getName())) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    private boolean isTutorValid(Tutor tutor) {
        if (tutor == null) {
            throw new IllegalArgumentException("Tutor is null.");
        }
        if (tutor.getTaughtSubjects() == null || tutor.getTaughtSubjects().isEmpty()) {
            throw new IllegalArgumentException(tutor.getName() + "'s taughtSubjects cannot be null or empty.");
        }
        return isNameValid(tutor.getName());
    }

    private boolean isNameValid(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name cannot be null or empty.");
        }
        return true;
    }

}
