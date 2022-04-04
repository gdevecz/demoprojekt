package schoolrecords.Controller;

public enum OptionResult {
    ADD_STUDENT("Az új tanuló elmentve."),
    REMOVE_STUDENT("A tanuló törölve"),
    GRADING("A tanuló érdemjegye elmentve"),
    STUDENT_ALREADY_EXISTS("Ilyen nevű tanuló már létezik!"),
    NO_STUDENT_EXISTS("Nincs rögzített tanuló!"),
    NO_STUDENT_EXIST_WITH_THIS_NAME("Nincs ilyen nevű tanuló!"),
    NO_SUBJECT_FOUND("Nincs ilyen tantárgy!"),
    NO_TUTOR_FOUND("Nincs ilyen nevű tanár!") ;

    private String message;

    OptionResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
