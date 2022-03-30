package schoolrecords.dbservices;

import schoolrecords.*;

import java.util.ArrayList;
import java.util.List;

public class SchoolRecordsService {

    private StudentsDao studentDao;

    private MarksDao marksDao;

    private SubjectsDao subjectsDao;

    private TutorsDao tutorsDao;

    public SchoolRecordsService(StudentsDao studentDao, MarksDao marksDao, SubjectsDao subjectsDao, TutorsDao tutorsDao) {
        this.studentDao = studentDao;
        this.marksDao = marksDao;
        this.subjectsDao = subjectsDao;
        this.tutorsDao = tutorsDao;
    }

    public boolean saveStudent(Student student) {
        if (!isStudentNameExists(student.getName())) {
            studentDao.saveStudent(student.getName());
            saveStudentSubjects(student);
            return true;
        }
        return false;
    }

    private void saveStudentSubjects(Student student) {
        long studentId = studentDao.findStudentRecordByName(student.getName()).getId();
        for (Mark mark : student.getMarks()) {
            long subjectId = subjectsDao.findSubjectRecordByName(mark.getSubject().getSubjectName()).getId();
            marksDao.saveMark(studentId, subjectId, mark.getMarkType().toString());
        }
    }

    public boolean deleteStudent(Student student) {
        if (isStudentNameExists(student.getName())) {
            studentDao.deleteStudentByName(student.getName());
            return true;
        }
        return false;
    }

    public List<String> listAllStudentNames() {
        return studentDao.listAllStudentNames();
    }

    public Student findStudentByName(String name) {
        StudentRecord studentRecord = studentDao.findStudentRecordByName(name);
        List<MarkRecord> markRecords = marksDao.findMarkRecordsByStudentId(studentRecord.getId());
        List<Mark> marks = getMarksFromMarkRecords(markRecords);
        return new Student(studentRecord.getName(), marks);
    }

    private List<Mark> getMarksFromMarkRecords(List<MarkRecord> markRecords) {
        List<Mark> result = new ArrayList<>();
        for (MarkRecord markRecord : markRecords) {
            MarkType markType = MarkType.valueOf(markRecord.getMarkType());
            SubjectRecord subjectRecord = subjectsDao.findSubjectRecordById(markRecord.getSubjectId());
            Tutor tutor = loadTutorById(subjectRecord.getTutorId());
            result.add(new Mark(markType, new Subject(subjectRecord.getName()), tutor));
        }
        return result;
    }

    private Tutor loadTutorById(long tutorId) {
        TutorRecord tutorRecord = tutorsDao.findTutorRecordById(tutorId);
        List<SubjectRecord> subjectRecords = subjectsDao.listSubjectRecordsByTutorId(tutorRecord.getId());
        List<Subject> taughtSubjects = subjectRecords.stream().map(subjectRecord -> new Subject(subjectRecord.getName())).toList();
        return new Tutor(tutorRecord.getName(), taughtSubjects);
    }

    public double calculateClassAverage() {
        List<Student> students = listAllStudents();
        Validator.isClassAverageCalculationValid(students);
        return roundToDoubleWithTwoDecimalPlaces(students.stream()
                .mapToDouble(Student::calculateAverage)
                .average().orElse(0.0));
    }

    public double calculateClassAverageBySubjectName(String subjectName) {
        List<Student> students = listAllStudents();
        Validator.isClassAverageCalculationValid(students);
        return roundToDoubleWithTwoDecimalPlaces(students.stream()
                .mapToDouble(student -> student.calculateSubjectAverage(new Subject(subjectName)))
                .filter(avg -> avg != 0.0)
                .average().orElseThrow(() -> new ArithmeticException("Error in calculateClassAverageBySubject: " + subjectName)));
    }

    public Tutor findTutorByName(String tutorName) {
        TutorRecord tutorRecord = tutorsDao.findTutorRecordByName(tutorName);
        return loadTutorById(tutorRecord.getId());
    }

    public List<Student> listAllStudents() {
        List<String> studentNames = listAllStudentNames();
        List<Student> students = new ArrayList<>();
        studentNames.forEach(studentName -> students.add(findStudentByName(studentName)));
        return students;
    }

    public void gradingStudent(MarkType markType, String studentName, String subjectName) {
        StudentRecord studentRecord = studentDao.findStudentRecordByName(studentName);
        SubjectRecord subjectRecord = subjectsDao.findSubjectRecordByName(subjectName);
        marksDao.saveMark(studentRecord.getId(), subjectRecord.getId(), markType.toString());
    }

    public List<StudyResultByName> listStudyResults() {
        return listAllStudents().stream().map(student -> new StudyResultByName(student.getName(), student.calculateAverage())).toList();
    }

    public StudyResultByName findStudyResultByName(String name) {
        Student student = findStudentByName(name);
        return new StudyResultByName(student.getName(), student.calculateAverage());
    }

    public double studentAverageBySubject(String studentName, String subjectname) {
        Student student = findStudentByName(studentName);
        Subject subject = findSubjectByName(subjectname);
        return student.calculateSubjectAverage(subject);
    }

    private Subject findSubjectByName(String subjectname) {
        SubjectRecord subjectRecord = subjectsDao.findSubjectRecordByName(subjectname);
        return new Subject(subjectRecord.getName());
    }

    private boolean isStudentNameExists(String name) {
        return studentDao.isStudentExists(name);
    }

    private double roundToDoubleWithTwoDecimalPlaces(double number) {
        return (Math.round(number * 100)) / 100.0;
    }
}
