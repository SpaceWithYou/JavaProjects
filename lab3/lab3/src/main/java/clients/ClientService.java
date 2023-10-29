package clients;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class ClientService implements ClientServiceInteface {
    private Cache<String, Client> clientCache;

    public ClientService() {
        clientCache = Caffeine.newBuilder().initialCapacity(1).maximumSize(10_000).build();
    }

    @Override
    public void create(Client client) {
        clientCache.put(client.getId(), client);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(int id) {
        return false;
    }

    @Override
    public Client search(int id) {
        return null;
    }
}
