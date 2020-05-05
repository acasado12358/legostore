package rc.legostore;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class LegostoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegostoreApplication.class, args);
    }

    //-----------------------------------------------------------------------------
    // SECTION 7  video 35 Mongobee for maintainening consistence between java and DB
    //
    @Autowired
    MongoTemplate mongoTemplate;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee("mongodb://localhost:27017/legostore");
        runner.setMongoTemplate(mongoTemplate);
        runner.setChangeLogsScanPackage("rc.legostore.persistence"); // all clasees that contain data migration can be stored in this package

        return runner;
    }
}
