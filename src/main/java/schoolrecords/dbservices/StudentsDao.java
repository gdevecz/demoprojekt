package schoolrecords.dbservices;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class StudentsDao {

    private JdbcTemplate jdbcTemplate;

    public StudentsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long saveStudent(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    //language=sql
                    "insert into students(student_name) values(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, name);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
    public void deleteStudentByName(String name) {
        jdbcTemplate.update(
                //language=sql
                "delete from students where student_name = ?",
                name);
    }

    public List<String> listAllStudentNames() {
        List<String> result = jdbcTemplate.query(
                //language=sql
                "select student_name from students order by student_name",
                (rs, rowNum) -> rs.getString("student_name"));
        if (result.isEmpty()) {
            throw new IllegalStateException("No student in the class!");
        }
        return result;
    }

    public StudentRecord findStudentRecordByName(String name) {
        List<StudentRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from students where student_name = ?",
                (rs, rowNum) -> new StudentRecord(
                        rs.getLong("id"),
                        rs.getString("student_name")),
                name);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student by this name cannot be found! " + name);
        }
        return result.get(0);
    }

    public boolean isStudentExists(String name) {
        List<String> result = jdbcTemplate.query(
                //language=sql
                "select student_name from students where student_name = ?",
                (rs, rowNUm) -> rs.getString("student_name"),
                name);
        return !result.isEmpty();
    }
}
