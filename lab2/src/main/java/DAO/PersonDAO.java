package DAO;
import Persons.Person;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements DaoInterface<Person> {
    private static List<Person> personList;                                                                             //list of persons
    private final String path;                                                                                          //local path

    public PersonDAO(String path) {
        Path path1 = Path.of(path);
        if (!Files.exists(path1) || !Files.isDirectory(path1)) {
            throw new IllegalArgumentException();
        }
        this.path = path;
        personList = new ArrayList<>();
    }

    @Override
    public void add(Person person) {
        person.write(this.path);
        personList.add(person);
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
        personList.remove(person);
    }

    @Override
    public void update(Person oldPerson, Person newPerson) {
        personList.set(personList.indexOf(oldPerson), newPerson);                                                       //replace oldPerson by newPerson in list with old filename
        this.delete(oldPerson);
        newPerson.write(path);
    }

    @Override
    public Person getById(String id) {
        for (Person person : personList) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public String[] getIds() {
        String[] res = new String[personList.size()];
        int i = 0;
        for(Person person: personList) {
            res[i] = person.getId();
            i++;
        }
        return res;
    }
}
