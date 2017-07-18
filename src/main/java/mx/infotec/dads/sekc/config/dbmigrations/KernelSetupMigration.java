package mx.infotec.dads.sekc.config.dbmigrations;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Alpha;
import mx.infotec.dads.sekc.config.dbmigrations.domain.AreasOfConcern;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Checkpoint;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Kernel;
import mx.infotec.dads.sekc.config.dbmigrations.domain.KernelMigration;
import mx.infotec.dads.sekc.config.dbmigrations.domain.LevelOfDetail;
import mx.infotec.dads.sekc.config.dbmigrations.domain.State;
import mx.infotec.dads.sekc.config.dbmigrations.domain.WorkProduct;
import mx.infotec.dads.sekc.config.dbmigrations.util.MigrationMapper;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "002")
public class KernelSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "03-createAlphasStatesCheckPointsCatalogs")
    public void createAlphasStatesCheckPointsCatalogs(MongoTemplate mongoTemplate) {
        String kernelUrl = "https://gist.githubusercontent.com/danimaniarqsoft/6813e7c27d5b42bd3eda2844e87b107e/raw/3c2062ea7e722b81b2480de23c4116a8d385112d/kernel.js";
        ObjectMapper mapper = new ObjectMapper();
        try {
            KernelMigration kernelMigration = mapper.readValue(new URL(kernelUrl), KernelMigration.class);
            Kernel kernel = kernelMigration.getKernel();
            List<AreasOfConcern> areasOfConcerns = kernel.getAreasOfConcern();
            migrateAreasOfConcern(mongoTemplate, areasOfConcerns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void migrateAreasOfConcern(MongoTemplate mongoTemplate, List<AreasOfConcern> areasOfConcerns) {
        for (AreasOfConcern areaOfConcern : areasOfConcerns) {
            List<Alpha> alphas = areaOfConcern.getAlphas();
            migrateAlphas(mongoTemplate, alphas);
        }
    }

    private void migrateAlphas(MongoTemplate mongoTemplate, List<Alpha> alphas) {
        for (Alpha alpha : alphas) {
            List<SEState> seStateList = new ArrayList<>();
            List<SEWorkProduct> seWorkProduct = new ArrayList<>();
            migrateStates(mongoTemplate, alpha, seStateList);
            migrateWorkProduct(mongoTemplate, alpha, seWorkProduct);
            SEAlpha seAlpha = MigrationMapper.toSEAlpha(alpha);
            seAlpha.setStates(seStateList);
            mongoTemplate.save(seAlpha);
        }
    }

    private void migrateWorkProduct(MongoTemplate mongoTemplate, Alpha alpha, List<SEWorkProduct> seWorkProductList) {
        for (WorkProduct workProduct : alpha.getWorkProducts()) {
            List<LevelOfDetail> levelOfDetail = workProduct.getLevelOfDetails();
            List<SELevelOfDetail> seLevelOfDetail = new ArrayList<>();
            migrateLevelOfDetails(mongoTemplate, levelOfDetail, seLevelOfDetail);
            SEWorkProduct seWorkproduct = MigrationMapper.toSEWorkproduct(workProduct);
            seWorkproduct.setLevelOfDetail(seLevelOfDetail);
            mongoTemplate.save(seWorkproduct);
            seWorkProductList.add(seWorkproduct);
        }
    }

    private void migrateLevelOfDetails(MongoTemplate mongoTemplate, List<LevelOfDetail> levelOfDetails,
            List<SELevelOfDetail> seLevelOfDetails) {
        for (LevelOfDetail levelOfDetail : levelOfDetails) {
            List<Checkpoint> checkpoints = levelOfDetail.getCheckpoints();
            List<SECheckpoint> seCheckpointList = new ArrayList<>();
            migrateCheckpoints(mongoTemplate, checkpoints, seCheckpointList);
            SELevelOfDetail seLevelOfDetail = MigrationMapper.toSELevelOfDetail(levelOfDetail);
            seLevelOfDetail.setCheckListItem(seCheckpointList);
            mongoTemplate.save(seLevelOfDetail);
            seLevelOfDetails.add(seLevelOfDetail);
        }

    }

    private void migrateStates(MongoTemplate mongoTemplate, Alpha alpha, List<SEState> seStateList) {
        for (State state : alpha.getStates()) {
            List<Checkpoint> checkpoints = state.getCheckpoints();
            List<SECheckpoint> seCheckpointList = new ArrayList<>();
            migrateCheckpoints(mongoTemplate, checkpoints, seCheckpointList);
            SEState seState = MigrationMapper.toSEState(state);
            seState.setCheckListItem(seCheckpointList);
            mongoTemplate.save(seState);
            seStateList.add(seState);
        }
    }

    private void migrateCheckpoints(MongoTemplate mongoTemplate, List<Checkpoint> checkpoints,
            List<SECheckpoint> seCheckpointList) {
        for (Checkpoint checkpoint : checkpoints) {
            SECheckpoint seCheckpoint = MigrationMapper.toSECheckpoint(checkpoint);
            mongoTemplate.save(seCheckpoint);
            seCheckpointList.add(seCheckpoint);
        }
    }
}
