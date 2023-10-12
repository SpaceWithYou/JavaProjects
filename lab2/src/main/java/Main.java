import Persons.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {;
        HashMap<Subjects, Double> marks = new HashMap<>();
        marks.put(Subjects.MATH, 1.0);
        marks.put(Subjects.PROGRAMMING, 2.0);
        marks.put(Subjects.PHYSICS, 3.0);
        Student student = new Student("name", "surname", "secondname", 100, "11", marks);
        student.write("C:\\Users\\Артемий\\Downloads\\JavaProjects\\lab2\\temped_tests");
        System.out.println(student.toString());
        System.out.println(marks.toString());
    }
}