package conference;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

/**
 * @author Jeka
 * @since 09/10/2014
 */
@Configuration
@EnableNeo4jRepositories
public class Neo4jConfig extends Neo4jConfiguration {

    public Neo4jConfig() {
        setBasePackage("conference");
    }

    @Bean
    GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("conference.db");
    }
}
