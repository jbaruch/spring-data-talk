package conference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.time.LocalDate.now;
import static java.time.LocalTime.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;

/**
 * @author Jeka
 * @since 11/10/2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class JpaMainTest {

    private static final Logger LOG = LoggerFactory.getLogger(JpaMainTest.class);

    @Autowired
    private SpeakerRepository speakerRepository;

    @Before
    @Transactional
    @Rollback(false)
    public void setUp(){


        Speaker jeka = new Speaker("Evgeny Borisov");
        jeka.addTalk(new Talk("Spring The Ripper", time("12:30")));
        jeka.addTalk(new Talk("Spring Data", time("17:30")));


        Speaker nikolay = new Speaker("Nikolay Alimenkov");
        nikolay.addTalk(new Talk("CD JEE7", time("18:00")));

        speakerRepository.save(Arrays.asList(jeka, nikolay));
    }

    @Test
    @Transactional
    public void testJpa() {

        LOG.info("count = " + speakerRepository.count());
        List<Speaker> allSpeakers = speakerRepository.getAllSpeakers();
        for (Speaker speaker : allSpeakers) {
            LOG.info(speaker.getName());
        }
        Speaker speaker = speakerRepository.findByName("Evgeny Borisov").get(0);
        Set<Talk> talks = speaker.getTalks();
        for (Talk talk : talks) {
            LOG.info("talk = " + talk);
        }
    }

    private Date time(String time) {
        String[] split = time.split(":");
        int hours = Integer.parseInt(split[0]);
        int minutes = Integer.parseInt(split[1]);
        return from(of(hours, minutes).atDate(now()).atZone(systemDefault()).toInstant());
    }
}
