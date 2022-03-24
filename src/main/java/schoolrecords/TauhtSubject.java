package schoolrecords;

public class TauhtSubject {

    private Long id;

    private Long teacherId;

    private Long subjectId;

    public TauhtSubject(Long id, Long teacherId, Long subjectId) {
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public TauhtSubject(Long teacherId, Long subjectId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public Long getId() {
        return id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Long getSubjectId() {
        return subjectId;
    }
}
