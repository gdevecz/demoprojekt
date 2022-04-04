package schoolrecords.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import schoolrecords.*;

import javax.sql.DataSource;
import java.util.List;

public class MarksDao {

    private JdbcTemplate jdbcTemplate;

    public MarksDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveMark(long studentId, Mark mark) {
        jdbcTemplate.update(
                //language=sql
                "insert into marks(student_id, subject_id, tutor_id, mark_type) values(?, ?, ?, ?)",
                studentId, mark.getSubject().getId(), mark.getTutor().getId(), mark.getMarkType().toString()
        );
    }

    public List<Mark> findMarksByStudent(Student student, List<Subject> subjects, List<Tutor> tutors) {
        return jdbcTemplate.query(
                //language=sql
                "select * from marks where student_id = ?",
                (rs, rowNum) -> new Mark(
                        MarkType.of(rs.getString("mark_type")),
                        findSubjectFromListBySubjectId(rs.getLong("subject_id"), subjects),
                        findTutorFromListByTutorId(rs.getLong("tutor_id"), tutors)),
                student.getId()
        );
    }

    private Subject findSubjectFromListBySubjectId(long subjectId, List<Subject> subjects) {
        return subjects.stream()
                .filter(subject -> subject.getId() == subjectId)
                .findFirst().orElseThrow(()-> new IllegalArgumentException("Cannot find subject."));
    }

    private Tutor findTutorFromListByTutorId(long tutorId, List<Tutor> tutors) {
        return tutors.stream()
                .filter(tutor -> tutorId==tutor.getId())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot find tutor."));
    }
}
