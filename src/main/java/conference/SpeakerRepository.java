package conference;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@Repository
public class SpeakerRepository {
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<Speaker> findByName(String name) {
        Query query = em.createQuery("select s from Speaker as s where s.name=:name");
        return query.setParameter("name", name).getResultList();
    }


    @SuppressWarnings("unchecked")
    public List<Speaker> getAllSpeakers() {
        return em.createQuery("from Speaker").getResultList();
    }

    public void save(List<Speaker> speakers) {
        for (Speaker speaker : speakers) {
            em.persist(speaker);
        }
    }


    public int count() {
        return em.createQuery("select count (s.name) from Speaker s").getFirstResult();
    }


}
