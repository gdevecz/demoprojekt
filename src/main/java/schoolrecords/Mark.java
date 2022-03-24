package schoolrecords;

public class Mark {

    private MarkType markType;

    private Subject subject;

    public Mark(MarkType markType, Subject subject) {
        this.markType = markType;
        this.subject = subject;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public Subject getSubject() {
        return subject;
    }
}
