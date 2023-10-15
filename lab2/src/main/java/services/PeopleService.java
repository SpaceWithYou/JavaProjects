package services;
import dao.CachedPeopleDAO;
import persons.Person;
import persons.Student;
import persons.Subjects;
import persons.Teacher;
import java.util.HashMap;

public class PeopleService {
    private final CachedPeopleDAO peopleDAO;

    public PeopleService(String path) {
        peopleDAO = new CachedPeopleDAO(path);
    }

    public void createPerson(Person person) {
        System.out.println("creating...");
        peopleDAO.add(person);
    }

    private HashMap<Subjects, Double> parseMap(String stringMap) {
        HashMap<Subjects, Double> map = new HashMap<>();
        stringMap = stringMap.replace("{", "").replace("}", "").
                replace("\r\n", "").replace("\"", "");
        String[] splitString = stringMap.split(",");
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
        stringArr = stringArr.replace("[", "").replace("]", "");
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
     * or Hashmap of Subjects and Doubles JSON <br>
     * for teacher subject**/
    public Person updatePerson(String id, HashMap<String, String> changeMap) {
        System.out.println("updating...");
        Person person = this.peopleDAO.getById(id);
        for(String value: changeMap.keySet()) {
            value.toLowerCase();
            switch (value) {
                case "name" -> person.setName(changeMap.get(value));
                case "surname" -> person.setSurName(changeMap.get(value));
                case "secondname" -> person.setSecondName(changeMap.get(value));
                case "birthyear" -> person.setBirthYear(Integer.parseInt(changeMap.get(value)));
                case "number" -> person.setTelephoneNumber(changeMap.get(value));
                case "map" -> {
                    if(person.toString().contains("hours")) {
                        continue;
                    }
                    Student student = (Student) person;
                    student.setMap(parseMap(changeMap.get(value)));
                }
                case "hours" -> {
                    if(person.toString().contains("map")) {
                        continue;
                    }
                    Teacher teacher = (Teacher) person;
                    teacher.setHours(parseHours(changeMap.get(value)));
                }
                case "subject" -> {
                    if(person.toString().contains("map")) {
                        continue;
                    }
                    Teacher teacher = (Teacher) person;
                    if(changeMap.get(value).equals("NODATA")) teacher.setSubject(Subjects.NODATA);
                    else if(changeMap.get(value).equals("MATH")) teacher.setSubject(Subjects.MATH);
                    else if(changeMap.get(value).equals("PHYSICS")) teacher.setSubject(Subjects.PHYSICS);
                    else if(changeMap.get(value).equals("PROGRAMMING")) teacher.setSubject(Subjects.PROGRAMMING);
                }
                default -> System.out.println("Unknown command " + value);
            }
        }
        return person;
    }

    public void deletePerson(String id) {
        System.out.println("deleting...");
        peopleDAO.delete(peopleDAO.getById(id));
    }

    public String[] getIds() {
        return peopleDAO.getIds();
    }

    public Person[] getAll() {
        return this.peopleDAO.getAll();
    }
}
