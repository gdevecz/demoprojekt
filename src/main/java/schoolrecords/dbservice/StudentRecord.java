package schoolrecords.dbservice;

public class StudentRecord {

    private long id;

    private String name;

    public StudentRecord(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
