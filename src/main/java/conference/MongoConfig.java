package conference;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@Configuration
@EnableMongoRepositories
public class MongoConfig {
    @Bean
    public Mongo getMongo() throws Exception{
        return new MongoClient();
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(Mongo m) throws Exception{
        return new MongoTemplate(m, "conference");
    }

}
