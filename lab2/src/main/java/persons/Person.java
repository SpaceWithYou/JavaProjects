package persons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.util.UUID;

public abstract class Person {
    private String name;
    private String surName;
    private String secondName;
    private int birthYear;
    private String telephoneNumber;
    private final String id;

    public Person(String name, String surName, String secondName, int birthYear, String telephoneNumber) {
        this.name = name;
        this.surName = surName;
        this.secondName = secondName;
        this.birthYear = birthYear;
        this.telephoneNumber = telephoneNumber;
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    Person() {
        name = "";
        surName = "";
        secondName = "";
        birthYear = 0;
        telephoneNumber = "";
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Person person) {
        return this.toString().equals(person.toString());
    }

    public static Person rawCreate(String rawData) throws Exception {
        if(rawData.contains("hours")) {
            return new ObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true).readValue(rawData, Teacher.class);
        } else if (rawData.contains("map")) {
            return new ObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true).readValue(rawData, Student.class);
        }
        return new ObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true).readValue(rawData, Person.class);
    }

    public static Person create(String filename) {                                                                      //creates Persons.Person from text file
        File file = new File(filename);
        StringBuilder builder = new StringBuilder();
        if(!file.isFile()) return null;
        try(FileReader reader = new FileReader(file)) {
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
//            if(builder.toString().contains("hours")) {
//                return new ObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true).readValue(builder.toString(), Teacher.class);
//            } else if (builder.toString().contains("map")) {
//                return new ObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true).readValue(builder.toString(), Student.class);
//            }
//            return new ObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true).readValue(builder.toString(), Person.class);
              return rawCreate(builder.toString());
        }
        catch(Exception e) {
            System.out.println("Incorrect data");
            throw new RuntimeException(e);
        }
    }

    public int write(String directory) {                                                                                //Creates person file in directory
        File file = new File(directory + "\\" + this.name + this.surName + this.secondName + ".txt");
        if(file.length() != 0) {                                                                                        //non-empty file
            return 0;
        }
        else {
            try {
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(this.toString().getBytes());
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return 1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String newSurName) {
        this.surName = newSurName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String newSecondName) {
        this.secondName = newSecondName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int newBirthYear) {
        this.birthYear = newBirthYear;
    }

    public String getTelephoneNumber() {
       return telephoneNumber;
    }

    public void setTelephoneNumber(String newNumber) {
        this.telephoneNumber = newNumber;
    }

    public String getId() {
        return id;
    }
}
