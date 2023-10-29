package clients;

public interface ClientServiceInteface {
    void create(Client client);
    boolean delete(int id);
    boolean update(int id);
    Client search(int id);
}
