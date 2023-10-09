import Workers.*;
import org.junit.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class DispatcherTest {
    @Before
    public void onStart(){
        Path path = Path.of(".\\temped_tests");
        try {
            Files.createDirectory(path);
        } catch (Exception e) {
            System.out.println("Temped dir already exists");
        }
    }

    @Test
    public void crudPersons() {
        Path path = Path.of(".\\temped_tests");
        Path filePath = Path.of(path.toString() + "\\file1.txt");
        Controller controller = new Controller(path.toString());
        Dispatcher dispatcher = new Dispatcher(path.toString(), controller);
        dispatcher.doWork();
        try {
            Files.createFile(filePath);
            Files.write(filePath, "updatePerson(111, adad, adada)".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @After
    public void cleanUp() {
        Path path = Path.of(".\\temped_tests");
        try {
            for(File file : new File(path.toString()).listFiles()) {
                file.delete();
            }
            Files.delete(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}