package mx.infotec.dads.sekc.admin.practice.catalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.omg.essence.model.activityspaceandactivity.ActionKind;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEActivitySpaceRepository;
import mx.infotec.dads.essence.repository.SEAlphaRepository;
import mx.infotec.dads.essence.repository.SECompetencyRepository;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.ActionsKind;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.ActivitySpace;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Alpha;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Catalogs;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.CatalogsDto;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Kernel;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Practice;
import mx.infotec.dads.sekc.admin.practice.catalog.service.CatalogsService;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.Competency;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.ResourcesType;
import mx.infotec.dads.sekc.admin.practice.catalog.dto.ResourcesTypes;


/**
 *
 * @author wisog
 */
@Service
public class CatalogsServiceImpl implements CatalogsService {

    @Autowired
    private SEKernelRepository kernelRepository;
    @Autowired
    private SEAlphaRepository alphaRepository;
    @Autowired
    private SEActivitySpaceRepository activitySpaceRepository;
    @Autowired
    private SEPracticeRepository practiceRepository;
    @Autowired
    private SECompetencyRepository competencyRepository;

    private ResponseWrapper response;

    private void recoverCatalogFields(Catalogs catalogs){
        ClassMatcher classMatcher = new ClassMatcher();
        //------List<Kernel>          kernels
        List<SEKernel> seKernelList = kernelRepository.findAll();
        List<Kernel> kernelList = new ArrayList<>();
        
        seKernelList.forEach((seKernel) -> {
            Kernel kernel = new Kernel();
            kernelList.add( classMatcher.matchKernel(kernel, seKernel) );
        });
        catalogs.setKernels(kernelList);
        //------List<Alpha>           alphas
        List<SEAlpha> seAlphaList = alphaRepository.findAll();
        List<Alpha> alphaList = new ArrayList<>();
        
        seAlphaList.forEach((seAlpha) -> {
            Alpha alpha = new Alpha();
            alphaList.add( classMatcher.matchAlpha(alpha, seAlpha) );
        });
        catalogs.setAlphas(alphaList);
        //------List<ActivitySpace>   activitySpaces
        List<SEActivitySpace> seActivitySpacelList = activitySpaceRepository.findAll();
        List<ActivitySpace> activitySpaceList = new ArrayList<>();
        
        seActivitySpacelList.forEach((seActivitySpace) -> {
            ActivitySpace activitySpace = new ActivitySpace();
            activitySpaceList.add( classMatcher.matchActivitySpace(activitySpace, seActivitySpace) );
        });
        catalogs.setActivitySpaces(activitySpaceList);
        //------List<Practice>        practices
        List<SEPractice> sePracticelList = practiceRepository.findAll();
        List<Practice> practiceList = new ArrayList<>();
        
        sePracticelList.forEach((sePractice) -> {
            Practice practice = new Practice();
            practiceList.add( classMatcher.matchPractice(practice, sePractice) );
        });
        catalogs.setPractices(practiceList);
        //------List<Competency>      competencies
        List<SECompetency> seCompetencylList = competencyRepository.findAll();
        List<Competency> competencyList = new ArrayList<>();
        
        seCompetencylList.forEach((seCompetency) -> {
            Competency competency = new Competency();
            competencyList.add( classMatcher.matchCompetency(competency, seCompetency) );
        });
        catalogs.setCompetencies(competencyList);
        //------List<ActionsKind>     actionsKinds
        List<ActionsKind> actionsKindList = new ArrayList<>();
        for (ActionKind value: ActionKind.values()){
            ActionsKind actionsKind = new ActionsKind();
            actionsKind.setName(value.name());
            actionsKindList.add( actionsKind );
        }
        catalogs.setActionsKinds(actionsKindList);
        //------List<ResourcesType>   resourcesTypes
        List<ResourcesType> resourcesTypeList = new ArrayList<>();
        for (ResourcesTypes value: ResourcesTypes.values()){
            ResourcesType resourcesType = new ResourcesType();
            resourcesType.setId(value.getId());
            resourcesType.setName(value.name());
            resourcesTypeList.add( resourcesType );
        }
        catalogs.setResourcesTypes(resourcesTypeList);
    }
    
    @Override
    public ResponseWrapper findAll() {
        
        response = new ResponseWrapper();
        Catalogs catalogs = new Catalogs();
        recoverCatalogFields(catalogs);
        CatalogsDto catalogsDto = new CatalogsDto();
        catalogsDto.setCatalogs(catalogs);
        response.setResponse_code(HttpStatus.OK);
        response.setResponseObject(catalogsDto);
        
        return response;
    }
}
