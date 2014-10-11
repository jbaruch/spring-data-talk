package conference;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@NodeEntity
public class Speaker {

    @GraphId
    private Long speakerId;

    private String name;

    @RelatedTo(direction = INCOMING)
    private @Fetch Set<Talk> talks;

    public Speaker() {
    }

    public Speaker(String name) {
        this.name = name;
    }

    public void addTalk(Talk talk) {
        if(talks==null) talks = new HashSet<Talk>();
        talks.add(talk);
    }

    public String getName() {
        return name;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    @Override
    public String toString() {
        return name;
    }
}
