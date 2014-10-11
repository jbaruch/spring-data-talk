package conference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static java.time.LocalDate.now;
import static java.time.LocalTime.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;

/**
 * @author jbaruch
 * @since 10/11/14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Neo4jConfig.class)
public class NeoMainTest {

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private GraphDatabase graphDatabase;

    @Before
    public void setup() throws IOException {
        FileUtils.deleteRecursively(new File("conference.db"));

    }

    @Test
    public void test(){
        Speaker jeka = new Speaker("Evgeny Borisov");
        Talk ripper = new Talk("Spring The Ripper", time("12:30"));
        jeka.addTalk(ripper);
        jeka.addTalk(new Talk("Spring Data", time("17:30")));

        Speaker nikolay = new Speaker("Nikolay Alimenkov");
        nikolay.addTalk(new Talk("CD JEE7", time("18:00")));


        try (Transaction tx = graphDatabase.beginTx()) {
            speakerRepository.save(Arrays.asList(jeka, nikolay));
            long count = speakerRepository.count();
            System.out.println("count = " + count);
            Speaker speaker = speakerRepository.findByName("Evgeny Borisov").get(0);
            Set<Talk> talks = speaker.getTalks();
            for (Talk talk : talks) {
                System.out.println("talk = " + talk);
            }
            tx.success();
        }
    }

    private Date time(String time) {
        String[] split = time.split(":");
        int hours = Integer.parseInt(split[0]);
        int minutes = Integer.parseInt(split[1]);
        return from(of(hours, minutes).atDate(now()).atZone(systemDefault()).toInstant());
    }
}
