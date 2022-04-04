package schoolrecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {

    Student student;
    Subject subject;
    Subject anotherSubject;
    Tutor tutor;
    List<Mark> marks;

    @BeforeEach
    void init() {
        subject = new Subject(1L, "matematika");
        anotherSubject = new Subject(2L, "fizika");
        Tutor tutor = new Tutor(1L, "Kovács István", Arrays.asList(subject, anotherSubject));
        Mark mathMarks = new Mark(MarkType.D, subject, tutor);
        Mark anotherMathMarks = new Mark(MarkType.B, subject, tutor);
        Mark physicsMarks = new Mark(MarkType.C, anotherSubject, tutor);
        marks = Arrays.asList(mathMarks, physicsMarks, anotherMathMarks);

        student = new Student(1L, "Pintér Elemér", marks);
    }

    @Test
    void testCreateStudentWithMarks() {
        assertEquals(1L, student.getId());
        assertEquals("Pintér Elemér", student.getName());
        assertEquals(Arrays.asList(MarkType.D, MarkType.C, MarkType.B), student.getMarks().stream().map(Mark::getMarkType).toList());
    }

    @Test
    void testCreateStudentWithName() {
        Student student = new Student(2L, "Pintér Dezső");

        assertEquals(2L, student.getId());
        assertEquals("Pintér Dezső", student.getName());
        assertEquals(true, student.getMarks().isEmpty());

    }

    @Test
    void testGrading() {
        student.grading(new Mark(MarkType.A, anotherSubject, tutor));

        assertEquals(MarkType.A, student.getMarks().get(3).getMarkType());
        assertEquals("fizika", student.getMarks().get(3).getSubject().getName());
    }


    @Test
    void testCalculateAverage() {
        student.grading(new Mark(MarkType.B, anotherSubject, tutor));
        student.grading(new Mark(MarkType.C, anotherSubject, tutor));
        student.grading(new Mark(MarkType.C, anotherSubject, tutor));
        assertEquals(3.17, student.calculateAverage(), 0.01);
    }

    @Test
    void testCalculateSubjectAverage() {
        student.grading(new Mark(MarkType.B, subject, tutor));

        assertEquals(3.33, student.calculateSubjectAverage(subject), 0.01);
        assertEquals(3.0, student.calculateSubjectAverage(anotherSubject), 0.01);
    }

    @Test
    void testGetStudyResult() {
        student.grading(new Mark(MarkType.B, anotherSubject, tutor));
        student.grading(new Mark(MarkType.C, anotherSubject, tutor));
        student.grading(new Mark(MarkType.C, anotherSubject, tutor));
        StudyResultByName studyResultByName = student.getStudyResult();

        assertEquals("Pintér Elemér tanulmányi átlaga 3.17", studyResultByName.toString());
    }

    @Test
    void testToString() {

        assertEquals(
                "Pintér Elemér osztályzatai: matematika: elégséges(2), fizika: közepes(3), matematika: jó(4)",
                student.toString());
    }
}