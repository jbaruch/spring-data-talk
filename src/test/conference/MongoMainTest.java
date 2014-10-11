package conference;

import org.junit.After;
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
@ContextConfiguration(classes = MongoConfig.class)
public class MongoMainTest {

    private static final Logger LOG = LoggerFactory.getLogger(MongoMainTest.class);


    @Autowired
    private SpeakerRepository speakerRepository;

    @Before
    @Transactional
    @Rollback(false)
    public void setUp() {

        Speaker jeka = new Speaker("Evgeny Borisov");
        jeka.addTalk(new Talk("Spring The Ripper", time("12:30")));
        jeka.addTalk(new Talk("Spring Data", time("17:30")));


        Speaker nikolay = new Speaker("Nikolay Alimenkov");
        nikolay.addTalk(new Talk("CD JEE7", time("18:00")));

        Speaker baruch = new Speaker("Baruch Sadogursky");
        baruch.addTalk(new Talk("AST Groovy", time("12:00")));
        baruch.addTalk(new Talk("Making Spring Groovy", time("09:00")));

        speakerRepository.save(Arrays.asList(baruch, jeka, nikolay));
    }

    @Test
    public void testCount() {
        LOG.info("Speaker count" + speakerRepository.count());

    }

    @Test
    public void testFindAll() {
        List<Speaker> allSpeakers = speakerRepository.findAll();

        for (Speaker speaker : allSpeakers) {
            LOG.info(speaker.getName());
        }
    }

    @Test
    public void testFindByName() {
        Speaker speaker = speakerRepository.findByName("Evgeny Borisov").get(0);
        List<Talk> talks = speaker.getTalks();
        for (Talk talk : talks) {
            LOG.info("talk = " + talk);
        }
    }

    @Test
    public void testFindByNameLike() {
        LOG.info("********* ALL SPEAKERS WITH ov ***********");
        List<Speaker> speakers = speakerRepository.findByNameLike("*ov*");
        for (Speaker speaker : speakers) {
            LOG.info(speaker.getName());
        }
    }

    @Test
    public void testFindTalksBetween(){
        LOG.info("********* Talks between 08:00 and 13:00 ***********");
        List<Talk> matchingTalks = speakerRepository.findTalksBetween(time("08:00"), time("13:00"));
        matchingTalks.forEach(t -> LOG.info(t.toString()));
        LOG.info("********* ***************************** ***********");

    }

    @Test
    public void testGetSpeakersWithTalksAboutSpring(){
        LOG.info("********* Speakers talking about Spring ***********");
        Set<Speaker> matchingSpeakers = speakerRepository.getSpeakersWithTalksAbout(".*Spring.*");
        matchingSpeakers.forEach(t -> LOG.info(t.toString()));
        LOG.info("********* ***************************** ***********");
    }

    @After
    public void clean() {
        speakerRepository.deleteAll();
    }

    private Date time(String time) {
        String[] split = time.split(":");
        int hours = Integer.parseInt(split[0]);
        int minutes = Integer.parseInt(split[1]);
        return from(of(hours, minutes).atDate(now()).atZone(systemDefault()).toInstant());
    }
}
