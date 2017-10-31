package mx.infotec.dads.sekc.admin.practice.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEAction;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivity;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivityAssociation;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEApproach;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.model.foundation.SEResource;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.practice.dto.Action;
import mx.infotec.dads.sekc.admin.practice.dto.Activity;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Approach;
import mx.infotec.dads.sekc.admin.practice.dto.Competency;
import mx.infotec.dads.sekc.admin.practice.dto.CompletitionCriterion;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;

import mx.infotec.dads.sekc.admin.practice.dto.Entries;
import mx.infotec.dads.sekc.admin.practice.dto.EntryCriterion;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.dto.Resource;
import mx.infotec.dads.sekc.admin.practice.dto.Results;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToDo;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;
import static mx.infotec.dads.sekc.admin.practice.service.util.SEEssenceMapper.repoUtil;
import mx.infotec.dads.sekc.util.EssenceFilter;

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

    public static void mapConditions(SEPractice entity, PracticeDto dto ){
        dto.setConditions( new Conditions());
        mapCriterios( entity.getEntryCriterion(), dto, true);
        mapCriterios( entity.getResultCriterion(), dto, false);
        dto.getConditions().setMeasures( new ArrayList<>(entity.getMeasures()));
    }

    private static void mapCriterios(Collection<SECriterion> seCriterionList, PracticeDto dto, boolean isEntry) {
        if (dto.getConditions().getEntries() == null)
            dto.getConditions().setEntries(new Entries());
        if (dto.getConditions().getResults() == null)
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
            alphaState.setDescription(criterion.getDescription());
            dto.getConditions().getEntries().getAlphaStates().add(alphaState);
        }
    }
    
    private static void mapResultAlphaStatesEntryCriterion(SECriterion criterion, Activity dto) {
        if (criterion.getState() != null){    
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            alphaState.setDescription(criterion.getDescription());
            dto.getEntryCriterion().getAlphaStates().add(alphaState);
        }
    }

    private static void mapResultAlphaStatesResultCriterion(SECriterion criterion, PracticeDto dto) {
        if (criterion.getState() != null){ 
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            alphaState.setDescription(criterion.getDescription());
            dto.getConditions().getResults().getAlphaStates().add(alphaState);
        }
    }
    
    private static void mapResultAlphaStatesResultCriterion(SECriterion criterion, Activity dto) {
        if (criterion.getState() != null){ 
            AlphaState alphaState = new AlphaState();
            alphaState.setIdState(criterion.getState().getId());
            alphaState.setIdAlpha(criterion.getState().getAlpha().getId());
            alphaState.setDescription(criterion.getDescription());
            dto.getCompletitionCriterion().getAlphaStates().add(alphaState);
        }
    }

    private static void mapResultWorkProductLevelOfDetailEntryCriterion( SECriterion criterion, PracticeDto dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            wp.setDescription(criterion.getDescription());
            dto.getConditions().getEntries().getWorkProductsLevelofDetail().add(wp);
        }
    }
    
     private static void mapResultWorkProductLevelOfDetailEntryCriterion( SECriterion criterion, Activity dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            wp.setDescription(criterion.getDescription());
            dto.getEntryCriterion().getWorkProductsLevelofDetail().add(wp);
        }
    }

    private static void mapResultWorkProductLevelOfDetailResultCriterion( SECriterion criterion, PracticeDto dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            wp.setDescription(criterion.getDescription());
            dto.getConditions().getResults().getWorkProductsLevelofDetail().add(wp);
        }
    }
    
    private static void mapResultWorkProductLevelOfDetailResultCriterion( SECriterion criterion, Activity dto) {
        if (criterion.getLevelOfDetail() != null){
            WorkProductsLevelofDetail wp = new WorkProductsLevelofDetail();
            wp.setIdLevelOfDetail(criterion.getLevelOfDetail().getId());
            wp.setIdWorkProduct(criterion.getLevelOfDetail().getWorkProduct().getId());
            wp.setDescription(criterion.getDescription());
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
            String nameActivitySpace = ((SEActivitySpace) actAssociation.getEnd1()).getName();
            SEActivity seActivity = (SEActivity) actAssociation.getEnd2();
            Activity actDto = new Activity();
            actDto.setIdActivity(seActivity.getId());
            actDto.setIdActivitySpace(idActivitySpace);
            actDto.setNameActivitySpace(nameActivitySpace);
            actDto.setIdAreaOfConcern(seActivity.getIdAreaOfConcern());
            actDto.setName(seActivity.getName());
            actDto.setBriefDescription(seActivity.getBriefDescription());
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

    public static void mapActivityResources(SEActivity entity, Activity dto) {
        dto.setResources(new ArrayList<>());
        for (SEResource seResource : entity.getResource()){
            Resource resourceDto = new Resource();
            resourceDto.setContent(seResource.getContent());
            resourceDto.setIdTypeResource(seResource.getId());
            resourceDto.setFile(seResource.getFile());
            resourceDto.setFileName(seResource.getFileName());
            resourceDto.setFileType(seResource.getFileType());
            dto.getResources().add(resourceDto);
        }
    }

    public static void mapActions(SEActivity entity, Activity dto) {
        dto.setActions(new ArrayList<>());
        
        for (SEAction seAction : entity.getAction()){
            Action actionDto = new Action();
            actionDto.setIdActionKind(seAction.getKind().name());
            
            actionDto.setAlphaStates(new ArrayList<>());
            for (SEState seState : seAction.getState() ){
                AlphaState alphaState = new AlphaState();
                alphaState.setIdAlpha(seState.getAlpha().getId());
                alphaState.setIdState(seState.getId());
                alphaState.setDescription(seState.getAlpha().getName() +" - " + seState.getName());
                actionDto.getAlphaStates().add(alphaState);
            }
            
            actionDto.setWorkProductsLevelofDetail(new ArrayList<>());
            for (SELevelOfDetail seLevelOfDetail : seAction.getLevelOfDetail()){
                WorkProductsLevelofDetail wpLoD = new WorkProductsLevelofDetail();
                wpLoD.setIdWorkProduct(seLevelOfDetail.getWorkProduct().getId());
                wpLoD.setIdLevelOfDetail(seLevelOfDetail.getId());
                wpLoD.setDescription(seLevelOfDetail.getWorkProduct().getName() + " - " + seLevelOfDetail.getName());
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
            competencyDto.setCompetencyLevel(competency.getName()); // we don't persist this
            
            if (competency.getCompetency() != null){
                competencyDto.setIdCompetency( ((SECompetency) competency.getCompetency()).getId() ); // # TODO check this
                competencyDto.setCompetency( ((SECompetency) competency.getCompetency()).getName() );
            } else{ // we didn't persist this
                competencyDto.setIdCompetency( "" );
                competencyDto.setCompetency( "");
            }
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
