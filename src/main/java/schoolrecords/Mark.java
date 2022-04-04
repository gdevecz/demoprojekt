package schoolrecords;

public class Mark {

    private MarkType markType;

    private Subject subject;

    private Tutor tutor;

    public Mark(MarkType markType, Subject subject, Tutor tutor) {
        this.markType = markType;
        this.subject = subject;
        this.tutor = tutor;
    }

    public Mark(String markType, Subject subject, Tutor tutor) {
        this(MarkType.of(markType), subject, tutor);
    }

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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return subject.getName() + ": " + markType.getEvaluation() + "(" + markType.getGrade() + ")";
    }
}
