package Workers;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Controller {
    private static final long delay = 1000L;
    private final String path;
    private final Queue<String> commandQueue;

    public Controller(String path) {
        Path path1 = Path.of(path);
        if (!Files.exists(path1) || !Files.isDirectory(path1)) {
            throw new IllegalArgumentException();
        }
        this.path = path;
        commandQueue = new LinkedList<>();
        Path errorPath = Path.of(this.path + "\\errors");
        try {
            Files.createDirectory(errorPath);
        } catch (IOException e) {
            System.out.println("Temped dir already exists");
        }
    }

    Queue<String> getCommandQueue() {
        return commandQueue;
    }

    void flushQueue() {
        this.commandQueue.clear();
    }

    private void makeError(File file) {
        try {
            Path path1 = Path.of(this.path + "\\errors\\" + file.getName());
            Files.copy(Path.of(file.getAbsolutePath()), path1, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    private void check() {                                                                                              //Check directory for files
        System.out.println("Working " + Thread.currentThread().getName());
        System.out.println("Checking");
        File dir = new File(this.path);
        String fileLines;
        for (File file : dir.listFiles()) {
            try {
                if (file.isDirectory()) {
                    continue;
                }
                fileLines = Files.readAllLines(Path.of(file.getAbsolutePath())).toString();                             //Is control file
                if (fileLines.contains("updatePerson") || fileLines.contains("deletePerson") || fileLines.contains("createPerson")) {
                    commandQueue.add(fileLines);
                    System.out.println(file.getName());
                } else {
                    if(fileLines.contains("name")) {
                        continue;
                    }
                    makeError(file);
                }
                file.delete();
            } catch (Exception e) {
                System.out.println("Exception at file " + file.getName());
                makeError(file);
            }
        }
        System.out.println("Stop checking");
    }

    void doWork() {
        System.out.println("Working " + Thread.currentThread().getName());                                              //Timer works in other thread
        Timer timer = new Timer("Controller timer");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                check();
            }
        };
        timer.schedule(task, 0, delay);
    }

}
