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

    /**
     * <p>Commands:</p>
     * <p>updatePerson(String id, String... params), deletePerson(String id), createPerson(Persons.Person person)</p>
     * <p>In following form: command1;command2;...</p>
     **/
    private void decodeCommands(Queue<String> queue) {                                                                  //Decode commands from file, add them to queue
//        for(String s : queue) {
//            System.out.println(queue.peek());
//        }
//        String[] splited;
//        int index, lastIndex;
//        StringBuilder builder = new StringBuilder();
//        for(String fileCom : queue) {
//            splited = fileCom.split(";");
//            for(String com : splited) {
//                if(com.contains("updatePerson(")) {
//                    index = com.indexOf("updatePerson(");
//                    lastIndex = com.indexOf(")", index);
//                    builder.append(com, index + 14, lastIndex - 1);
//                    String[] tempedString = builder.toString().split(",");
//                    String t = tempedString.toString().replace(tempedString[0], "");
//                    service.updatePerson(tempedString[0], t.split(","));
//                    builder.delete(0, builder.length() - 1);
//                }
//                else if(com.contains("deletePerson(")) {
//                    index = com.indexOf("deletePerson(");
//                    lastIndex = com.indexOf(")", index);
//                    builder.append(com, index + 14, lastIndex - 1);
//                    service.deletePerson(builder.toString());
//                    builder.delete(0, builder.length() - 1);
//                }
//                else if(com.contains("createPerson(")) {
//
//                    index = com.indexOf("createPerson(");
//                    lastIndex = com.indexOf(")", index);
//                    builder.append(com, index + 14, lastIndex - 1);
//                    service.createPerson(Person.create(builder.toString()));
//                    builder.delete(0, builder.length() - 1);
//                }
//            }
//        }
        for(String element : queue) {

        }
        controller.flushQueue();
    }

    public void doWork() {
        System.out.println("Working " + Thread.currentThread().getName());                                              //Timer works in other thread
        Timer timer = new Timer();
        TimerTask checkTask = new TimerTask() {
            public void run() {
                if(check()) {
                    decodeCommands(controller.getCommandQueue());
                }
            }
        };
        timer.schedule(checkTask, delay);
    }

}
