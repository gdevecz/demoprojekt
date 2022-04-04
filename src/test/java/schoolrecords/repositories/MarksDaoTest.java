package schoolrecords.repositories;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolrecords.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarksDaoTest {

    MarksDao marksDao;
    List<Student> students;
    List<Tutor> tutors;
    List<Subject> subjects;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        SubjectsDao subjectsDao = new SubjectsDao(dataSource);
        subjects = subjectsDao.loadSubjects();
        tutors = new TutorsDao(dataSource).loadTutorsWithoutSubjects();
        tutors.forEach(tutor -> tutor.setTaughtSubjects(subjectsDao.findSubjectsByTutor(tutor, subjects)));
        students = new StudentsDao(dataSource).loadStudentsWithoutMarks();
        marksDao = new MarksDao(dataSource);
        students.forEach(student -> student.setMarks(marksDao.findMarksByStudent(student, subjects, tutors)));
    }

    @Test
    void saveMarkAndFindMarksByStudent() {
        marksDao.saveMark(students.get(0).getId(), new Mark(MarkType.D, subjects.get(0), tutors.get(0)));

        List<Mark> marks = marksDao.findMarksByStudent(students.get(0), subjects, tutors);

        assertEquals("Fizika", marks.get(2).getSubject().toString());
        assertEquals(MarkType.D, marks.get(2).getMarkType());
        assertEquals("Kovács Áron", marks.get(2).getTutor().getName());
    }


}