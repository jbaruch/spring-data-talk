package conference;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

/**
 * @author Jeka
 * @since 07/10/2014
 */
@Configuration
@EnableJpaRepositories
public class JpaConfig {
    @Bean
    public DriverManagerDataSource conferenceDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:conference;create=true");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(conferenceDataSource());
        emFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        emFactory.setJpaProperties(jpaProperties);
        emFactory.setPackagesToScan("conference");
        return emFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
