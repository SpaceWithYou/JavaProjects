package dao;
import clients.Client;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import services.ClientServiceInterface;
import java.util.concurrent.TimeUnit;

public class DaoCache implements ClientServiceInterface {
    private Cache<String, Client> clientCache;

    public DaoCache() {
        clientCache = Caffeine.newBuilder().initialCapacity(1).maximumSize(10_000).expireAfterAccess(30, TimeUnit.MINUTES).build();
    }

    @Override
    public void create(Client client) {
        clientCache.put(client.getId(), client);
    }

    @Override
    public boolean delete(String id) {
        try {
            clientCache.asMap().remove(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**Client has name and email
     * @return false if data.size != 2 otherwise - true**/
    @Override
    public boolean update(String id, String[] data) {
        if(data.length != 2) {
            return false;
        } else {
            Client client = clientCache.asMap().get(id);
            client.setName(data[0]);
            client.setEmail(data[1]);
            return true;
        }
    }

    @Override
    public Client search(String id) {
        return clientCache.getIfPresent(id);
    }
}
