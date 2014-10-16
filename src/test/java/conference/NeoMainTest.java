package conference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
@Transactional
public class NeoMainTest {

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private TalkRepository talkRepository;

    @Before
    @Rollback(false)
    public void setup() throws IOException {
        Speaker jeka = new Speaker("Evgeny Borisov");
        Talk ripper = new Talk("Spring The Ripper", time("12:30"));
        jeka.addTalk(ripper);
        jeka.addTalk(new Talk("Spring Data", time("17:30")));

        Speaker nikolay = new Speaker("Nikolay Alimenkov");
        nikolay.addTalk(new Talk("CD JEE7", time("18:00")));
        speakerRepository.save(Arrays.asList(jeka, nikolay));
    }

    @Test
    public void testFindAll() {
        Result<Speaker> speakers = speakerRepository.findAll();
        speakers.forEach(speaker -> System.out.println("speaker = " + speaker));
    }

    @Test
    public void testByName() {
        Iterable<Speaker> speakers = speakerRepository.findByName("Evgeny Borisov");
        speakers.forEach(speaker -> System.out.println("speaker = " + speaker));
    }

    @Test
    public void testByNameLike() {
        Iterable<Speaker> speakers = speakerRepository.findByNameLike(".*Evgeny.*");
        speakers.forEach(speaker -> System.out.println("speaker = " + speaker));
    }

    @Test
    public void testTalksBySpeaker(){
        List<Talk> talks = talkRepository.findTalksBySpeakerName("Evgeny Borisov");

//        Iterable<Talk> talks = talkRepository.findAllTalksBySpeakerName("Evgeny Borisov");
        talks.forEach(talk -> System.out.println("talk = " + talk));
    }

    @After
    @Rollback(false)
    public void cleanup() throws IOException {
        speakerRepository.deleteAll();
    }

    private Date time(String time) {
        String[] split = time.split(":");
        int hours = Integer.parseInt(split[0]);
        int minutes = Integer.parseInt(split[1]);
        return from(of(hours, minutes).atDate(now()).atZone(systemDefault()).toInstant());
    }
}
