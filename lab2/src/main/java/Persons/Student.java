package Persons;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person {
    private final Map<Subjects, Double> map;

    public Student(String name, String surName, String secondName, int birthYear, String telephoneNumber, Map<Subjects, Double> map) {
        super(name, surName, secondName, birthYear, telephoneNumber);
        this.map = new HashMap<>();
        this.map.putAll(map);
    }

    public Map<Subjects, Double> getMap() {
        Map<Subjects, Double> copyMap = new HashMap<>();
        for (Map.Entry<Subjects, Double> entry : map.entrySet()) {
            Subjects key = entry.getKey();
            Double value = entry.getValue();
            copyMap.put(key, value);
        }
        return copyMap;
    }

}
