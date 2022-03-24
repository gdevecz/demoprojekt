package schoolrecords;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class StudentDao {

    JdbcTemplate jdbcTemplate;

    public StudentDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long saveStudent(String studentName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                    PreparedStatement ps = con.prepareStatement(
                                            //language=sql
                                            "insert into students(student_name) values(?)",
                                            Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, studentName);
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public List<Student> loadAllStudents() {
        return jdbcTemplate.query(
                //language=sql
                "select * from students order by student_name",
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("student_name")
                )
        );
    }

    public Student findStudentById(Long id) {
        return jdbcTemplate.queryForObject(
                //language=sql
                "select * from students where id = ?",
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("student_name")
                ),
                id
        );
    }

    public List<Student> findStudentByName(String studentName) {
        return jdbcTemplate.query(
                //language=sql
                "select * from students where student_name = ?",
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("student_name")
                ),
                studentName
        );
    }

    public void removeStudent(Long studentId) {
        jdbcTemplate.update(
                //language=sql
                "delete from students where id = ?",
                studentId);
    }
}


