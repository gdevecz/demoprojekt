package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private Long id;

    private String studentName;

    private List<Mark> marks = new ArrayList<>();

    public Student(Long id, String studentName) {
        this.id = id;
        this.studentName = studentName;
    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void grading(Mark mark) {
        marks.add(mark);
    }

    public double calculateAverage() {
        return marks.stream()
                .mapToInt(m -> m.getMarkType().getGrade())
                .average()
                .orElseThrow(() -> new IllegalStateException("No grades."));
    }

    public double calculateSubjectAverage(Subject subject) {
        return marks.stream()
                .filter(m -> subject.getSubjectName().equals(m.getSubject().getSubjectName()))
                .mapToInt(m -> m.getMarkType().getGrade())
                .average()
                .orElseThrow(() -> new IllegalStateException("No grades from " + subject.getSubjectName() + "."));
    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }
}

