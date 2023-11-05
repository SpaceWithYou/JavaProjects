package clients;
import java.util.UUID;

public class Client {
    private String name;
    private String email;
    private final String id;

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
        this.id = UUID.randomUUID().toString();
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "name : " + name + ", email : " + email + ", id : " + id;
    }
}
