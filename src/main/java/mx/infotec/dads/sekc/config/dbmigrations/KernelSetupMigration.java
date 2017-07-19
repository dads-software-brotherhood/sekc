package mx.infotec.dads.sekc.config.dbmigrations;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.model.foundation.SEKernel;
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
import mx.infotec.dads.sekc.config.dbmigrations.domain.State;
import mx.infotec.dads.sekc.config.dbmigrations.domain.WorkProduct;
import mx.infotec.dads.sekc.config.dbmigrations.util.MigrationMapper;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "002")
public class KernelSetupMigration {

	private final Logger log = LoggerFactory.getLogger(KernelSetupMigration.class);

	@ChangeSet(order = "01", author = "initiator", id = "03-createAlphasStatesCheckPointsCatalogs")
	public void createKernel(MongoTemplate mongoTemplate) {
		String kernelUrl = "https://gist.githubusercontent.com/danimaniarqsoft/6813e7c27d5b42bd3eda2844e87b107e/raw/4d50f9d651e22b2fbf2c231e5d618baeb2d00fc6/kernel.js";
		ObjectMapper mapper = new ObjectMapper();
		try {
			KernelMigration kernelMigration = mapper.readValue(new URL(kernelUrl), KernelMigration.class);
			Kernel kernel = kernelMigration.getKernel();
			List<SEAreaOfConcern> areaOfConcernList = new ArrayList<>();
			migrateAreasOfConcern(mongoTemplate, kernel.getAreasOfConcern(), areaOfConcernList);
			SEKernel seKernel = MigrationMapper.toSEKernel(kernel);
			seKernel.setOwnedElements(new ArrayList<>());
			seKernel.getOwnedElements().addAll(areaOfConcernList);
			mongoTemplate.save(seKernel);
		} catch (IOException e) {
			log.error("Creating Kernel Error:", e);
		}
	}

	private void migrateAreasOfConcern(MongoTemplate mongoTemplate, List<AreasOfConcern> areasOfConcerns,
			List<SEAreaOfConcern> areaOfConcernList) {
		for (AreasOfConcern areaOfConcern : areasOfConcerns) {
			List<SEAlpha> alphaList = new ArrayList<>();
			List<SEActivitySpace> activitySpaceList = new ArrayList<>();
			List<SECompetency> competencyList = new ArrayList<>();
			migrateAlphas(mongoTemplate, areaOfConcern.getAlphas(), alphaList);
			migrateActivitySpaces(mongoTemplate, areaOfConcern.getActivitySpaces(), activitySpaceList);
			migrateCompetencies(mongoTemplate, areaOfConcern.getCompetencies(), competencyList);
			SEAreaOfConcern seAreaOfConcern = MigrationMapper.toSEAreaOfConcern(areaOfConcern);
			seAreaOfConcern.setOwnedElements(new ArrayList<>());
			seAreaOfConcern.getOwnedElements().addAll(alphaList);
			seAreaOfConcern.getOwnedElements().addAll(activitySpaceList);
			seAreaOfConcern.getOwnedElements().addAll(competencyList);
			mongoTemplate.save(seAreaOfConcern);
			areaOfConcernList.add(seAreaOfConcern);
		}
	}

	private void migrateActivitySpaces(MongoTemplate mongoTemplate, List<ActivitySpace> activitySpaces,
			List<SEActivitySpace> activitySpaceList) {
		for (ActivitySpace activitySpace : activitySpaces) {
			SEActivitySpace seActivitySpace = MigrationMapper.toSEActivitySpace(activitySpace);
			mongoTemplate.save(seActivitySpace);
			activitySpaceList.add(seActivitySpace);
		}
	}

	private void migrateCompetencies(MongoTemplate mongoTemplate, List<Competency> competencies,
			List<SECompetency> competencyList) {
		for (Competency competency : competencies) {
			List<SECompetencyLevel> competencyLevelList = new ArrayList<>();
			migrateCompetencyLevel(mongoTemplate, competency.getCompetencyLevels(), competencyLevelList);
			SECompetency seCompetency = MigrationMapper.toSECompetency(competency);
			seCompetency.setPossibleLevel(new ArrayList<>());
			seCompetency.getPossibleLevel().addAll(competencyLevelList);
			mongoTemplate.save(seCompetency);
			competencyList.add(seCompetency);

		}
	}

	private void migrateCompetencyLevel(MongoTemplate mongoTemplate, List<CompetencyLevel> competencyLevels,
			List<SECompetencyLevel> competencyLevelList) {
		for (CompetencyLevel competencyLevel : competencyLevels) {
			List<SECheckpoint> seCheckpointList = new ArrayList<>();
			migrateCheckpoints(mongoTemplate, competencyLevel.getCheckpoints(), seCheckpointList);
			SECompetencyLevel seCompetencyLevel = MigrationMapper.toSECompetencyLevel(competencyLevel);
			seCompetencyLevel.setChecklistItem(new ArrayList<>());
			seCompetencyLevel.getChecklistItem().addAll(seCheckpointList);
			mongoTemplate.save(seCompetencyLevel);
			competencyLevelList.add(seCompetencyLevel);
		}
	}

	private void migrateAlphas(MongoTemplate mongoTemplate, List<Alpha> alphas, List<SEAlpha> alphaList) {
		for (Alpha alpha : alphas) {
			List<SEState> seStateList = new ArrayList<>();
			List<SEWorkProduct> seWorkProduct = new ArrayList<>();
			migrateStates(mongoTemplate, alpha, seStateList);
			migrateWorkProduct(mongoTemplate, alpha, seWorkProduct);
			SEAlpha seAlpha = MigrationMapper.toSEAlpha(alpha);
			seAlpha.setStates(seStateList);
			mongoTemplate.save(seAlpha);
			alphaList.add(seAlpha);
		}
	}

	private void migrateWorkProduct(MongoTemplate mongoTemplate, Alpha alpha, List<SEWorkProduct> seWorkProductList) {
		for (WorkProduct workProduct : alpha.getWorkProducts()) {
			List<SELevelOfDetail> seLevelOfDetail = new ArrayList<>();
			migrateLevelOfDetails(mongoTemplate, workProduct.getLevelOfDetails(), seLevelOfDetail);
			SEWorkProduct seWorkproduct = MigrationMapper.toSEWorkproduct(workProduct);
			seWorkproduct.setLevelOfDetail(seLevelOfDetail);
			mongoTemplate.save(seWorkproduct);
			seWorkProductList.add(seWorkproduct);
		}
	}

	private void migrateLevelOfDetails(MongoTemplate mongoTemplate, List<LevelOfDetail> levelOfDetails,
			List<SELevelOfDetail> seLevelOfDetails) {
		for (LevelOfDetail levelOfDetail : levelOfDetails) {
			List<SECheckpoint> seCheckpointList = new ArrayList<>();
			migrateCheckpoints(mongoTemplate, levelOfDetail.getCheckpoints(), seCheckpointList);
			SELevelOfDetail seLevelOfDetail = MigrationMapper.toSELevelOfDetail(levelOfDetail);
			seLevelOfDetail.setCheckListItem(seCheckpointList);
			mongoTemplate.save(seLevelOfDetail);
			seLevelOfDetails.add(seLevelOfDetail);
		}

	}

	private void migrateStates(MongoTemplate mongoTemplate, Alpha alpha, List<SEState> seStateList) {
		for (State state : alpha.getStates()) {
			List<SECheckpoint> seCheckpointList = new ArrayList<>();
			migrateCheckpoints(mongoTemplate, state.getCheckpoints(), seCheckpointList);
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
