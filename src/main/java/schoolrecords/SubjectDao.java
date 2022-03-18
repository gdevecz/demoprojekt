package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {

    private MysqlDataSource dataSource;

    public SubjectDao(MysqlDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveSubject(String subjectName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     //language=sql
                     "insert into subjects(subject_name) values(?)")
        ) {
            ps.setString(1, subjectName);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new IllegalStateException("Error by insert", sqle);
        }
    }

    public List<Subject> listSubjects() {
        try (Connection connection = dataSource.getConnection();
             Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery("select * from subjects")
        ) {
            return getSubjects(rs);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Error by list", sqle);
        }
    }

    private List<Subject> getSubjects(ResultSet rs) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String subjectName = rs.getString("subject_name");
            subjects.add(new Subject(id, subjectName));
        }
        return subjects;
    }

    public Subject findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     //language=sql
                     "select * from subjects where id = ?"
             )
        ) {
            ps.setLong(1, id);
            return getSubject(ps);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Error by find by id", sqle);
        }
    }

    public Subject findByName(String subjectName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     //language=sql
                     "select * from subjects where subject_name = (?)"
             )
        ) {
            ps.setString(1, subjectName);
            return getSubject(ps);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Error by find by id", sqle);
        }
    }

    private Subject getSubject(PreparedStatement ps) {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Long resultId = rs.getLong("id");
                String resultSubjectName = rs.getString("subject_name");
                return new Subject(resultId, resultSubjectName);
            } else {
                throw new IllegalArgumentException("Cannot find subject.");
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error in result find subject.", sqle);
        }
    }
}
