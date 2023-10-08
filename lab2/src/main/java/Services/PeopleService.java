package Services;
import DAO.CachedPeopleDAO;
import Persons.Person;
import Persons.Student;
import Persons.Subjects;
import Persons.Teacher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

public class PeopleService {
    private final CachedPeopleDAO peopleDAO;

    public PeopleService(String path) {
        peopleDAO = new CachedPeopleDAO(path);
    }

    public void createPerson(Person person) {
        peopleDAO.add(person);
    }

    /**    Only spaces and commas in brackets:
     * "[   111,  1 2 3, 4, 5]" = [111, 123, 4, 5]**/
    private int[] stringToIntArray(String parseText) {
        String[] text = parseText.replace("[", "").replace("]", "").split(",");
        int[] res = new int[text.length];
        for(int i = 0; i < text.length; i++) {
            try {
                res[i] = Integer.parseInt(text[i]);
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }

        return res;
    }

    /**<p>Parameters are in the following order:
     name, surName, secondName, birthYear, telephoneNumber</p>
     <p>optional: map - student; subject, hours - teacher</p>**/
    public Person updatePerson(String id, String... params) {
        Person person = peopleDAO.getById(id);
        ObjectMapper mapper = new ObjectMapper();
        if (params.length == 7) {
            Subjects subject;
            switch (params[5]) {
                case "MATH" -> subject = Subjects.MATH;
                case "PHYSICS" -> subject = Subjects.PHYSICS;
                case "PROGRAMMING" -> subject = Subjects.PROGRAMMING;
                case "NODATA" -> subject = Subjects.NODATA;
                default -> {
                    System.out.println("Wrong parameter");
                    return null;
                }
            }
            TypeReference<int[]> typeRef = new TypeReference<int[]>() {};
            Teacher teacher = new Teacher(params[0], params[1], params[2], Integer.parseInt(params[3]), params[4], subject, stringToIntArray(params[6]));
            peopleDAO.update(person, teacher);
            return teacher;
        }
        if (params.length == 6) {
            try {
                TypeReference<HashMap<Subjects, Double>> typeRef = new TypeReference<HashMap<Subjects, Double>>() {};
                HashMap<Subjects, Double> map = mapper.readValue(params[5], typeRef);
                return new Student(params[0], params[1], params[2], Integer.parseInt(params[3]), params[4], map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Wrong parameter");
            return null;
        }
    }

    public void deletePerson(String id) {
        peopleDAO.delete(peopleDAO.getById(id));
    }
}
