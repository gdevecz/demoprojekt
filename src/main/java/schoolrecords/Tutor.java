package schoolrecords;

import java.util.List;
import java.util.Objects;

public class Tutor {

    private String tutorName;

    private List<Subject> taughtSubjects;

    public Tutor(String tutorName, List<Subject> taughtSubjects) {
        this.tutorName = tutorName;
        this.taughtSubjects = taughtSubjects;
    }

    public String getTutorName() {
        return tutorName;
    }

    public List<Subject> getTaughtSubjects() {
        return List.copyOf(taughtSubjects);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(tutorName, tutor.tutorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorName);
    }

    public boolean tutorTeachingSubject(Subject subject) {
        return taughtSubjects.contains(subject);
    }
}
