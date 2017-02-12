package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author ludovic
 */
public class StudentListDAO  implements DAO<StudentList> {
    private final EntityManager em;
    
    public StudentListDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UniversityPU");
        this.em = emf.createEntityManager();
        this.em.setFlushMode(FlushModeType.AUTO);
    }
    
    @Override
    public List<StudentList> findAll() {
        TypedQuery<StudentList> q = this.em.createQuery("SELECT l FROM StudentList l", StudentList.class);

        return q.getResultList();
    }

    @Override
    public StudentList findById(int id) {
        TypedQuery<StudentList> q = this.em.createQuery("SELECT l FROM StudentList l WHERE l.id = :id", StudentList.class);
        q.setParameter("id", id);
        
        return q.getSingleResult();
    }

    @Override
    public int create(StudentList entity) {
        this.em.getTransaction().begin();
        this.em.persist(entity);
        this.em.getTransaction().commit();
        
        return entity.getId();
    }
    
    public int addStudent(int id, Student entity) {
        StudentList list = this.em.find(StudentList.class, id);
        System.out.println(list);
        list.addStudent(entity);
        entity.setStudentList(list);

        this.em.getTransaction().begin();
        this.em.persist(list);
        this.em.getTransaction().commit();
        
        return entity.getId();
    }

    @Override
    public StudentList update(int id, StudentList entity) {
        StudentList list = this.em.find(StudentList.class, id);
        
        this.em.getTransaction().begin();
        list.setName(entity.getName());
        this.em.getTransaction().commit();
        
        return entity;
    }
    
    @Override
    public void delete(int id) {
        StudentList entity = this.em.find(StudentList.class, id);

        this.em.getTransaction().begin();
        this.em.remove(entity);
        this.em.getTransaction().commit();
    }
}
