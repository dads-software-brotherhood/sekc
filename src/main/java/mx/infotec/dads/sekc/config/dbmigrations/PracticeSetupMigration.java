package mx.infotec.dads.sekc.config.dbmigrations;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.config.dbmigrations.util.MigrationException;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "003")
public class PracticeSetupMigration {

    private final Logger log = LoggerFactory.getLogger(PracticeSetupMigration.class);

    @ChangeSet(order = "01", author = "initiator", id = "04-create-practice-example")
    public void createPractice(MongoTemplate mongoTemplate) {
        log.info("MIGRATING PRACTICE...");
        String kernelUrl = "https://gist.githubusercontent.com/danimaniarqsoft/6813e7c27d5b42bd3eda2844e87b107e/raw/b348b061f77a11de323a2852e95e44396b793ded/kernel.js";
        ObjectMapper mapper = new ObjectMapper();
        try {
            PracticeDto practiceDto = mapper.readValue(new URL(kernelUrl), PracticeDto.class);
            // mongoTemplate.save(seKernel);
        } catch (IOException e) {
            log.error("Creating Kernel Error:", e);
            throw new MigrationException("Creating Kernel Error", e);
        }
    }
}
