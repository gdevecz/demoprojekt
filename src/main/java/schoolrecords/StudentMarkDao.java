package schoolrecords;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class StudentMarkDao {

    JdbcTemplate jdbcTemplate;
    SubjectsDao subjectsDao;

    public StudentMarkDao(DataSource dataSource, SubjectsDao subjectsDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.subjectsDao = subjectsDao;
    }

    public void saveStudentMark(Long studentId, Long subjectId, String evaluation) {
        jdbcTemplate.update(
                //language=sql
                "insert into students_marks(student_id, subject_id, evaluation) values(?,?,?)",
                studentId, subjectId, evaluation);
    }

    public List<StudentMark> listAllMarks() {
        return jdbcTemplate.query(
                //language=sql
                "select * from students_marks",
                (rs, rowNum) -> new StudentMark(
                        rs.getLong("student_id"),
                        rs.getLong("subject_id"),
                        rs.getString("evaluation")
                )
        );
    }

    public List<Mark> findMarksByStudentId(Long studentId) {
        return jdbcTemplate.query(
                //language=sql
                "select * from students_marks where student_id = ?",
                (rs, rowNum) -> new Mark(
                        MarkType.valueOf(rs.getString("evaluation")),
                        subjectsDao.findSubjectById(rs.getLong("subject_id"))
                ),
                studentId
        );
    }

    public List<Mark> findMarksBySubjectId(Long subjectId) {
        return jdbcTemplate.query(
                //language=sql
                "select * from students_marks where subject_id = ?",
                (rs, rowNum) -> new Mark(
                        MarkType.valueOf(rs.getString("evaluation")),
                        subjectsDao.findSubjectById(rs.getLong("subject_id"))
                ),
                subjectId
        );
    }

    public List<Mark> findMarksByStudentIdAndSubjectId(Long studentId,Long subjectId){
        return jdbcTemplate.query(
                //language=sql
                "select * from students_marks where student_id = ? AND subject_id = ?",
                (rs, rowNum) -> new Mark(
                        MarkType.valueOf(rs.getString("evaluation")),
                        subjectsDao.findSubjectById(rs.getLong("subject_id"))
                ),
                studentId, subjectId
        );
    }


}