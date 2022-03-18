package schoolrecords;

public class Subject {


    private Long id;

    private String subjectName;

    public Subject(Long id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Subject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return id + ", " + subjectName;
    }
}
