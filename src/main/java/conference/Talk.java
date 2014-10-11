package conference;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Date;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@NodeEntity
public class Talk {

    @GraphId
    private Long talkId;

    private Date when;

    private String title;

    @RelatedTo(direction = Direction.BOTH)
    private Speaker speaker;

    public Talk() {
    }

    public Talk(String title, Date date) {
        this.title = title;
        this.when = date;
    }

    public Speaker getSpeaker() {
        return speaker;
    }


    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return title;
    }

    public Date getWhen() {
        return when;
    }
}
