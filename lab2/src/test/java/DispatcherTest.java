import com.fasterxml.jackson.databind.ObjectMapper;
import persons.Student;
import persons.Subjects;
import persons.Teacher;
import workers.*;
import org.junit.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class DispatcherTest {
    @Before
    public void onStart(){
        Path path = Path.of(".\\temped_tests");
        try {
            //create working dir
            if(!new File(path.toString()).exists()) {
                Files.createDirectory(path);
            }
            //create new Persons
            Teacher teacher = new Teacher("tName", "tSurname", "tSecondname", 100,
                    "+988888888", Subjects.NODATA, new int[] {8, 17});
            HashMap<Subjects, Double> marks = new HashMap<>();
            marks.put(Subjects.MATH, 1.0);
            marks.put(Subjects.PROGRAMMING, 2.0);
            marks.put(Subjects.PHYSICS, 3.0);
            Student student = new Student("sName", "sSurname", "sSecondName", 9999,
                    "10000000", marks);
            File file1 = new File(".\\file1.txt");
            //Creating test files
            String toWrite = "create(" + student.toString() + ");\n";
            Files.write(file1.toPath(), toWrite.getBytes());
            file1 = new File(".\\file2.txt");
            toWrite = "create(" + teacher.toString() + ");\n";
            Files.write(file1.toPath(), toWrite.getBytes());
        } catch (Exception e) {
            System.out.println("Temped dir already exists");
        }
    }

    @Test
    public void crudPersons() {
        try {
            //Creating Workers
            Controller controller = new Controller(".\\temped_tests\\");
            Dispatcher dispatcher = new Dispatcher(".\\temped_tests\\", controller);
            ObjectMapper mapper = new ObjectMapper();
            //Move test file's
            new File(".\\file1.txt").renameTo(new File(".\\temped_tests\\file1.txt"));
            new File(".\\file2.txt").renameTo(new File(".\\temped_tests\\file2.txt"));
            dispatcher.doWork();
            Thread.sleep(5000);
            assert dispatcher.getService().getIds().length == 2;
            System.out.println("Created Persons: ");
            System.out.println(dispatcher.getService().getAll()[0].toString());
            System.out.println(dispatcher.getService().getAll()[1].toString());
            //Another test file's
            File file2 = new File(".\\file3.txt");
            HashMap<String, String> change = new HashMap<>();
            HashMap<Subjects, Double> marks = new HashMap<>();
            marks.put(Subjects.MATH, -1.0);
            marks.put(Subjects.PROGRAMMING, -2.0);
            marks.put(Subjects.PHYSICS, -3.0);
            change.put("name", "newname");
            change.put("surname", "newsurname");
            change.put("secondname", "newsecondname");
            change.put("birthyear", "1");
            change.put("number", "++++++++");
            change.put("map", mapper.writeValueAsString(marks));
            change.put("hours", "[1, 5]");
            //update student
            String toWrite = "update(" + dispatcher.getService().getIds()[1] + ", " + mapper.writeValueAsString(change) + ");\n";
            Files.write(file2.toPath(), toWrite.getBytes());
            //update teacher
            file2 = new File(".\\file4.txt");
            toWrite = "update(" + dispatcher.getService().getIds()[0] + ", " + mapper.writeValueAsString(change) + ");\n";
            Files.write(file2.toPath(), toWrite.getBytes());
            new File(".\\file3.txt").renameTo(new File(".\\temped_tests\\file3.txt"));
            new File(".\\file4.txt").renameTo(new File(".\\temped_tests\\file4.txt"));
            Thread.sleep(5000);
            System.out.println("Updated Persons: ");
            System.out.println(dispatcher.getService().getAll()[0].toString());
            System.out.println(dispatcher.getService().getAll()[1].toString());
            //delete Person
            toWrite = "delete(" + dispatcher.getService().getIds()[0] + ");\n" + "delete(" +
                    dispatcher.getService().getIds()[1] + ");\n";
            Files.write(new File("file5.txt").toPath(), toWrite.getBytes());
            //new File("file5.txt").renameTo(new File(".\\temped_tests\\file5.txt"));
            Thread.sleep(5000);
            assert dispatcher.getService().getIds().length == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        Path path = Path.of(".\\temped_tests\\");
        try {
            File dir = new File(path.toString());
            for(File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}