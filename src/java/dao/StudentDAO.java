package dao;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @student ludovic
 */
public class StudentDAO implements DAO<Student> {
    private final EntityManager em;
    
    public StudentDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UniversityPU");
        this.em = emf.createEntityManager();
        this.em.setFlushMode(FlushModeType.AUTO);
    }
    
    @Override
    public Collection<Student> findAll() {
        TypedQuery<Student> q = this.em.createQuery("SELECT s FROM Student s", Student.class);

        return q.getResultList();
    }

    @Override
    public Student findById(int id) {
        TypedQuery<Student> q = this.em.createQuery("SELECT s FROM Student s WHERE s.id = :id", Student.class);
        q.setParameter("id", id);
        
        return q.getSingleResult();
    }
    
    public Collection<Student> findByList(int id) {
        TypedQuery<Student> q = this.em.createQuery("SELECT s FROM Student s WHERE s.list_id = :id", Student.class);

        return q.getResultList();
    }

    @Override
    public int create(Student entity) {
        this.em.getTransaction().begin();
        this.em.persist(entity);
        this.em.getTransaction().commit();
        
        return entity.getId();
    }

    @Override
    public Student update(int id, Student entity) {
        Student student = this.em.find(Student.class, id);
        
        this.em.getTransaction().begin();
        student.setName(entity.getName());
        this.em.getTransaction().commit();
        
        return entity;
    }
    
    @Override
    public void delete(int id) {
        Student entity = this.em.find(Student.class, id);
        entity.getStudentList().removeStudent(entity);

        this.em.getTransaction().begin();
        this.em.remove(entity);
        this.em.getTransaction().commit();
    }
}
