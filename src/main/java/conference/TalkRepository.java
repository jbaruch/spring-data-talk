package conference;

import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * @author jbaruch
 * @since 10/11/14
 */
public interface TalkRepository extends GraphRepository<Talk> {

//    @Query("MATCH (speaker:Speaker {name:{0}})<--(talk:Talk) RETURN talk")
//    List<Talk> findAllTalksBySpeakerName(String name);

    List<Talk> findTalksBySpeakerName(String speakerName);
}
