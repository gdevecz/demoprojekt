package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private Long id;

    private String teacherName;

    private List<Subject> taughtSubjects = new ArrayList<>();

    public Teacher(Long id, String teacherName) {
        this.id = id;
        this.teacherName = teacherName;
    }

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
        this.taughtSubjects = taughtSubjects;
    }

    public Long getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public List<Subject> getTaughtSubjects() {
        return List.copyOf(taughtSubjects);
    }

    public void addTaughtSubject(Subject subject) {
        taughtSubjects.add(subject);
    }
}
