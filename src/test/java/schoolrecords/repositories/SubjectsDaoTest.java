package schoolrecords.repositories;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolrecords.Subject;
import schoolrecords.Tutor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectsDaoTest {

    SubjectsDao subjectsDao;
    List<Tutor> tutors;
    List<Subject> subjects;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        subjectsDao = new SubjectsDao(dataSource);
        TutorsDao tutorsDao = new TutorsDao(dataSource);
        tutors = tutorsDao.loadTutorsWithoutSubjects();
        subjects = subjectsDao.loadSubjects();
        tutors.forEach(tutor -> tutor.setTaughtSubjects(subjectsDao.findSubjectsByTutor(tutor, subjects)));
    }

    @Test
    void testLoadAllSubjects() {
        List<Subject> subjects = subjectsDao.loadSubjects();

        System.out.println(subjects);
        assertEquals(Arrays.asList("Fizika", "Földrajz", "Informatika", "Irodalom", "Matematika", "Nyelvtan", "Testnevelés", "Történelem"),
                subjects.stream().map(Subject::toString).toList());
    }

    @Test
    void testFindSubjectsByTutor() {
        Tutor tutor = tutors.get(3);

        List<Subject> taughtSubjects = subjectsDao.findSubjectsByTutor(tutor, subjects);

        assertEquals(Arrays.asList("Matematika", "Fizika"), tutor.getTaughtSubjects().stream().map(Subject::toString).toList());
    }
}