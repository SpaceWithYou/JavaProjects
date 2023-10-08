package DAO;

public interface DaoInterface<T> {
    void add(T t);
    void delete(T t);
    void update(T t1, T t2);
    T getById(String id);
}
