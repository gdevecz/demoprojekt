package schoolrecords.dbservices;

public class MarkRecord {

    private long studentId;

    private long subjectId;

    private String markType;

    public MarkRecord(long studentId, long subjectId, String markType) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.markType = markType;
    }

    public long getStudentId() {
        return studentId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public String getMarkType() {
        return markType;
    }
}
