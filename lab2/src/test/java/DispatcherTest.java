import Persons.Subjects;
import Persons.Teacher;
import Workers.*;
import org.junit.*;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

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

    }

    @After
    public void cleanUp() {
        Path path = Path.of(".\\temped_tests\\");
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