package schoolrecords;

public class Mark {

    private MarkType markType;

    private Subject subject;

    private Tutor tutor;

    public Mark(MarkType markType, Subject subject, Tutor tutor) {
        Validator.isSubjectAndTutorValid(subject, tutor);
        this.markType = markType;
        this.subject = subject;
        this.tutor = tutor;
    }

    public Mark(String markType, Subject subject, Tutor tutor) {
        Validator.isSubjectAndTutorValid(subject, tutor);
        Validator.isMarkTypeTextValid(markType);
        this.markType = MarkType.of(markType);
        this.subject = subject;
        this.tutor = tutor;

    }

    @Override
    public String toString() {
        return markType.getEvaluation() + "(" + markType.getGrade() + ")";
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
}
