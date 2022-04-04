package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Tutor {

    private Long id;

    private String name;

    private List<Subject> taughtSubjects = new ArrayList<>();

    public Tutor(Long id, String name, List<Subject> taughtSubjects) {
        isTutorValid(name, taughtSubjects);
        this.id = id;
        this.name = name;
        this.taughtSubjects.addAll(taughtSubjects);
    }

    public Tutor(Long id, String name) {
        this(id, name, new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getTaughtSubjects() {
        return List.copyOf(taughtSubjects);
    }

    public void setTaughtSubjects(List<Subject> taughtSubjects) {
        this.taughtSubjects = taughtSubjects;
    }

    public boolean tutorTeachingSubject(Subject subject) {
        return taughtSubjects.contains(subject);
    }

    private boolean isTutorValid(String name, List<Subject> subjects) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (subjects == null) {
            throw new IllegalArgumentException(name + "'s taught subjects is null!");
        }
        subjects.forEach(this::isSubjectValid);
        return true;
    }

    public boolean isSubjectValid(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Subject is null.");
        }
        return isNameValid(subject.getName());
    }

    public boolean isNameValid(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name cannot be null or empty.");
        }
        return true;
    }
}
