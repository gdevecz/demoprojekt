package schoolrecords.repositories;

import schoolrecords.Mark;
import schoolrecords.Student;
import schoolrecords.Subject;
import schoolrecords.Tutor;

import java.util.List;

public class ClassRecordRepository {

    private MarksDao marksDao;
    private StudentsDao studentsDao;
    private SubjectsDao subjectsDao;
    private TutorsDao tutorsDao;

    public ClassRecordRepository(MarksDao marksDao, StudentsDao studentsDao, SubjectsDao subjectsDao, TutorsDao tutorsDao) {
        this.marksDao = marksDao;
        this.studentsDao = studentsDao;
        this.subjectsDao = subjectsDao;
        this.tutorsDao = tutorsDao;
    }

    public List<Subject> loadSubjects() {
        return subjectsDao.loadSubjects();
    }

    public List<Tutor> loadTutors(List<Subject> subjects) {
        List<Tutor> tutors = loadTutorsWithoutSubjects();
        tutors.forEach(tutor -> tutor.setTaughtSubjects(subjectsDao.findSubjectsByTutor(tutor, subjects)));
        return tutors;
    }

    public List<Student> loadStudents(List<Tutor> tutors, List<Subject> subjects) {
        List<Student> students = studentsDao.loadStudentsWithoutMarks();
        students.forEach(student -> student.setMarks(marksDao.findMarksByStudent(student, subjects, tutors)));
        return students;
    }

    public void saveMark(Student student, Mark mark) {
        marksDao.saveMark(student.getId(), mark);
    }

    private List<Tutor> loadTutorsWithoutSubjects() {
        return tutorsDao.loadTutorsWithoutSubjects();
    }

    public long saveStudent(String name) {
        return studentsDao.saveNewStudent(name);
    }

    public void deleteStudent(Student student) {
        studentsDao.deleteStudentById(student.getId());
    }

    public void gradingStudent(Student student, Mark mark) {
        marksDao.saveMark(student.getId(), mark);
    }
}

