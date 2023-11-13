import com.fasterxml.jackson.databind.ObjectMapper;
import persons.*;
import workers.Controller;
import workers.Dispatcher;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {;
//        HashMap<Subjects, Double> marks = new HashMap<>();
//        marks.put(Subjects.MATH, 1.0);
//        marks.put(Subjects.PROGRAMMING, 2.0);
//        marks.put(Subjects.PHYSICS, 3.0);
//        Student student = new Student("name", "surname", "secondname", 100, "11", marks);
//        student.write("C:\\Users\\Артемий\\Downloads\\JavaProjects\\lab2\\temped_tests");
//        System.out.println(student.toString());
//        System.out.println(marks.toString());
        Controller controller = new Controller("C:\\Users\\Артемий\\Downloads\\JavaProjects\\lab2\\temped_tests");
        Dispatcher dispatcher = new Dispatcher("C:\\Users\\Артемий\\Downloads\\JavaProjects\\lab2\\temped_tests", controller);
        dispatcher.doWork();
        System.out.println("PERSONS = ");
        try {
            Thread.sleep(2000);
            System.out.println(dispatcher.getService().getAll()[0].toString());
            ObjectMapper mapper = new ObjectMapper();
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
            System.out.println("update(" + dispatcher.getService().getIds()[0] + ", " + mapper.writeValueAsString(change) + ");\n");
            Thread.sleep(2000);
            System.out.println(dispatcher.getService().getAll()[0].toString());
            System.out.println("delete(" + dispatcher.getService().getIds()[0] + ");\n");
            Thread.sleep(2000);
            System.out.println("LENGTH = " + dispatcher.getService().getAll().length);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}