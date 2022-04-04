package schoolrecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ClassRecordsTest {

    private ClassRecords classRecords;
    private Tutor tutor= new Tutor(1L,"Nagy Csilla",
            Arrays.asList(new Subject(1L,"földrajz"),
                    new Subject(2L, "matematika"),
                    new Subject(3L, "biológia"),
                    new Subject(4L, "zene"),
                    new Subject(5L, "fizika"),
                    new Subject(6L, "kémia")));


    @BeforeEach
    public void setUp() {
        Student firstStudent = new Student(1L, "Kovács Rita");
        Student secondStudent = new Student(2L, "Nagy Béla");
        Student thirdStudent = new Student(3L, "Varga Márton");
        firstStudent.grading(new Mark(MarkType.A, new Subject("földrajz"), tutor));
        firstStudent.grading(new Mark(MarkType.C, new Subject("matematika"), tutor));
        firstStudent.grading(new Mark(MarkType.D, new Subject("földrajz"), tutor));
        secondStudent.grading(new Mark(MarkType.A, new Subject("biológia"), tutor));
        secondStudent.grading(new Mark(MarkType.C, new Subject("matematika"), tutor));
        secondStudent.grading(new Mark(MarkType.D, new Subject("zene"), tutor));
        thirdStudent.grading(new Mark(MarkType.A, new Subject("fizika"), tutor));
        thirdStudent.grading(new Mark(MarkType.C, new Subject("kémia"), tutor));
        thirdStudent.grading(new Mark(MarkType.D, new Subject("földrajz"), tutor));
        classRecords = new ClassRecords("Fourth Grade A", new Random(5), new ArrayList<>());
        classRecords.addStudent(firstStudent);
        classRecords.addStudent(secondStudent);
        classRecords.addStudent(thirdStudent);
    }

    @Test
    public void testCreate() {
        assertEquals("Fourth Grade A", classRecords.getClassName());
    }

    @Test
    public void testAddStudentAlreadyExists() {
        assertFalse(classRecords.addStudent(new Student(2L, "Nagy Béla")));
    }

    @Test
    public void testAddStudent() {
        assertTrue(classRecords.addStudent(new Student(4L, "Nagy Klára")));
    }

    @Test
    public void testRemoveStudent() {
        assertTrue(classRecords.removeStudent(new Student(2L, "Nagy Béla")));
    }

    @Test
    public void testRemoveStudentDoesNotExists() {
        assertFalse(classRecords.removeStudent(new Student(4L, "Nagy Klára")));
    }

    @Test
    public void testCalculateClassAverage() {
        assertEquals(3.33, classRecords.calculateClassAverage(), 0.02);
    }

    @Test
    public void testFindStudentByName() {
        assertEquals("Kovács Rita", classRecords.findStudentByName("Kovács Rita").get().getName());
    }

    @Test
    public void testRepetition() {
        assertEquals("Varga Márton", classRecords.repetition().get().getName());
    }

    @Test
    public void testListStudyResults() {
        //Given
        List<StudyResultByName> list = classRecords.listStudyResults();
        //Then
        assertEquals("Kovács Rita", list.get(0).getStudentName());
        assertEquals(3.33, list.get(0).getStudyAverage(), 0.02);
        assertEquals(3, list.size());
    }

    @Test
    public void testListStudentNames() {
        assertEquals("Kovács Rita, Nagy Béla, Varga Márton", classRecords.listStudentNames());
    }
}
