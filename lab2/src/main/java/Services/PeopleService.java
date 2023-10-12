package Services;
import DAO.CachedPeopleDAO;
import Persons.Person;
import Persons.Student;
import Persons.Subjects;
import Persons.Teacher;

import java.util.HashMap;

public class PeopleService {
    private final CachedPeopleDAO peopleDAO;

    public PeopleService(String path) {
        peopleDAO = new CachedPeopleDAO(path);
    }

    public void createPerson(Person person) {
        peopleDAO.add(person);
    }

    private HashMap<Subjects, Double> parseMap(String stringMap) {
        HashMap<Subjects, Double> map = new HashMap<>();
        stringMap.replace("{", "").replace("}", "").
                replace(",", "").replace("", "\"").replace(" ", "");
        String[] splitString = stringMap.split(":");
        String[] splitLine;
        try {
            for(String line: splitString) {
                splitLine = line.split(":");
                switch (splitLine[0]) {
                    case "PHYSICS" -> map.put(Subjects.PHYSICS, Double.parseDouble(splitLine[1]));
                    case "MATH" -> map.put(Subjects.MATH, Double.parseDouble(splitLine[1]));
                    case "PROGRAMMING" -> map.put(Subjects.PROGRAMMING, Double.parseDouble(splitLine[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("Exception ");
            e.printStackTrace();
        }
        return map;
    }

    private int[] parseHours(String stringArr) {
        stringArr.replace("[", "").replace("]", "");
        String[] splited = stringArr.split(", ");
        int[] res = new int[splited.length];
        try {
            for(int i = 0; i < splited.length; i++) {
                res[i] = Integer.parseInt(splited[i]);
            }
        } catch (Exception e) {
            System.out.println("Exception ");
            e.printStackTrace();
        }
        return res;
    }

    /**Inputs: id, <br>
     * changeMap keys: <br>
     * name, surname, secondname, birthyear, number <br>
     * Optional: hours [hour1, hour2, ...] string <br>
     * or Hashmap of Subjects and Doubles JSON **/
    public Person updatePerson(String id, HashMap<String, String> changeMap) {
        Person person = this.peopleDAO.getById(id);
        for(String value: changeMap.values()) {
            value.toLowerCase();
            switch (value) {
                case "name" -> person.setName(changeMap.get("name"));
                case "surname" -> person.setSurName(changeMap.get("surname"));
                case "secondname" -> person.setSecondName(changeMap.get("secondname"));
                case "birthyear" -> person.setBirthYear(Integer.parseInt(changeMap.get("birthyear")));
                case "map" -> {
                    Student student = (Student) person;
                    student.setMap(parseMap(changeMap.get("map")));
                }
                case "hours" -> {
                    Teacher teacher = (Teacher) person;
                    teacher.setHours(parseHours(changeMap.get("hours")));
                }
                default -> System.out.println("Unknown command");
            }
        }
        return person;
    }

    public void deletePerson(String id) {
        peopleDAO.delete(peopleDAO.getById(id));
    }

    public String[] getIds() {
        return peopleDAO.getIds();
    }
}
