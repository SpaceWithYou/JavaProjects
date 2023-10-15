package workers;
import persons.Person;
import services.PeopleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class Dispatcher {
    private static final long delay = 1000L;
    private final PeopleService service;
    private final Controller controller;

    public Dispatcher(String path, Controller controller) {
        service = new PeopleService(path);
        this.controller = controller;
    }

    private boolean check() {                                                                                           //Check for new commands
        return !controller.getCommandQueue().isEmpty();
    }

    /**
     * <p>Commands:</p>
     * <p>
     * update(String id, String... params), HashMap in JSON format<br>
     * delete(String id), <br>
     * create(Person person) in JSON format
     * </p>
     * <p>In following form: <br> command1; <br> command2; <br>...</p>
     **/
    private void decodeCommands(Queue<String> queue) {
        int index;
        String[] lines;
        try {
            for(String commands : queue) {
                lines = commands.split(";, ");
                for(String line: lines) {
                    if(line.contains("update(")) {
                        index = line.indexOf("update(");
                        int index2 = line.indexOf(",");
                        String id = line.substring(index + 7, index2);
                        String substring = line.substring(index2 + 1, line.length() - 1);
                        substring = substring.replace(");", "");
                        ObjectMapper mapper = new ObjectMapper();
                        TypeReference<HashMap<String, String>> typeRef
                                = new TypeReference<HashMap<String, String>>() {};
                        HashMap<String, String> map = mapper.readValue(substring, typeRef);
                        service.updatePerson(id, map);
                    }
                    else if(line.contains("create(")) {
                        index = line.indexOf("create(");
                        String substring = line.substring(index + 7, line.length() - 1);
                        substring = substring.replace(");", "");
                        service.createPerson(Person.rawCreate(substring));
                    }
                    else if(line.contains("delete(")) {
                        index = line.indexOf("delete(");
                        String substring = line.substring(index + 7, line.length() - 1);
                        substring = substring.replace(");", "");
                        service.deletePerson(substring);
                    }
                    else {
                        System.out.println("Unknown command: " + line);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error ");
            e.printStackTrace();
        }
        controller.flushQueue();
    }

    public void doWork() {
        controller.doWork();         //Timer works in other thread
        Timer timer = new Timer("Dispatcher timer");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (check()) {
                    decodeCommands(controller.getCommandQueue());
                    printInfo();
                }
            }
        };
        timer.schedule(task, 0, delay);
    }

    public PeopleService getService() {
        return this.service;
    }

    public Controller getController() {
        return this.controller;
    }

    private void printInfo() {
        String[] Ids = service.getIds();
        System.out.println("Current IDs: ");
        for(String id : Ids) {
            System.out.println("id = " + id);
        }
    }
}
