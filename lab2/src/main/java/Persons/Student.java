package Persons;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person {
    private Map<Subjects, Double> map;

    public Student(String name, String surName, String secondName, int birthYear, String telephoneNumber, Map<Subjects, Double> map) {
        super(name, surName, secondName, birthYear, telephoneNumber);
        this.map = new HashMap<>();
        this.map.putAll(map);
    }

//    public Student() {
//        super();
//        this.map = new HashMap<>();
//    }

    public Map<Subjects, Double> getMap() {
        Map<Subjects, Double> copyMap = new HashMap<>();
        for (Map.Entry<Subjects, Double> entry : map.entrySet()) {
            Subjects key = entry.getKey();
            Double value = entry.getValue();
            copyMap.put(key, value);
        }
        return copyMap;
    }

    public void setMap(HashMap<Subjects, Double> newMap) {
        this.map.clear();
        for(Subjects subject: newMap.keySet()) {
            this.map.put(subject, newMap.get(subject));
        }
    }
}
