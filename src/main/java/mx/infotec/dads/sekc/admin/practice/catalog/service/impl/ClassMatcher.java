package mx.infotec.dads.sekc.admin.practice.catalog.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SELanguageElement;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.model.foundation.extention.SEAreaOfConcern;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.ActivitySpace;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Alpha;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.AreasOfConcern;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Checkpoint;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Kernel;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Practice;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Competency;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.CompetencyLevel;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.LevelsOfDetail;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.State;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Workproduct;
import mx.infotec.dads.sekc.config.dbmigrations.domain.Color;

/**
 *
 * @author wisog
 */
public class ClassMatcher {
    
    public Kernel matchKernel(Kernel kernel, SEKernel seKernel){
        kernel.setId(seKernel.getId());
        kernel.setName(seKernel.getName());
        kernel.setDescription(seKernel.getDescription());
        kernel.setBriefDescription(seKernel.getBriefDescription());
        //kernel.setAdditionalProperty("aresOfConcerns", seKernel.getOwnedElements());
        return kernel;
    }
    
    public AreasOfConcern matchAreaOfConcern(AreasOfConcern areaOfConcern, SEAreaOfConcern seAreaOfConcern) {
        areaOfConcern.setId(seAreaOfConcern.getId());
        areaOfConcern.setName(seAreaOfConcern.getName());
        areaOfConcern.setBriefDescription(seAreaOfConcern.getBriefDescription());
        areaOfConcern.setDescription(seAreaOfConcern.getDescription());
        Color color = new Color();
        color.setName(seAreaOfConcern.getColor().getName());
        color.setHexadecimal(seAreaOfConcern.getColor().getHexadecimal());
        areaOfConcern.setColor(color);
        List<Alpha> alphas = new ArrayList<>();
        List<ActivitySpace> activitySpaces = new ArrayList<>();
        List<Competency> competencies = new ArrayList<>();
        for (SELanguageElement element: seAreaOfConcern.getOwnedElements()){
            switch (element.getClass().getSimpleName()){
                case "SEAlpha":
                    alphas.add(matchAlpha(new Alpha(), (SEAlpha) element, false));
                break;
                case "SEActivitySpace":
                    activitySpaces.add(matchActivitySpace(new ActivitySpace(), (SEActivitySpace) element));
                break;
                case "SECompetency":
                    competencies.add(matchCompetency(new Competency(), (SECompetency) element, false));
                break;
            }
        }
        areaOfConcern.setAlphas(alphas);
        areaOfConcern.setActivitySpaces(activitySpaces);
        areaOfConcern.setCompetencies(competencies);
        return areaOfConcern;
    }
    
    public Alpha matchAlpha(Alpha alpha, SEAlpha seAlpha, boolean subElements){
        alpha.setId(seAlpha.getId());
        alpha.setName(seAlpha.getName());
        alpha.setDescription(seAlpha.getDescription());
        alpha.setBriefDescription(seAlpha.getBriefDescription());
        
        if (subElements){
            List<State> stateList = new ArrayList<>();
            if (seAlpha.getStates() != null){
                seAlpha.getStates().forEach((seState) -> {
                    State state = new State();
                    stateList.add(matchState(state, seState));
                });
                alpha.setStates(stateList);
            }

            //workproducts
            List<Workproduct> workProductList = new ArrayList<>();
            if (seAlpha.getWorkProductManifest() != null){
                seAlpha.getWorkProductManifest().stream().map((workProductManifest) -> workProductManifest.getWorkProduct()).forEachOrdered((seWorkProduct) -> {
                    Workproduct workProduct = new Workproduct();
                    workProductList.add(matchWorkProduct(workProduct, seWorkProduct));
                });
                alpha.setWorkproducts(workProductList);
            }
        }
        return alpha;
    }
    
    public State matchState(State state, SEState seState){
        state.setId(seState.getId());
        state.setName(seState.getName());
        state.setDescription(seState.getDescription());
        List<Checkpoint> checkpointList = new ArrayList<>();
        if (seState.getCheckListItem() != null){
            seState.getCheckListItem().forEach((seCheckPoint) -> {
                Checkpoint checkpoint = new Checkpoint();
                checkpointList.add(matchCheckpoint(checkpoint, seCheckPoint));
            });
            state.setCheckpoints(checkpointList);
        }
        return state;
    }
    
    public Checkpoint matchCheckpoint(Checkpoint checkpoint, SECheckpoint seCheckpoint){
        checkpoint.setId(seCheckpoint.getId());
        checkpoint.setName(seCheckpoint.getName());
        checkpoint.setDescription(seCheckpoint.getDescription());
        checkpoint.setBriefDescription(seCheckpoint.getShortDescription());
        return checkpoint;
    }
    
    public Workproduct matchWorkProduct(Workproduct workProduct, SEWorkProduct seWorkProduct){
        workProduct.setId(seWorkProduct.getId());
        workProduct.setName(seWorkProduct.getName());
        workProduct.setDescription(seWorkProduct.getDescription());
        workProduct.setBriefDescription(seWorkProduct.getBriefDescription());
        
        List<LevelsOfDetail> levelsOfDetailList = new ArrayList<>();
        if (seWorkProduct.getLevelOfDetail() != null){
            seWorkProduct.getLevelOfDetail().forEach((seLevelOfDetail) -> {
                LevelsOfDetail levelOfDetail = new LevelsOfDetail();
                levelsOfDetailList.add(matchLevelsOfDetail(levelOfDetail, seLevelOfDetail));
            });
            workProduct.setLevelsOfDetails(levelsOfDetailList);
        }
        return workProduct;
    }
    
    public LevelsOfDetail matchLevelsOfDetail(LevelsOfDetail levelOfDetail, SELevelOfDetail seLevelOfDetail){
        levelOfDetail.setId(seLevelOfDetail.getId());
        levelOfDetail.setName(seLevelOfDetail.getName());
        levelOfDetail.setDescription(seLevelOfDetail.getDescription());
        List<Checkpoint> checkpointList = new ArrayList<>();
        if (seLevelOfDetail.getCheckListItem() != null){
            seLevelOfDetail.getCheckListItem().forEach((seCheckPoint) -> {
                Checkpoint checkpoint = new Checkpoint();
                checkpointList.add(matchCheckpoint(checkpoint, seCheckPoint));
            });
            levelOfDetail.setCheckpoints(checkpointList);
        }
        return levelOfDetail;
    }
        
    public ActivitySpace matchActivitySpace(ActivitySpace activitySpace, SEActivitySpace seActivitySpace){
        activitySpace.setId(seActivitySpace.getId());
        activitySpace.setName(seActivitySpace.getName());
        activitySpace.setDescription(seActivitySpace.getDescription());
        activitySpace.setBriefDescription(seActivitySpace.getBriefDescription());
        return activitySpace;
    }
    
    public Practice matchPractice(Practice practice, SEPractice sepractice){
        practice.setId(sepractice.getId());
        practice.setName(sepractice.getName());
        practice.setDescription(sepractice.getDescription());
        practice.setBriefDescription(sepractice.getBriefDescription());
        return practice;
    }
    
    public Competency matchCompetency(Competency competency, SECompetency seCompetency, boolean subElements){
        competency.setId(seCompetency.getId());
        competency.setName(seCompetency.getName());
        competency.setDescription(seCompetency.getDescription());
        competency.setBriefDescription(seCompetency.getBriefDescription());
        
        if (subElements){
            //CompetencyLevel
            List<CompetencyLevel> competencyLevelList = new ArrayList<>();
            if (seCompetency.getPossibleLevel() != null){
                seCompetency.getPossibleLevel().forEach((seCompetencyLevel) -> {
                    CompetencyLevel competencyLevel = new CompetencyLevel();
                    competencyLevelList.add( matchCompetencyLevel(competencyLevel, (SECompetencyLevel) seCompetencyLevel));
                });
                competency.setCompetencyLevel(competencyLevelList);
            }
        }
        return competency;
    }
    
    public CompetencyLevel matchCompetencyLevel(CompetencyLevel competencyLevel, SECompetencyLevel seCompetencyLevel){
        competencyLevel.setId(seCompetencyLevel.getId());
        competencyLevel.setName(seCompetencyLevel.getName());
        competencyLevel.setBriefDescription(seCompetencyLevel.getBriefDescription());
        competencyLevel.setLevel(seCompetencyLevel.getLevel());
        
        List<Checkpoint> checkpointList = new ArrayList<>();
        if (seCompetencyLevel.getChecklistItem() != null){
            seCompetencyLevel.getChecklistItem().forEach((seCheckPoint) -> {
                Checkpoint checkpoint = new Checkpoint();
                checkpointList.add(matchCheckpoint(checkpoint, (SECheckpoint) seCheckPoint));
            });
            competencyLevel.setCheckpoints(checkpointList);
        }
        return competencyLevel;
    }
    
}
