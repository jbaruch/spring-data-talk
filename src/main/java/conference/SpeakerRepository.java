package conference;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jeka
 * @since 07/10/2014
 */
public interface SpeakerRepository extends MongoRepository<Speaker, Long> {

    List<Speaker> findByName(String name);

    List<Speaker> findByNameLike(String name);

    @Query("{'talks.when' : {$gt : ?0, $lt : ?1}}")
    List<Speaker> findSpeakersWithTalksBetween(Date from, Date to);

    default List<Talk> findTalksBetween(Date from, Date to){
        List<Speaker> speakers = findSpeakersWithTalksBetween(from, to);
        return speakers.stream().map(Speaker::getTalks).flatMap(Collection::stream).filter(t -> isBetween(from, to, t)).collect(Collectors.toList());
    }

    default boolean isBetween(Date from, Date to, Talk time) {
        return time.getWhen().after(from) && time.getWhen().before(to);
    }

    @Query("{ 'talks.title': {$regex : ?0 }}")
    Set<Speaker> getSpeakersWithTalksAbout(String name);
}
