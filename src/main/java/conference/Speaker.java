package conference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeka
 * @since 07/10/2014
 */
public class Speaker {

    private String name;

    private List<Talk> talks;

    public Speaker() {
    }

    public Speaker(String name) {
        this.name = name;
    }

    public void addTalk(Talk talk) {
        if(talks==null) talks = new ArrayList<Talk>();
        talks.add(talk);
    }

    public String getName() {
        return name;
    }

    public List<Talk> getTalks() {
        return talks;
    }

    @Override
    public String toString() {
        return name;
    }
}
