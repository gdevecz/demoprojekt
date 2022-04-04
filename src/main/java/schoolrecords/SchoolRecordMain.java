package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import schoolrecords.Console.ConsolService;
import schoolrecords.Controller.SchoolRecordController;
import schoolrecords.repositories.*;

public class SchoolRecordMain {

    public static void main(String[] args) {
        String className = "Demo class";

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        MarksDao marksDao = new MarksDao(dataSource);
        StudentsDao studentsDao = new StudentsDao(dataSource);
        SubjectsDao subjectDao = new SubjectsDao(dataSource);
        TutorsDao tutorsDao = new TutorsDao(dataSource);

        ClassRecordRepository classRecordRepository =
                new ClassRecordRepository(marksDao,
                        studentsDao,
                        subjectDao,
                        tutorsDao);
        SchoolRecordController src = new SchoolRecordController(classRecordRepository, new ConsolService(), className);
        src.menu();
    }
}

