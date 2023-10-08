import Workers.*;
import org.junit.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DispatcherTest {
    @Before
    public void onStart() {
        Path path = Path.of(".\\temped");
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void crudPersons() {
        Path path = Path.of(".\\temped");
        Controller controller = new Controller(path.toString());
        Dispatcher dispatcher = new Dispatcher(path.toString(), controller);
        dispatcher.doWork();
        try {
            Files.createFile(Path.of(path.toString() + "\\file1.txt"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}