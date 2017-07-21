package mx.infotec.dads.sekc.util;

import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.model.foundation.extention.SEAreaOfConcern;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.config.dbmigrations.domain.ActivitySpace;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Alpha;

/**
 * SEEssenceMapper Mapper Used for PracticeDto mapping
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class SEEssenceMapper {

    private SEEssenceMapper() {

    }

    public static SEPractice SEPractice(PracticeDto from) {
        SEPractice to = new SEPractice();
        to.setOwner(EntityBuilder.build(p -> p.setId(from.getIdKernel())));
        to.setName(from.getName());
        to.setBriefDescription(from.getBriefDesciption());
        to.setDescription(from.getDescription());
        to.setAuthor(from.getAuthor());
        to.setBriefDescription(from.getBriefDesciption());
        to.setConsistencyRules(from.getConsistencyRules());
        to.setKeyWords(from.getKeywords());
        to.setObjective(from.getObjective());
        return to;
    }
    // /**
    // * Convert an AreaOfConcern entity to SEAreaOfConcern entity
    // *
    // * @param from
    // * @return SEAreaOfConcern
    // */
    // public static SEAreaOfConcern toSEAreaOfConcern(PracticeDto from) {
    // SEAreaOfConcern to = new SEAreaOfConcern();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // to.setBriefDescription(from.getBriefDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an Checkpoint entity to SECheckpoint entity
    // *
    // * @param from
    // * @return SECheckpoint
    // */
    // public static SECheckpoint toSECheckpoint(PracticeDto from) {
    // SECheckpoint to = new SECheckpoint();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // to.setShortDescription(from.getBriefDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an Alpha entity to SEAlpha entity
    // *
    // * @param from
    // * @return Alpha
    // */
    // public static SEAlpha toSEAlpha(PracticeDto from) {
    // SEAlpha to = new SEAlpha();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // to.setBriefDescription(from.getBriefDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an State entity to SEState entity
    // *
    // * @param from
    // * @return SEState
    // */
    // public static SEState toSEState(PracticeDto from) {
    // SEState to = new SEState();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an WorkProduct entity to SEWorkProduct entity
    // *
    // * @param from
    // * @return SEWorkProduct
    // */
    // public static SEWorkProduct toSEWorkproduct(PracticeDto from) {
    // SEWorkProduct to = new SEWorkProduct();
    // to.setName(from.getName());
    // to.setBriefDescription(from.getBriefDescription());
    // to.setDescription(from.getDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an LevelOfDetail entity to SELevelOfDetail entity
    // *
    // * @param from
    // * @return SELevelOfDetail
    // */
    // public static SELevelOfDetail toSELevelOfDetail(PracticeDto from) {
    // SELevelOfDetail to = new SELevelOfDetail();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // to.setSufficientLevel(from.getIsSufficientLevel());
    // return to;
    // }
    //
    // /**
    // * Convert an ActivitySpace entity to SEActivitySpace entity
    // *
    // * @param from
    // * @return SEActivitySpace
    // */
    // public static SEActivitySpace toSEActivitySpace(PracticeDto from) {
    // SEActivitySpace to = new SEActivitySpace();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // to.setBriefDescription(from.getBriefDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an Competency entity to SECompetency entity
    // *
    // * @param from
    // * @return SECompetency
    // */
    // public static SECompetency toSECompetency(PracticeDto from) {
    // SECompetency to = new SECompetency();
    // to.setName(from.getName());
    // to.setDescription(from.getDescription());
    // to.setBriefDescription(from.getBriefDescription());
    // return to;
    // }
    //
    // /**
    // * Convert an CompetencyLevel entity to SECompetencyLevel entity
    // *
    // * @param from
    // * @return SECompetencyLevel
    // */
    // public static SECompetencyLevel toSECompetencyLevel(PracticeDto from) {
    // SECompetencyLevel to = new SECompetencyLevel();
    // to.setName(from.getName());
    // to.setBriefDescription(from.getBriefDescription());
    // to.setLevel(from.getLevel());
    // return to;
    // }

}
