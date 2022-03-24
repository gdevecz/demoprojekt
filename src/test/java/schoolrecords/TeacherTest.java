package schoolrecords;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void testCreateTeacher() {
        Teacher teacher = new Teacher("John Doe");

        assertEquals(true, teacher.getId() == null);
        assertEquals("John Doe", teacher.getTeacherName());
        assertEquals(true, teacher.getTaughtSubjects().isEmpty());
    }

    @Test
    void testCreateTeacherWithId() {
        Teacher teacher = new Teacher(2L, "John Doe");

        assertEquals(2L, teacher.getId());
        assertEquals("John Doe", teacher.getTeacherName());
        assertEquals(true, teacher.getTaughtSubjects().isEmpty());
    }

    @Test
    void testAddTaughtSubject() {
        Teacher teacher = new Teacher(2L, "John Doe");
        teacher.addTaughtSubject(new Subject("Matematika"));
        teacher.addTaughtSubject(new Subject("Fizika"));

        assertEquals(Arrays.asList("Matematika", "Fizika"), teacher.getTaughtSubjects().stream().map(Subject::getSubjectName).toList());
    }

}