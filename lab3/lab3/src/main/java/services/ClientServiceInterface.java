package services;
import clients.Client;

public interface ClientServiceInterface {
    void create(Client client);
    boolean delete(String id);
    boolean update(String id, String[] data);
    Client search(String id);
}
