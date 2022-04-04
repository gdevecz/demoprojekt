package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Student {

    private Long id;

    private String name;

    private List<Mark> marks = new ArrayList<>();

    public Student(Long id, String name, List<Mark> marks) {
        this.id = id;
        this.name = name;
        this.marks.addAll(marks);
    }

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void grading(Mark mark) {
        marks.add(mark);
    }

    public double calculateAverage() {
        return marks.stream()
                .map(Mark::getMarkType)
                .mapToInt(MarkType::getGrade)
                .average().orElse(0.0);
    }

    public double calculateSubjectAverage(Subject subject) {
        return marks.stream()
                .filter(mark -> subject.equals(mark.getSubject()))
                .map(Mark::getMarkType)
                .mapToInt(MarkType::getGrade)
                .average().orElse(0.0);
    }

    public StudyResultByName getStudyResult() {
        return new StudyResultByName(name, calculateAverage());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" osztályzatai: ")
                .append(marks.stream().map(Mark::toString).collect(Collectors.joining(", ")));
        return sb.toString();
    }
}
