package mx.infotec.dads.sekc.admin.practice.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.model.foundation.SELanguageElement;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.sekc.admin.practice.consult.dto.Alpha;
import mx.infotec.dads.sekc.admin.practice.consult.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.consult.dto.CheckListItem;
import mx.infotec.dads.sekc.admin.practice.consult.dto.Entry;
//import mx.infotec.dads.sekc.admin.practice.consult.dto.Competency;
//import mx.infotec.dads.sekc.admin.practice.consult.dto.CompetencyLevel;
import mx.infotec.dads.sekc.admin.practice.consult.dto.LevelOfDetail;
import mx.infotec.dads.sekc.admin.practice.consult.dto.State;
import mx.infotec.dads.sekc.admin.practice.consult.dto.WorkProduct;
import mx.infotec.dads.sekc.admin.practice.consult.dto.PracticeConsultDto;
import mx.infotec.dads.sekc.admin.practice.consult.dto.Result;
import mx.infotec.dads.sekc.admin.practice.consult.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.consult.dto.WorkProductsLevelofDetail;

/**
 *
 * @author wisog
 */
public class ClassMatcher {
    
    public static Alpha matchAlpha(SEAlpha seAlpha){
        Alpha alpha = new Alpha();
        alpha.setId(seAlpha.getId());
        alpha.setName(seAlpha.getName());
        alpha.setDescription(seAlpha.getDescription());
        alpha.setBriefDesciption(seAlpha.getBriefDescription());
        
        List<State> stateList = new ArrayList<>();
        if (seAlpha.getStates() != null){
            seAlpha.getStates().forEach((seState) -> {
                stateList.add(matchState( seState));
            });
            alpha.setStates(stateList);
        }
        
        //workproducts
        List<WorkProduct> workProductList = new ArrayList<>();
        if (seAlpha.getWorkProductManifest() != null){
            seAlpha.getWorkProductManifest().stream().map((workProductManifest) -> workProductManifest.getWorkProduct()).forEachOrdered((seWorkProduct) -> {
                workProductList.add(matchWorkProduct( seWorkProduct));
            });
            alpha.setWorkProducts(workProductList);
        }
        
        return alpha;
    }
    
    public static State matchState(SEState seState){
        State state = new State();
        state.setId(seState.getId());
        state.setName(seState.getName());
        state.setDescription(seState.getDescription());
        List<CheckListItem> checkpointList = new ArrayList<>();
        if (seState.getCheckListItem() != null){
            seState.getCheckListItem().forEach((seCheckPoint) -> {
                checkpointList.add(matchCheckpoint( seCheckPoint));
            });
            state.setCheckListItem(checkpointList);
        }
        return state;
    }
    
    public static CheckListItem matchCheckpoint(SECheckpoint seCheckpoint){
        CheckListItem checkpoint = new CheckListItem();
        checkpoint.setId(seCheckpoint.getId());
        checkpoint.setName(seCheckpoint.getName());
        checkpoint.setDescription(seCheckpoint.getDescription());
        checkpoint.setShortDescription(seCheckpoint.getShortDescription());
        return checkpoint;
    }
    
    public static WorkProduct matchWorkProduct(SEWorkProduct seWorkProduct){
        WorkProduct workProduct = new WorkProduct();
        workProduct.setId(seWorkProduct.getId());
        workProduct.setName(seWorkProduct.getName());
        workProduct.setDescription(seWorkProduct.getDescription());
        workProduct.setBriefDescription(seWorkProduct.getBriefDescription());
        List<LevelOfDetail> levelsOfDetailList = new ArrayList<>();
        if (seWorkProduct.getLevelOfDetail() != null){
            seWorkProduct.getLevelOfDetail().forEach((seLevelOfDetail) -> {
                levelsOfDetailList.add(matchLevelsOfDetail( seLevelOfDetail));
            });
            workProduct.setLevelOfDetail(levelsOfDetailList);
        }
        return workProduct;
    }
    
    public static LevelOfDetail matchLevelsOfDetail(SELevelOfDetail seLevelOfDetail){
        LevelOfDetail levelOfDetail = new LevelOfDetail();
        levelOfDetail.setId(seLevelOfDetail.getId());
        levelOfDetail.setName(seLevelOfDetail.getName());
        levelOfDetail.setDescription(seLevelOfDetail.getDescription());
        List<CheckListItem> checkpointList = new ArrayList<>();
        if (seLevelOfDetail.getCheckListItem() != null){
            seLevelOfDetail.getCheckListItem().forEach((seCheckPoint) -> {
                checkpointList.add(matchCheckpoint( seCheckPoint));
            });
            levelOfDetail.setCheckListItem(checkpointList);
        }
        return levelOfDetail;
    }
    
    public static PracticeConsultDto matchPractice(SEPractice sepractice){
        PracticeConsultDto practice = new PracticeConsultDto();
        practice.setId(sepractice.getId());
        practice.setName(sepractice.getName());
        practice.setDescription(sepractice.getDescription());
        practice.setBriefDescription(sepractice.getBriefDescription());
        practice.setConsistencyRules(sepractice.getConsistencyRules());
        practice.setObjective(sepractice.getObjective());
        practice.setMeasures( new ArrayList(sepractice.getMeasures()));
        practice.setKeyWords(sepractice.getKeyWords());
        practice.setAuthor(sepractice.getAuthor());
        try{
        practice.setEntries(matchEntries(sepractice));
        practice.setResults(matchResults(sepractice));
        }catch (Exception e){
            e.printStackTrace();
        }
        practice.setThingsToWorkWith(matchThingsToWorkWith(sepractice));
        
        return practice;
    }
    public static List<Entry> matchEntries(SEPractice sepractice){
        List<Entry> entries = new ArrayList<>();
        sepractice.getEntryCriterion().stream().map((entryCrit) -> {
            Entry entry = new Entry();
            List<AlphaState> alphStates = new ArrayList<>();
            List<WorkProductsLevelofDetail> wpLevels = new ArrayList<>();
            if (entryCrit.getState() != null){ //AlphaState Entry Criterion
                AlphaState alphState = new AlphaState();
                alphState.setIdState(entryCrit.getState().getId());
                alphState.setState(entryCrit.getState().getName());
                alphState.setIdAlpha(entryCrit.getState().getAlpha().getId());
                alphState.setAlpha(entryCrit.getState().getAlpha().getName());
                alphStates.add(alphState);
            }else if (entryCrit.getLevelOfDetail() != null){ //WorkProduct-LevelOfDetail Entry Criterion
                WorkProductsLevelofDetail wpLvlDetail = new WorkProductsLevelofDetail();
                wpLvlDetail.setIdLevelOfDetail(entryCrit.getLevelOfDetail().getId());
                wpLvlDetail.setLevelOfDetail(entryCrit.getLevelOfDetail().getName());
                wpLvlDetail.setIdWorkProduct(entryCrit.getLevelOfDetail().getWorkProduct().getId());
                wpLvlDetail.setWorkProduct(entryCrit.getLevelOfDetail().getWorkProduct().getName());
                wpLevels.add(wpLvlDetail);
            }else if (entryCrit.getOtherConditions() != null)
                entry.setOtherConditions(new ArrayList( entryCrit.getOtherConditions()));
            entry.setAlphaStates(alphStates);
            entry.setWorkProductsLevelofDetail(wpLevels);
            return entry;
        }).forEachOrdered((entry) -> {
            entries.add(entry);
        });
        return entries;
    }
    
    public static List<Result> matchResults(SEPractice sepractice){
        List<Result> results = new ArrayList<>();
        sepractice.getResultCriterion().stream().map((resultCrit) -> {
            Result result = new Result();
            List<AlphaState> alphStates = new ArrayList<>();
            List<WorkProductsLevelofDetail> wpLevels = new ArrayList<>();
            if (resultCrit.getState() != null){ //AlphaState Entry Criterion
                AlphaState alphState = new AlphaState();
                alphState.setState(resultCrit.getState().getName());
                alphState.setAlpha(resultCrit.getState().getAlpha().getName());
                alphStates.add(alphState);
            }else if (resultCrit.getLevelOfDetail() != null){ //WorkProduct-LevelOfDetail Entry Criterion
                WorkProductsLevelofDetail wpLvlDetail = new WorkProductsLevelofDetail();
                wpLvlDetail.setLevelOfDetail(resultCrit.getLevelOfDetail().getName());
                wpLvlDetail.setWorkProduct(resultCrit.getLevelOfDetail().getWorkProduct().getName());
                wpLevels.add(wpLvlDetail);
            }else if (resultCrit.getOtherConditions() != null)
                result.setOtherConditions(new ArrayList( resultCrit.getOtherConditions()));
            result.setAlphaStates(alphStates);
            result.setWorkProductsLevelofDetail(wpLevels);
            return result;
        }).forEachOrdered((result) -> {
            results.add(result);
        });
        return results;
    }
    
    public static ThingsToWorkWith matchThingsToWorkWith(SEPractice sepractice){
        ThingsToWorkWith tToWork = new ThingsToWorkWith();
        List<Alpha> alphas = new ArrayList<>();
        for (SELanguageElement element :  sepractice.getReferredElements() ){
            if (element.getClass().getSimpleName().equals("SEAlpha")){
                alphas.add(matchAlpha( (SEAlpha) element));
            }
        }
        tToWork.setAlphas(alphas);
        return tToWork;
    }
    
}
