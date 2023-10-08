import Persons.Student;
import Persons.Subjects;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Subjects, Double> marks = new HashMap<>();
        marks.put(Subjects.MATH, 1.0);
        marks.put(Subjects.PHYSICS, 2.0);
        marks.put(Subjects.PROGRAMMING, 3.0);
        Student student = new Student("sName", "sSurName", "sSecondName", 1999, "+79999999999", marks);
        student.write("C:\\Users\\Артемий\\Downloads\\JavaLabs\\lab2\\temped");
    }
}