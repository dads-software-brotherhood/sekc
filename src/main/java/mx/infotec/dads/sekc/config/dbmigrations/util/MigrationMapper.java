package mx.infotec.dads.sekc.config.dbmigrations.util;

import mx.infotec.dads.essence.model.SEGraphicalElement;
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

    /**
     * Convert an Kernel entity to SEKernel entity
     * 
     * @param from
     * @return SEKernel
     */
    public static SEKernel toSEKernel(Kernel from) {
        SEKernel to = new SEKernel();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    /**
     * Convert an AreaOfConcern entity to SEAreaOfConcern entity
     * 
     * @param from
     * @return SEAreaOfConcern
     */
    public static SEAreaOfConcern toSEAreaOfConcern(AreasOfConcern from) {
        SEAreaOfConcern to = new SEAreaOfConcern();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        return to;
    }

    /**
     * Convert an Checkpoint entity to SECheckpoint entity
     * 
     * @param from
     * @return SECheckpoint
     */
    public static SECheckpoint toSECheckpoint(Checkpoint from) {
        SECheckpoint to = new SECheckpoint();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setShortDescription(from.getBriefDescription());
        return to;
    }

    /**
     * Convert an Alpha entity to SEAlpha entity
     * 
     * @param from
     * @return Alpha
     */
    public static SEAlpha toSEAlpha(Alpha from, SEGraphicalElement icon) {
        SEAlpha to = new SEAlpha();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        to.setIcon(icon);
        return to;
    }

    /**
     * Convert an State entity to SEState entity
     * 
     * @param from
     * @return SEState
     */
    public static SEState toSEState(State from) {
        SEState to = new SEState();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        return to;
    }

    /**
     * Convert an WorkProduct entity to SEWorkProduct entity
     * 
     * @param from
     * @return SEWorkProduct
     */
    public static SEWorkProduct toSEWorkproduct(WorkProduct from, SEGraphicalElement icon) {
        SEWorkProduct to = new SEWorkProduct();
        to.setName(from.getName());
        to.setBriefDescription(from.getBriefDescription());
        to.setDescription(from.getDescription());
        to.setIcon(icon);
        return to;
    }

    /**
     * Convert an LevelOfDetail entity to SELevelOfDetail entity
     * 
     * @param from
     * @return SELevelOfDetail
     */
    public static SELevelOfDetail toSELevelOfDetail(LevelOfDetail from) {
        SELevelOfDetail to = new SELevelOfDetail();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setSufficientLevel(from.getIsSufficientLevel());
        return to;
    }

    /**
     * Convert an ActivitySpace entity to SEActivitySpace entity
     * 
     * @param from
     * @return SEActivitySpace
     */
    public static SEActivitySpace toSEActivitySpace(ActivitySpace from, SEGraphicalElement icon) {
        SEActivitySpace to = new SEActivitySpace();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        to.setIcon(icon);
        return to;
    }

    /**
     * Convert an Competency entity to SECompetency entity
     * 
     * @param from
     * @return SECompetency
     */
    public static SECompetency toSECompetency(Competency from, SEGraphicalElement icon) {
        SECompetency to = new SECompetency();
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setBriefDescription(from.getBriefDescription());
        to.setIcon(icon);
        return to;
    }

    /**
     * Convert an CompetencyLevel entity to SECompetencyLevel entity
     * 
     * @param from
     * @return SECompetencyLevel
     */
    public static SECompetencyLevel toSECompetencyLevel(CompetencyLevel from) {
        SECompetencyLevel to = new SECompetencyLevel();
        to.setName(from.getName());
        to.setBriefDescription(from.getBriefDescription());
        to.setLevel(from.getLevel());
        return to;
    }

}
