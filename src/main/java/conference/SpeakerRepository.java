package conference;


import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * @author Jeka
 * @since 07/10/2014
 */
public interface SpeakerRepository extends GraphRepository<Speaker>{

    @Query("MATCH (speaker:Speaker {name:{0}})<--(talk:Talk) RETURN talk")
    List<Talk> findAllTalksBySpeakerName(String name);


    List<Speaker> findByName(String name);

    List<Speaker> findByNameLike(String name);

//    "(speaker:Speaker) RETURN Speaker"

}
