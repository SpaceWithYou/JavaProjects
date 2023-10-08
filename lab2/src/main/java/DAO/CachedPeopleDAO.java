package DAO;
import Persons.Person;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CachedPeopleDAO implements DaoInterface<Person> {
    private static Map<String, Person> personMap;                               //HashMap <id, person>
    private final String path;

    public CachedPeopleDAO(String path) {
        Path path1 = Path.of(path);
        if (!Files.exists(path1) || !Files.isDirectory(path1)) {
            throw new IllegalArgumentException();
        }
        this.path = path;
        personMap = new HashMap<>();
    }

    @Override
    public void add(Person person) {
        personMap.put(person.getId(), person);
        person.write(this.path);
    }

    @Override
    public void delete(Person person) {
        File dir = new File(path);
        for (File file : dir.listFiles()) {                                       //Read files and compare them
            try {
                if (Person.create(file.getAbsolutePath()).equals(person)) {
                    file.delete();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Exception on file " + file.getName());
            }
        }
        personMap.remove(person);
    }

    @Override
    public void update(Person oldPerson, Person newPerson) {
        personMap.remove(oldPerson.getId());
        personMap.put(newPerson.getId(), newPerson);                                                                    //replace oldPerson by newPerson in map
        this.delete(oldPerson);                                                                                         // Write new file
        newPerson.write(path);
    }

    @Override
    public Person getById(String id) {
        return personMap.get(id);
    }
}