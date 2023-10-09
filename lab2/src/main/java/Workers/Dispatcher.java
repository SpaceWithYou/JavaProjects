package Workers;
import Persons.Person;
import Services.PeopleService;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

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

    private String[] getParams(String[] arguments) {
        String[] res = new String[arguments.length - 1];
        System.arraycopy(arguments, 1, res, 0, arguments.length - 1);
        return res;
    }
    /**
     * <p>Commands:</p>
     * <p>
     * updatePerson(String id, String... params), <br>
     * deletePerson(String id), <br>
     * createPerson(Person person) in JSON format
     * </p>
     * <p>In following form: <br> command1; <br> command2; <br>...</p>
     **/
    private void decodeCommands(Queue<String> queue) {
        System.out.println("Queue has " + controller.getCommandQueue().size() + "  elements");
        String substring;
        String[] arguments;
        int startIndex;
        int endIndex;
        for(String element : queue) {
            System.out.println(element);
            String[] splited = element.split(";\n");
            for (String s : splited) {
                if (s.contains("updatePerson(")) {
                    startIndex = s.indexOf("updatePerson(");
                    endIndex = s.indexOf(")");
                    substring = s.substring(startIndex + 13, endIndex);
                    arguments = substring.split(", ");
                    service.updatePerson(arguments[0], getParams(arguments));
                } else if (s.contains("deletePerson(")) {
                    startIndex = s.indexOf("deletePerson(");
                    endIndex = s.indexOf(")");
                    substring = s.substring(startIndex + 13, endIndex);
                    service.deletePerson(substring);
                } else if (s.contains("createPerson(")) {
                    startIndex = s.indexOf("createPerson(");
                    endIndex = s.indexOf(")");
                    substring = s.substring(startIndex + 13, endIndex);
                    try {
                        service.createPerson(Person.rawCreate(substring));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    System.out.println("Unknown command " + s);
                }
            }
        }
        controller.flushQueue();
    }

    public void doWork() {
        controller.doWork();
        System.out.println("Working " + Thread.currentThread().getName());                                              //Timer works in other thread
        Timer timer = new Timer("Dispatcher timer");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (check()) {
                    decodeCommands(controller.getCommandQueue());
                }
            }
        };
        timer.schedule(task, 0, delay);
    }
}
