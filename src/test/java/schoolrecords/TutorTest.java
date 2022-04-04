package schoolrecords;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TutorTest {

    @Test
    void testCreateTutor() {
        Tutor tutor = new Tutor(2L, "Kovács István",
                Arrays.asList(new Subject(1L, "matematika"), new Subject(2L, "fizika")));

        assertEquals(2L, tutor.getId());
        assertEquals("Kovács István", tutor.getName());
        assertEquals(Arrays.asList("matematika", "fizika"), tutor.getTaughtSubjects().stream().map(Subject::getName).toList());
    }

    @Test
    void testCreateTutorWithNullName() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Tutor(1L, null));

        assertEquals("Name cannot be null!" , exception.getMessage());
    }

    @Test
    void testCreateTutorWithEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Tutor(1L, ""));

        assertEquals("Name cannot be empty!" , exception.getMessage());
    }

    @Test
    void testTutorTeachingSubjectTrue() {
        Subject subject = new Subject("matematika");
        Subject anotherSubject = new Subject("fizika");
        Tutor tutor = new Tutor(1L, "Kovács István", Arrays.asList(subject, anotherSubject));

        assertEquals(true, tutor.tutorTeachingSubject(subject));
        assertEquals(true, tutor.tutorTeachingSubject(anotherSubject));
    }

    @Test
    void testTutorTeachingSubjectFalse() {
        Subject subject = new Subject("matematika");
        Subject anotherSubject = new Subject("fizika");
        Tutor tutor = new Tutor(1L, "Kovács István", Arrays.asList(subject, anotherSubject));

        assertEquals(false, tutor.tutorTeachingSubject(new Subject("kémia")));
    }
}