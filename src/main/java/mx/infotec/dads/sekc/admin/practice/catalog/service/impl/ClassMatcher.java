package mx.infotec.dads.sekc.admin.practice.catalog.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlphaContainment;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProductManifest;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.ActivitySpace;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Alpha;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.AlphaContainment;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Kernel;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Practice;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.WorkProductManifest;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Competency;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.PossibleLevel;

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
        kernel.setAdditionalProperty("aresOfConcerns", seKernel.getOwnedElements());
        return kernel;
    }
    
    public Alpha matchAlpha(Alpha alpha, SEAlpha seAlpha){
        alpha.setId(seAlpha.getId());
        alpha.setName(seAlpha.getName());
        alpha.setDescription(seAlpha.getDescription());
        alpha.setBriefDescription(seAlpha.getBriefDescription());
        
        //AlphaContainment
        List<AlphaContainment> alphaContainmentList = new ArrayList<>();
        
        if (seAlpha.getAlphaContainment() != null)
            seAlpha.getAlphaContainment().forEach((seAlphaContainment) -> {
                AlphaContainment alphaContainment = new AlphaContainment();
                alphaContainmentList.add( matchAlphaConainment(alphaContainment, seAlphaContainment) );
            });
        alpha.setAlphaContainment(alphaContainmentList);
        //WorkProductManifest
        List<WorkProductManifest> workProductManifestList = new ArrayList<>();
        
        if (seAlpha.getWorkProductManifest()!= null)
            seAlpha.getWorkProductManifest().forEach((seWorkProductManifest) -> {
                WorkProductManifest workProductManifest = new WorkProductManifest();
                workProductManifestList.add( matchWorkProductManifest(workProductManifest, seWorkProductManifest) );
            });
        alpha.setWorkProductManifest(workProductManifestList);
        
        return alpha;
    }
    
    public AlphaContainment matchAlphaConainment(AlphaContainment alphaContainment, SEAlphaContainment seAlphaContainment){
        alphaContainment.setId(seAlphaContainment.getId());
        // SEAlphaContainment doesn't have name, des, briefDesc...
        return alphaContainment;
    }
    
    public WorkProductManifest matchWorkProductManifest(WorkProductManifest workProductManifest, SEWorkProductManifest seWorkProductManifest){
        workProductManifest.setId(seWorkProductManifest.getId());
        // SEWorkProductManifest doesn't have name, des, briefDesc...
        return workProductManifest;
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
    
    public Competency matchCompetency(Competency competency, SECompetency seCompetency){
        competency.setId(seCompetency.getId());
        competency.setName(seCompetency.getName());
        competency.setDescription(seCompetency.getDescription());
        competency.setBriefDescription(seCompetency.getBriefDescription());
        //PossibleLevel
        List<PossibleLevel> possibleLevelList = new ArrayList<>();
        
        if (seCompetency.getPossibleLevel() != null)
            seCompetency.getPossibleLevel().forEach((sePossibleLevel) -> {
                PossibleLevel possibleLevel = new PossibleLevel();
                possibleLevelList.add( matchPossibleLevel(possibleLevel, (SECompetencyLevel) sePossibleLevel) );
            });
        competency.setPossibleLevel(possibleLevelList);
        return competency;
    }
    
    public PossibleLevel matchPossibleLevel(PossibleLevel possibleLevel, SECompetencyLevel sePossibleLevel){
        possibleLevel.setId(sePossibleLevel.getId());
        possibleLevel.setName(sePossibleLevel.getName());
        possibleLevel.setBriefDescription(sePossibleLevel.getBriefDescription());
        possibleLevel.setLevel(sePossibleLevel.getLevel());
        return possibleLevel;
    }
    
}
