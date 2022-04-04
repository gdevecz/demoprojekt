package schoolrecords.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import schoolrecords.Subject;
import schoolrecords.Tutor;

import javax.sql.DataSource;
import java.util.List;

public class SubjectsDao {

    private JdbcTemplate jdbcTemplate;

    public SubjectsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Subject> loadSubjects() {
        return jdbcTemplate.query(
                //language=sql
                "select * from subjects order by subject_name",
                (rs, rowNum) -> new Subject(
                        rs.getLong("id"),
                        rs.getString("subject_name")));
    }

    public List<Subject> findSubjectsByTutor(Tutor tutor, List<Subject> subjects) {
        return jdbcTemplate.query(
                //language=sql
                "select id from subjects where tutor_id = ?",
                (rs, rowNum) ->
                        findSubjectFromListBySubjectId(rs.getLong("id"), subjects)
                , tutor.getId());
        }

    private Subject findSubjectFromListBySubjectId(long subjectId, List<Subject> subjects) {
        return subjects.stream()
                .filter(subject -> subject.getId() == subjectId)
                .findFirst().orElseThrow(()-> new IllegalArgumentException("Cannot find subject."));
    }
}
