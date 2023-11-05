package services;
import clients.Client;
import dao.DaoCache;

public class ClientService implements ClientServiceInterface {
    private static DaoCache cache;

    public ClientService() {
        cache = new DaoCache();
    }

    public DaoCache getCache() {
        return cache;
    }

    @Override
    public void create(Client client) {
        cache.create(client);
    }

    @Override
    public boolean delete(String id) {
        return cache.delete(id);
    }

    @Override
    public boolean update(String id, String[] data) {
        return cache.update(id, data);
    }

    @Override
    public Client search(String id) {
        return cache.search(id);
    }

}
