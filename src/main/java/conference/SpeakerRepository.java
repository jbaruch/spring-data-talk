package conference;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jeka
 * @since 07/10/2014
 */
public interface SpeakerRepository extends CrudRepository<Speaker, Long> {

    List<Speaker> findByName(String name);

    List<Speaker> findByNameLike(String name);
}
