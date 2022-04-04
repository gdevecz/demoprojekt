package schoolrecords.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import schoolrecords.Student;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class StudentsDao {

    private JdbcTemplate jdbcTemplate;

    public StudentsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteStudentById(long id) {
        jdbcTemplate.update(
                //language=sql
                "delete from students where id = ?",
                id);
    }

    public List<Student> loadStudentsWithoutMarks() {
        return jdbcTemplate.query(
                //language=sql
                "select * from students order by student_name",
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("student_name")));
    }

    public long saveNewStudent(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    //language=sql
                    "insert into students(student_name) values(?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
