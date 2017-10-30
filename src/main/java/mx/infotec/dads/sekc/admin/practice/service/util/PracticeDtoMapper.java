package mx.infotec.dads.sekc.admin.practice.service.util;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEAction;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivity;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivityAssociation;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEApproach;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECompletionCriterion;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEEntryCriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SEKernel;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.model.foundation.SEResource;
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.essence.util.ResourcesTypes;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.practice.dto.Action;
import mx.infotec.dads.sekc.admin.practice.dto.Activity;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Approach;
import mx.infotec.dads.sekc.admin.practice.dto.Competency;
import mx.infotec.dads.sekc.admin.practice.dto.CompletitionCriterion;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.Criteriable;
import mx.infotec.dads.sekc.admin.practice.dto.Entries;
import mx.infotec.dads.sekc.admin.practice.dto.EntryCriterion;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.Resource;
import mx.infotec.dads.sekc.admin.practice.dto.Results;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToDo;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;
import static mx.infotec.dads.sekc.admin.practice.service.util.SEEssenceMapper.repoUtil;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateConditions;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateGeneralInformation;
import static mx.infotec.dads.sekc.admin.practice.validation.PracticeDtoValidation.validateWorkProductLevelOfDetailCriterion;
import mx.infotec.dads.sekc.exception.SekcException;
import mx.infotec.dads.sekc.util.EntityBuilder;
import mx.infotec.dads.sekc.util.EssenceFilter;
import org.omg.essence.model.activityspaceandactivity.ActionKind;

public class PracticeDtoMapper {

    private PracticeDtoMapper() {

    }

    /**
     * Mapper for convert Repository entity to PracticeDto
     * 
     * @param entity
     * @return PracticeDto
     */
    public static PracticeDto toDto(SEPractice entity) {
        PracticeDto dto = new PracticeDto();
        mapGeneralInfo(entity, dto);
        mapRelatedPractices(entity, dto);
        mapConditions(entity, dto);
        mapThingsToWorkWith(entity, dto);
        mapThingsToDo(entity, dto);
        return dto;
    }
    
    public static void mapGeneralInfo(SEPractice entity, PracticeDto dto ) {
        dto.setId(entity.getId());
        dto.setIdKernel( entity.getOwner().getId());
        dto.setName(entity.getName());
        dto.setObjective(entity.getObjective());
        dto.setBriefDesciption(entity.getBriefDescription());
        dto.setDescription(entity.getDescription());
        dto.setConsistencyRules(entity.getConsistencyRules());
        dto.setAuthor(entity.getAuthor());
        dto.setKeywords(entity.getKeyWords());
    }

    public static void mapConditions(SEPractice entity, PracticeDto dto ){//Conditions conditions, SEPractice to) {
        mapCriterios( entity.getEntryCriterion(), dto, true);
        mapCriterios( entity.getResultCriterion(), dto, false);
    }

    private static void mapCriterios(Collection<SECriterion> seCriterionList, PracticeDto dto, boolean isEntry) {
        dto.setConditions( new Conditions());
        dto.getConditions().setEntries(new Entries());
        dto.getConditions().setResults(new Results());
        mapAlphaCriterion(seCriterionList,  dto, isEntry);
        mapWorkProductCriterion(seCriterionList,  dto, isEntry);
        mapOtherCriterion(seCriterionList,  dto, isEntry);
    }
    
    private static void mapCriterios(Collection<SECriterion> seCriterionList, Activity dto, boolean isEntry) {
        dto.setEntryCriterion(new EntryCriterion());
        dto.setCompletitionCriterion(new CompletitionCriterion());
        mapAlphaCriterion(seCriterionList,  dto, isEntry);
        mapWorkProductCriterion(seCriterionList,  dto, isEntry);
        mapOtherCriterion(seCriterionList,  dto, isEntry);
    }

    private static void mapOtherCriterion(Collection<SECriterion> seCriterionList, PracticeDto dto,
            boolean isEntry) {
        if (isEntry){
            dto.getConditions().getEntries().setOtherConditions(new ArrayList<>());
            mapResultOtherEntryConditions(seCriterionList, dto);
        }else {
            dto.getConditions().getResults().setOtherConditions(new ArrayList<>());
            mapResultOtherResultConditions(seCriterionList, dto);
        }
    }
    
    private static void mapOtherCriterion(Collection<SECriterion> seCriterionList, Activity dto,
            boolean isEntry) {
        if (isEntry){
            dto.getEntryCriterion().setOtherConditions(new ArrayList<>());
            mapResultOtherEntryConditions(seCriterionList, dto);
        }else {
            dto.getCompletitionCriterion().setOtherConditions(new ArrayList<>());
            mapResultOtherResultConditions(seCriterionList, dto);
        }
    }

    private static void mapWorkProductCriterion(Collection<SECriterion> seCriterionList, PracticeDto dto,
            boolean isEntry) {
            if (isEntry) {
                dto.getConditions().getEntries().setWorkProductsLevelofDetail(new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultWorkProductLevelOfDetailEntryCriterion(entry, dto));
            } else {
                dto.getConditions().getResults().setWorkProductsLevelofDetail(new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultWorkProductLevelOfDetailResultCriterion(entry, dto));
            }
    }
    
    private static void mapWorkProductCriterion(Collection<SECriterion> seCriterionList, Activity dto,
            boolean isEntry) {
            if (isEntry) {
                dto.getEntryCriterion().setWorkProductsLevelofDetail(new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultWorkProductLevelOfDetailEntryCriterion(entry, dto));
            } else {
                dto.getCompletitionCriterion().setWorkProductsLevelofDetail(new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultWorkProductLevelOfDetailResultCriterion(entry, dto));
            }
    }

    private static void mapAlphaCriterion(Collection<SECriterion> seCriterionList, PracticeDto dto,
            boolean isEntry) {
            if (isEntry) {
                dto.getConditions().getEntries().setAlphaStates( new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultAlphaStatesEntryCriterion(entry, dto));
            } else {
                dto.getConditions().getResults().setAlphaStates( new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultAlphaStatesResultCriterion(entry, dto));
            }
    }
    
    private static void mapAlphaCriterion(Collection<SECriterion> seCriterionList, Activity dto,
            boolean isEntry) {
            if (isEntry) {
                dto.getEntryCriterion().setAlphaStates( new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultAlphaStatesEntryCriterion(entry, dto));
            } else {
                dto.getCompletitionCriterion().setAlphaStates( new ArrayList<>());
                seCriterionList.forEach(entry -> mapResultAlphaStatesResultCriterion(entry, dto));
            }
    }

    private static void mapResultAlphaStatesEntryCriterion(SECriterion criterion, PracticeDto dto) {
        if (criterion.getState() != null){    
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            dto.getConditions().getEntries().getAlphaStates().add(alphaState);
        }
    }
    
    private static void mapResultAlphaStatesEntryCriterion(SECriterion criterion, Activity dto) {
        if (criterion.getState() != null){    
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            dto.getEntryCriterion().getAlphaStates().add(alphaState);
        }
    }

    private static void mapResultAlphaStatesResultCriterion(SECriterion criterion, PracticeDto dto) {
        if (criterion.getState() != null){ 
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            dto.getConditions().getResults().getAlphaStates().add(alphaState);
        }
    }
    
    private static void mapResultAlphaStatesResultCriterion(SECriterion criterion, Activity dto) {
        if (criterion.getState() != null){ 
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            dto.getCompletitionCriterion().getAlphaStates().add(alphaState);
        }
    }

    private static void mapResultWorkProductLevelOfDetailEntryCriterion( SECriterion criterion, PracticeDto dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            dto.getConditions().getEntries().getWorkProductsLevelofDetail().add(wp);
        }
    }
    
     private static void mapResultWorkProductLevelOfDetailEntryCriterion( SECriterion criterion, Activity dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            dto.getEntryCriterion().getWorkProductsLevelofDetail().add(wp);
        }
    }

    private static void mapResultWorkProductLevelOfDetailResultCriterion( SECriterion criterion, PracticeDto dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            dto.getConditions().getResults().getWorkProductsLevelofDetail().add(wp);
        }
    }
    
    private static void mapResultWorkProductLevelOfDetailResultCriterion( SECriterion criterion, Activity dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            dto.getCompletitionCriterion().getWorkProductsLevelofDetail().add(wp);
        }
    }

    private static void mapResultOtherEntryConditions( Collection<SECriterion> criterionList, PracticeDto dto) {
        
        for (SECriterion criterion : criterionList){
            if (criterion.getOtherConditions() != null)
                dto.getConditions().getEntries().getOtherConditions().addAll( criterion.getOtherConditions() );
        }
    }
    
    private static void mapResultOtherEntryConditions( Collection<SECriterion> criterionList, Activity dto) {
        
        for (SECriterion criterion : criterionList){
            if (criterion.getOtherConditions() != null)
                dto.getEntryCriterion().getOtherConditions().addAll( criterion.getOtherConditions() );
        }
    }

    private static void mapResultOtherResultConditions( Collection<SECriterion> criterionList, PracticeDto dto) {
        
        for (SECriterion criterion : criterionList){
            if (criterion.getOtherConditions() != null)
                dto.getConditions().getResults().getOtherConditions().addAll( criterion.getOtherConditions() );
        }
    }
    
    private static void mapResultOtherResultConditions( Collection<SECriterion> criterionList, Activity dto) {
        
        for (SECriterion criterion : criterionList){
            if (criterion.getOtherConditions() != null)
                dto.getCompletitionCriterion().getOtherConditions().addAll( criterion.getOtherConditions() );
        }
    }

    public static void mapThingsToWorkWith(SEPractice entity, PracticeDto dto) {
        dto.setThingsToWorkWith( new ThingsToWorkWith());
        dto.getThingsToWorkWith().setAlphas(new ArrayList<>());
        dto.getThingsToWorkWith().setWorkProducts(new ArrayList<>());
        
        EssenceFilter.filterLanguageElement( entity.getReferredElements(), SEAlpha.class ).parallelStream().forEach(alpha ->
            dto.getThingsToWorkWith().getAlphas().add(alpha.getId()));
        
        EssenceFilter.filterLanguageElement( entity.getReferredElements(), SEWorkProduct.class ).parallelStream().forEach(workProduct ->
            dto.getThingsToWorkWith().getWorkProducts().add(workProduct.getId()));
        
    }

    public static void mapThingsToDo(SEPractice entity, PracticeDto dto) {
        dto.setThingsToDo( new ThingsToDo());
        dto.getThingsToDo().setActivities(new ArrayList<>());
        
        for (SEActivityAssociation actAssociation : EssenceFilter.filterLanguageElement(entity.getOwnedElements(), SEActivityAssociation.class)){
            //seActivity:
            String idActivitySpace = ((SEActivitySpace) actAssociation.getEnd1()).getId();
            SEActivity seActivity = (SEActivity) actAssociation.getEnd2();
            Activity actDto = new Activity();
            actDto.setIdActivity(seActivity.getId());
            actDto.setIdActivitySpace(idActivitySpace);
            actDto.setName(seActivity.getName());
            actDto.setBriefDesciption(seActivity.getBriefDescription());
            actDto.setDescription(seActivity.getDescription());
            actDto.setTo(seActivity.getTo());
            actDto.setPosition(seActivity.getPosition());
            actDto.setGoJsPosition(seActivity.getGoJsPosition());
            actDto.setCreated(Boolean.TRUE);
            mapCompetencyLevel(seActivity, actDto);
            mapApproach(seActivity, actDto);
            mapActions(seActivity, actDto);
            mapCriterios( seActivity.getCriterion(), actDto, true);
            mapCriterios( seActivity.getCriterion(), actDto, false);
            mapActivityResources(seActivity, actDto);
            dto.getThingsToDo().getActivities().add(actDto);
        }
        Collections.sort(dto.getThingsToDo().getActivities(), new Comparator<Activity>(){
            @Override
            public int compare(Activity act1, Activity act2){
               return act1.getPosition() - act2.getPosition();
            }
         });
    }
    /*
    public static void mapActivitiesComposition(List<Activity> activities){
        for (int i=0; i< activities.size(); i++){
            SEActivity act = (SEActivity) repoUtil.getDocument( idActivities.get(i), SEActivity.class);
            if (!activities.get(i).getTo().isEmpty()){
                for (String to: activities.get(i).getTo()){
                    //creamos la relación entre actividades
                    SEActivity act2 = (SEActivity) repoUtil.getDocument( idActivities.get(Integer.parseInt(to)), SEActivity.class);
                    if (act2 != null){
                        SEActivityAssociation actAssociation = new SEActivityAssociation();
                        actAssociation.setEnd1(act);
                        actAssociation.setEnd2(act2);
                        repoUtil.mongoTemplate.save(actAssociation);
                        act.getActivityAssociation().add(actAssociation);
                        repoUtil.mongoTemplate.save(act);
                    }
                }
            }
        }
    }*/

    public static void mapActivityResources(SEActivity entity, Activity dto) {
        dto.setResources(new ArrayList<>());
        for (SEResource seResource : entity.getResource()){
            Resource resourceDto = new Resource();
            resourceDto.setContent(seResource.getContent());
            resourceDto.setIdTypeResource(seResource.getId());
            resourceDto.setFile(seResource.getFileName());
            dto.getResources().add(resourceDto);
        }
    }

    public static void mapActions(SEActivity entity, Activity dto) {
        dto.setActions(new ArrayList<>());
        
        for (SEAction seAction : entity.getAction()){
            Action actionDto = new Action();
            actionDto.setIdActionKind(seAction.getKind().name());
            
            actionDto.setAlphaStates(new ArrayList<>());
            for (SEAlpha seAlpha : seAction.getAlpha() ){
                AlphaState alphaState = new AlphaState();
                alphaState.setIdAlpha(seAlpha.getId());
                alphaState.setIdState(""); //we don't persist this
                actionDto.getAlphaStates().add(alphaState);
            }
            
            actionDto.setWorkProductsLevelofDetail(new ArrayList<>());
            for (SEWorkProduct seWorkProduct : seAction.getWorkProduct()){
                WorkProductsLevelofDetail wpLoD = new WorkProductsLevelofDetail();
                wpLoD.setIdWorkProduct(seWorkProduct.getId());
                wpLoD.setIdLevelOfDetail(""); // We don't persist this
                actionDto.getWorkProductsLevelofDetail().add(wpLoD);
            }
        }
    }

    public static void mapAlphaStateToAction(List<AlphaState> alphaStates, SEAction seAction) {
        Optional.of(alphaStates).ifPresent(alphaStateList -> {
            alphaStateList.forEach(alphaState -> {
                seAction.getAlpha().add( (SEAlpha) repoUtil.getDocument(alphaState.getIdAlpha(), SEAlpha.class));
            });
        });
    }

    public static void mapLevelOfDetailToAction(List<WorkProductsLevelofDetail> workProductsLevelofDetail,
            SEAction seAction) {
        Optional.of(workProductsLevelofDetail).ifPresent(workProductsLevelofDetailList -> {
            workProductsLevelofDetailList.forEach(workProduct -> {
                seAction.getWorkProduct().add( (SEWorkProduct) repoUtil.getDocument(workProduct.getIdWorkProduct(), SEWorkProduct.class));
            });
        });
    }

    private static void mapApproach(SEActivity entity, Activity dto ) {
        dto.setApproaches(new ArrayList<>());
        
        for (SEApproach seApproach: entity.getApproach()){
            Approach approachDto = new Approach();
            approachDto.setName(seApproach.getName());
            approachDto.setDescription(seApproach.getDescription());
            dto.getApproaches().add(approachDto);
        }
    }

    private static void mapCompetencyLevel(SEActivity entity, Activity dto ) {
        dto.setCompetencies(new ArrayList<>());
        for (SECompetencyLevel competency : entity.getRequiredCompetencyLevel() ) {
            Competency competencyDto = new Competency();
            competencyDto.setIdCompetencyLevel(competency.getId());
            competencyDto.setIdCompetencyLevel(((SECompetency) competency.getCompetency()).getId());
            dto.getCompetencies().add(competencyDto);
        }
    }

    public static void mapRelatedPractices(SEPractice entity, PracticeDto dto) {
        //relatedPractices are optional on the client
        dto.setRelatedPractices( new ArrayList<>());
        for (SEPractice relatedPractice : EssenceFilter.filterLanguageElement( entity.getReferredElements(), SEPractice.class ) ) {
            dto.getRelatedPractices().add(relatedPractice.getId());
        }
    }

}
