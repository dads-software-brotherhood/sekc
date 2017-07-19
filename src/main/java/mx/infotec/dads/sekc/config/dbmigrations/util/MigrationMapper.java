package mx.infotec.dads.sekc.config.dbmigrations.util;

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
import mx.infotec.dads.sekc.config.dbmigrations.domain.LevelOfDetail;
import mx.infotec.dads.sekc.config.dbmigrations.domain.State;
import mx.infotec.dads.sekc.config.dbmigrations.domain.WorkProduct;

/**
 * Migration Mapper Used for datamigration mapping
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class MigrationMapper {

    private MigrationMapper() {

    }

    public static SEKernel toSEKernel(Kernel from) {
        SEKernel to = new SEKernel();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    public static SEAreaOfConcern toSEAreaOfConcern(AreasOfConcern from) {
        SEAreaOfConcern to = new SEAreaOfConcern();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    public static SECheckpoint toSECheckpoint(Checkpoint from) {
        SECheckpoint to = new SECheckpoint();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setShortDescription(from.getBriefDescription());
        return to;
    }

    public static SEAlpha toSEAlpha(Alpha from) {
        SEAlpha to = new SEAlpha();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    public static SEState toSEState(State from) {
        SEState to = new SEState();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        return to;
    }

    public static SEWorkProduct toSEWorkproduct(WorkProduct from) {
        SEWorkProduct to = new SEWorkProduct();
        to.setName(from.getName());
        to.setBriefDescription(from.getBriefDescription());
        to.setDescription(from.getDescription());
        return to;
    }

    public static SELevelOfDetail toSELevelOfDetail(LevelOfDetail from) {
        SELevelOfDetail to = new SELevelOfDetail();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setSufficientLevel(from.getIsSufficientLevel());
        return to;
    }

    public static SEActivitySpace toSEActivitySpace(ActivitySpace from) {
        SEActivitySpace to = new SEActivitySpace();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    public static SECompetency toSECompetency(Competency from) {
        SECompetency to = new SECompetency();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    public static SECompetencyLevel toSECompetencyLevel(CompetencyLevel from) {
        SECompetencyLevel to = new SECompetencyLevel();
        to.setName(from.getName());
        to.setBriefDescription(from.getBriefDescription());
        to.setLevel(from.getLevel());
        return to;
    }

}
