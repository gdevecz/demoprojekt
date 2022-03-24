package schoolrecords;

public class StudentMark {

    private Long id;

    private Long studentId;

    private Long teacherId;

    private String mark;

    public StudentMark(Long id, Long studentId, Long teacherId, String mark) {
        this.id = id;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.mark = mark;
    }

    public StudentMark(Long studentId, Long teacherId, String mark) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public String getMark() {
        return mark;
    }
}
