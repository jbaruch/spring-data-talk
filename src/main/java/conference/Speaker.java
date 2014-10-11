package conference;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.BOTH;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@NodeEntity
public class Speaker {

    @GraphId
    private Long speakerId;

    private String name;

    @RelatedTo(direction = BOTH)
    private @Fetch Set<Talk> talks;

    public Speaker() {
        talks = new HashSet<Talk>();
    }

    public Speaker(String name) {
        this();
        this.name = name;

    }

    public void addTalk(Talk talk) {
        talk.setSpeaker(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        if (name != null ? !name.equals(speaker.name) : speaker.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
