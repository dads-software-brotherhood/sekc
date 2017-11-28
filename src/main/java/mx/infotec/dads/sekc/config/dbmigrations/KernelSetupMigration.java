package mx.infotec.dads.sekc.config.dbmigrations;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProductManifest;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.model.foundation.extention.SEColor;
import mx.infotec.dads.essence.model.foundation.extention.SEAreaOfConcern;
import mx.infotec.dads.sekc.config.dbmigrations.domain.ActivitySpace;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Alpha;
import mx.infotec.dads.sekc.config.dbmigrations.domain.AreasOfConcern;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Checkpoint;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Competency;
import mx.infotec.dads.sekc.config.dbmigrations.domain.CompetencyLevel;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Kernel;
import mx.infotec.dads.sekc.config.dbmigrations.domain.KernelMigration;
import mx.infotec.dads.sekc.config.dbmigrations.domain.LevelOfDetail;
import mx.infotec.dads.sekc.config.dbmigrations.util.MigrationException;
import mx.infotec.dads.sekc.config.dbmigrations.util.MigrationMapper;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "002")
public class KernelSetupMigration {

    private final Logger log = LoggerFactory.getLogger(KernelSetupMigration.class);

    @ChangeSet(order = "01", author = "initiator", id = "03-createAlphasStatesCheckPointsCatalogs")
    public void createKernel(MongoTemplate mongoTemplate) {
       // String kernelUrl = "https://gist.githubusercontent.com/danimaniarqsoft/6813e7c27d5b42bd3eda2844e87b107e/raw/b348b061f77a11de323a2852e95e44396b793ded/kernel.js";
    	// String kernelUrl = "https://raw.githubusercontent.com/clara2108/Files/master/kernel.js";
    	String kernelUrl = "https://gist.githubusercontent.com/mosbaldo/e46e1b03e374a723a602418bbe985bbf/raw/2bd205a547ec83676b19b58d45be9f88679671cc/kernel.js";
        ObjectMapper mapper = new ObjectMapper();
        try {
            KernelMigration kernelMigration = mapper.readValue(new URL(kernelUrl), KernelMigration.class);
            Kernel kernel = kernelMigration.getKernel();
            SEKernel seKernel = MigrationMapper.toSEKernel(kernel);
            seKernel.setOwnedElements(new ArrayList<>());
            seKernel.getOwnedElements().addAll(migrateAreasOfConcern(mongoTemplate, kernel.getAreasOfConcern()));
            mongoTemplate.save(seKernel);
            //migratePractices(mongoTemplate); 
        } catch (IOException e) {
            log.error("Creating Kernel Error:", e);
            throw new MigrationException("Creating Kernel Error", e);
        }
    }

    /**
     * Migrate AreasOfConcern
     * 
     * @param mongoTemplate
     * @param areasOfConcerns
     * @return List<SEAreaOfConcern>
     */
    private List<SEAreaOfConcern> migrateAreasOfConcern(MongoTemplate mongoTemplate,
            List<AreasOfConcern> areasOfConcerns) {
        List<SEAreaOfConcern> areaOfConcernList = new ArrayList<>();
        areasOfConcerns.forEach(area -> {
            SEAreaOfConcern seAreaOfConcern = MigrationMapper.toSEAreaOfConcern(area);
            seAreaOfConcern.setOwnedElements(new ArrayList<>());
            seAreaOfConcern.getOwnedElements().addAll(migrateAlphas(mongoTemplate, area.getAlphas()));
            seAreaOfConcern.getOwnedElements().addAll(migrateActivitySpaces(mongoTemplate, area.getActivitySpaces()));
            seAreaOfConcern.getOwnedElements().addAll(migrateCompetencies(mongoTemplate, area.getCompetencies()));
            SEColor color = new SEColor();
            color.setName(area.getColor().getName());
            color.setHexadecimal(area.getColor().getHexadecimal());
            seAreaOfConcern.setColor(color);
            mongoTemplate.save(seAreaOfConcern);
            areaOfConcernList.add(seAreaOfConcern);
        });
        return areaOfConcernList;
    }

    /**
     * Migrate ActivitySpace
     * 
     * @param mongoTemplate
     * @param activitySpaces
     * @return List<SEActivitySpace>
     */
    private List<SEActivitySpace> migrateActivitySpaces(MongoTemplate mongoTemplate,
            List<ActivitySpace> activitySpaces) {
        List<SEActivitySpace> activitySpaceList = new ArrayList<>();
        activitySpaces.forEach(activitySpace -> {
            SEActivitySpace seActivitySpace = MigrationMapper.toSEActivitySpace(activitySpace);
            mongoTemplate.save(seActivitySpace);
            activitySpaceList.add(seActivitySpace);
        });
        return activitySpaceList;
    }

    /**
     * Migrate Competencies
     * 
     * @param mongoTemplate
     * @param competencies
     * @return List<SECompetency>
     */
    private List<SECompetency> migrateCompetencies(MongoTemplate mongoTemplate, List<Competency> competencies) {
        List<SECompetency> competencyList = new ArrayList<>();
        competencies.forEach(competency -> {
            SECompetency seCompetency = MigrationMapper.toSECompetency(competency);
            seCompetency.setPossibleLevel(new ArrayList<>());
            mongoTemplate.save(seCompetency);
            seCompetency.getPossibleLevel()
                    .addAll(migrateCompetencyLevel(mongoTemplate, competency.getCompetencyLevels(), seCompetency));
            mongoTemplate.save(seCompetency);
            competencyList.add(seCompetency);
        });
        return competencyList;
    }

    /**
     * Migrate CompetencyLevel
     * 
     * @param mongoTemplate
     * @param competencyLevels
     * @return List<SECompetencyLevel>
     */
    private List<SECompetencyLevel> migrateCompetencyLevel(MongoTemplate mongoTemplate,
            List<CompetencyLevel> competencyLevels, SECompetency seCompetency) {
        List<SECompetencyLevel> competencyLevelList = new ArrayList<>();
        competencyLevels.forEach(competencyLevel -> {
            SECompetencyLevel seCompetencyLevel = MigrationMapper.toSECompetencyLevel(competencyLevel);
            seCompetencyLevel.setChecklistItem(new ArrayList<>());
            seCompetencyLevel.getChecklistItem()
                    .addAll(migrateCheckpoints(mongoTemplate, competencyLevel.getCheckpoints()));
            seCompetencyLevel.setCompetency(seCompetency);
            mongoTemplate.save(seCompetencyLevel);
            competencyLevelList.add(seCompetencyLevel);
        });
        return competencyLevelList;
    }

    /**
     * Migrate Alphas
     * 
     * @param mongoTemplate
     * @param alphas
     * @return List<SEAlpha>
     */
    private List<SEAlpha> migrateAlphas(MongoTemplate mongoTemplate, List<Alpha> alphas) {
        List<SEAlpha> alphaList = new ArrayList<>();
        alphas.forEach(alpha -> {
            SEAlpha seAlpha = MigrationMapper.toSEAlpha(alpha);
            mongoTemplate.save(seAlpha);// without states
            seAlpha.setStates(migrateStates(mongoTemplate, alpha, seAlpha));
            mongoTemplate.save(seAlpha);// with states & without workproducts
            List<SEWorkProduct> seWorkProductList = migrateWorkProduct(mongoTemplate, alpha);
            List<SEWorkProductManifest> migrateWorkProductManifest = migrateWorkProductManifest(mongoTemplate, seAlpha,
                    seWorkProductList);
            seAlpha.setWorkProductManifest(migrateWorkProductManifest);
            mongoTemplate.save(seAlpha);// with workProduct Manifest and
                                        // workproducts
            alphaList.add(seAlpha);
        });
        return alphaList;
    }

    /**
     * Migrate WorkProducts
     * 
     * @param mongoTemplate
     * @param alpha
     * @return List<SEWorkProduct>
     */
    private List<SEWorkProduct> migrateWorkProduct(MongoTemplate mongoTemplate, Alpha alpha) {
        List<SEWorkProduct> seWorkProductList = new ArrayList<>();
        alpha.getWorkProducts().forEach(workProduct -> {
            SEWorkProduct seWorkproduct = MigrationMapper.toSEWorkproduct(workProduct);
            mongoTemplate.save(seWorkproduct); // without LevelOfDetail
            seWorkproduct.setLevelOfDetail(migrateLevelOfDetails(mongoTemplate, workProduct.getLevelOfDetails(), seWorkproduct));
            mongoTemplate.save(seWorkproduct); // with LevelOfDetail
            seWorkProductList.add(seWorkproduct);
        });
        return seWorkProductList;
    }

    /**
     * Migrate WorkProductsManifest
     * 
     * @param mongoTemplate
     * @param alpha
     * @param workProducts
     * @return List<SEWorkProductManifest>
     */
    private List<SEWorkProductManifest> migrateWorkProductManifest(MongoTemplate mongoTemplate, SEAlpha alpha,
            List<SEWorkProduct> workProducts) {
        Objects.requireNonNull(alpha.getId(), "No {id} provided for this Alpha");
        List<SEWorkProductManifest> seWorkProductManifestList = new ArrayList<>();
        workProducts.forEach(workProduct -> {
            Objects.requireNonNull(workProduct.getId(), "No {id} provided for this WorkProduct");
            SEWorkProductManifest seWorkproductManifest = new SEWorkProductManifest();
            seWorkproductManifest.setAlpha(alpha);
            seWorkproductManifest.setWorkProduct(workProduct);
            mongoTemplate.save(seWorkproductManifest);
            seWorkProductManifestList.add(seWorkproductManifest);
        });
        return seWorkProductManifestList;
    }

    /**
     * MigrateLevelOfDetail
     * 
     * @param mongoTemplate
     * @param levelOfDetails
     * @return List<SELevelOfDetail>
     */
    private List<SELevelOfDetail> migrateLevelOfDetails(MongoTemplate mongoTemplate,
            List<LevelOfDetail> levelOfDetails, SEWorkProduct seWorkProduct) {
        List<SELevelOfDetail> seLevelOfDetails = new ArrayList<>();
        levelOfDetails.forEach(levelOfDetail -> {
            SELevelOfDetail seLevelOfDetail = MigrationMapper.toSELevelOfDetail(levelOfDetail);
            seLevelOfDetail.setCheckListItem(migrateCheckpoints(mongoTemplate, levelOfDetail.getCheckpoints()));
            seLevelOfDetail.setWorkProduct(seWorkProduct);
            mongoTemplate.save(seLevelOfDetail);
            seLevelOfDetails.add(seLevelOfDetail);
        });
        return seLevelOfDetails;
    }

    /**
     * Miagrate States
     * 
     * @param mongoTemplate
     * @param alpha
     * @return List<SEState>
     */
    private List<SEState> migrateStates(MongoTemplate mongoTemplate, Alpha alpha, SEAlpha seAlpha) {
        List<SEState> seStateList = new ArrayList<>();
        alpha.getStates().forEach(state -> {
            SEState seState = MigrationMapper.toSEState(state);
            seState.setAlpha(seAlpha);
            mongoTemplate.save(seState); //without checklists
            seState.setCheckListItem(migrateCheckpoints(mongoTemplate, state.getCheckpoints(), seState));
            mongoTemplate.save(seState); //with checklist
            seStateList.add(seState);
        });
        return seStateList;
    }

    /**
     * Miagrate Checkpoins
     * 
     * @param mongoTemplate
     * @param checkpoints
     * @return List<SECheckpoint>
     */

    private List<SECheckpoint> migrateCheckpoints(MongoTemplate mongoTemplate, List<Checkpoint> checkpoints) {
        List<SECheckpoint> seCheckpointList = new ArrayList<>();
        checkpoints.forEach(checkpoint -> {
            SECheckpoint seCheckpoint = MigrationMapper.toSECheckpoint(checkpoint);
            mongoTemplate.save(seCheckpoint);
            seCheckpointList.add(seCheckpoint);
        });
        return seCheckpointList;
    }
    /**
     * This is for state-checkpoint relationship
    */
    private List<SECheckpoint> migrateCheckpoints(MongoTemplate mongoTemplate, List<Checkpoint> checkpoints, SEState seState) {
        List<SECheckpoint> seCheckpointList = new ArrayList<>();
        checkpoints.forEach(checkpoint -> {
            SECheckpoint seCheckpoint = MigrationMapper.toSECheckpoint(checkpoint);
            seCheckpoint.setState(seState);
            mongoTemplate.save(seCheckpoint);
            seCheckpointList.add(seCheckpoint);
        });
        return seCheckpointList;
    }

    /**
     * Miagrate Test Practices
     * 
     * @param mongoTemplate
     * @param checkpoints
     * @return List<SECheckpoint>
     */

    private void migratePractices(MongoTemplate mongoTemplate) {

        for (int i = 0; i < 1000; i++) {
            SEPractice practice = new SEPractice();
            practice.setName("Practice Example "+i);
            practice.setBriefDescription("Arquitectura de software");
            practice.setDescription("La arquitectura de software es una de las mejores del mundo");
            practice.setObjective("el objetivo es implementar el diseño de una práctica de software para el mundo");
            practice.setKeyWords(Arrays.asList("arquitectura", "innovation"));
            mongoTemplate.save(practice);
        }
    }
}
