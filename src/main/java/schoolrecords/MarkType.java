package schoolrecords;

public enum MarkType {
    A(5, "jeles"), B(4, "jó"), C(3, "közepes"), D(2, "elégséges"), F(1, "elégtelen");

    public static final String GRADE_CHARACTERS = "ABCDF";
    public static final String GRADE_NUMBERS = "54321";

    private int grade;

    private String evaluation;

    MarkType(int grade, String evaluation) {
        this.grade = grade;
        this.evaluation = evaluation;
    }

    public static MarkType of(String markType) {
        return getMarkTypeFromText(markType);
    }

    public int getGrade() {
        return grade;
    }

    public String getEvaluation() {
        return evaluation;
    }

    private static MarkType getMarkTypeFromText(String text) {
        if (GRADE_NUMBERS.contains(text)) {
            return MarkType.valueOf(Character.toString(GRADE_CHARACTERS.charAt(GRADE_NUMBERS.indexOf(text))));
        }
        return MarkType.valueOf(text);
    }
}
