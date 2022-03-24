package schoolrecords;

public class Mark {

    private Long id;

    private Student student;

    private MarkType markType;

    private Subject subject;

    public Mark(Long id, MarkType markType, Subject subject) {
        this.id = id;
        this.markType = markType;
        this.subject = subject;
    }

    public Mark(MarkType markType, Subject subject) {
        this.markType = markType;
        this.subject = subject;
    }

    public Mark() {
    }

    public Long getId() {
        return id;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public Subject getSubject() {
        return subject;
    }
}
