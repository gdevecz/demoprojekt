package schoolrecords;

public class StudyResultByName {

    private String studentName;

    private double studyAverage;

    public StudyResultByName(String studentName, double studyAverage) {
        schoolrecords.Validator.isNameValid(studentName);
        this.studentName = studentName;
        this.studyAverage = studyAverage;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getStudyAverage() {
        return studyAverage;
    }

    @Override
    public String toString() {
        return studentName + " tanulmányi átlaga " + studyAverage;
    }
}
