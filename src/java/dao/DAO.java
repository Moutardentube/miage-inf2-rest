package dao;

import java.util.Collection;

/**
 *
 * @author eddebbima
 */
public interface DAO<T> {
    
    public int create(T entity);
    
    public T update(int id, T entity);
    
    public void delete(int id);
    
    public Collection<T> findAll();
    
    public T findById(int id);
}
