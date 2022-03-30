package schoolrecords.dbservice;

import java.util.Objects;

public class SubjectRecord {

    private long id;

    private String name;

    private long tutorId;

    public SubjectRecord(long id, String name, long tutorId) {
        this.id = id;
        this.name = name;
        this.tutorId = tutorId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTutorId() {
        return tutorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectRecord that = (SubjectRecord) o;
        return tutorId == that.tutorId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tutorId);
    }
}
