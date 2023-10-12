package Workers;
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
        System.out.println("Working decoder");
        for(String commands : queue) {
            
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
        for(String id : Ids) {
            System.out.println("id = " + id);
        }
    }
}
