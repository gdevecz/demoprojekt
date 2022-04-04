package schoolrecords;

public class StudyResultByName {

    private String studentName;

    private double studyAverage;

    public StudyResultByName(String studentName, double studyAverage) {
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
        StringBuilder sb = new StringBuilder();
        return sb.append(studentName)
                .append(" tanulmányi átlaga ")
                .append((Math.round(studyAverage * 100)) / 100.0)
                .toString();
    }
}
