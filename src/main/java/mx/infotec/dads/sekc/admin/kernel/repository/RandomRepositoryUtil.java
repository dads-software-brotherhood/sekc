package mx.infotec.dads.sekc.admin.kernel.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;

import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.essence.model.foundation.SEBasicElement;
import mx.infotec.dads.essence.model.foundation.SEElementGroup;
import mx.infotec.dads.essence.model.foundation.SEEndeavorProperty;
import mx.infotec.dads.essence.model.foundation.SEExtensionElement;
import mx.infotec.dads.essence.model.foundation.SELanguageElement;
import mx.infotec.dads.essence.model.foundation.SEMergeResolution;
import mx.infotec.dads.essence.model.foundation.SEPatternAssociation;
import mx.infotec.dads.essence.model.foundation.SEResource;
import mx.infotec.dads.essence.model.foundation.SETag;
import mx.infotec.dads.essence.model.view.SEFeatureSelection;
import mx.infotec.dads.essence.model.view.SEViewSelection;
import mx.infotec.dads.essence.repository.SEActionRepository;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.essence.repository.SELibraryRepository;
import mx.infotec.dads.essence.repository.SEMethodRepository;
import mx.infotec.dads.essence.repository.SEPracticeAssetRepository;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.essence.repository.SEStateRepository;
import mx.infotec.dads.essence.repository.SEActivitySpaceRepository;
import mx.infotec.dads.essence.repository.SEAlphaAssociationRepository;
import mx.infotec.dads.essence.repository.SEAlphaContainmentRepository;
import mx.infotec.dads.essence.repository.SECheckpointRepository;
import mx.infotec.dads.essence.repository.SECompetencyLevelRepository;
import mx.infotec.dads.essence.repository.SECompletionCriterionRepository;
import mx.infotec.dads.essence.repository.SEEntryCriterionRepository;
import mx.infotec.dads.essence.repository.SELevelOfDetailRepository;
import mx.infotec.dads.essence.repository.SEMergeResolutionRepository;
import mx.infotec.dads.essence.repository.SEUserDefinedTypeRepository;
import mx.infotec.dads.essence.repository.SEWorkProductManifestRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.BasicElementDto;
import mx.infotec.dads.sekc.admin.kernel.dto.ElementGroupDto;
import mx.infotec.dads.sekc.admin.kernel.dto.Extension;
import mx.infotec.dads.sekc.admin.kernel.dto.FeatureSelection;
import mx.infotec.dads.sekc.admin.kernel.dto.LanguageElementDto;
import mx.infotec.dads.sekc.admin.kernel.dto.MergeResolution;
import mx.infotec.dads.sekc.admin.kernel.dto.OwnedElement;
import mx.infotec.dads.sekc.admin.kernel.dto.PatternAssociation;
import mx.infotec.dads.sekc.admin.kernel.dto.Property;
import mx.infotec.dads.sekc.admin.kernel.dto.Referrer;
import mx.infotec.dads.sekc.admin.kernel.dto.Resource;
import mx.infotec.dads.sekc.admin.kernel.dto.Tag;
import mx.infotec.dads.sekc.admin.kernel.dto.ViewSelection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author wisog
 */
@Service
public class RandomRepositoryUtil {
    
    @Autowired
    private SEKernelRepository kernelRepository;
    @Autowired
    private SELibraryRepository libraryRepository;
    @Autowired
    private SEMethodRepository methodRepository;
    @Autowired
    private SEPracticeRepository practiceRepository;
    @Autowired
    private SEPracticeAssetRepository practiceAssetRepository;
    @Autowired
    private SEStateRepository stateRepository;
    @Autowired
    private SEActivitySpaceRepository activitySpaceRepository;
    @Autowired
    private SELevelOfDetailRepository levelOfDetailRepository;
    @Autowired
    private SECompetencyLevelRepository competencyLevelRepository;
    @Autowired
    private SECheckpointRepository checkPointRepository;
    @Autowired
    private SEEntryCriterionRepository entryCriterionRepository;
    @Autowired
    private SEMergeResolutionRepository mergeResolutionRepository;
    @Autowired
    private SEUserDefinedTypeRepository userDefinedTypeRepository;
    @Autowired
    private SECompletionCriterionRepository completionCriterionRepository;
    @Autowired
    private SEActionRepository actionRepository;
    @Autowired
    private SEAlphaContainmentRepository alphaContainmentRepository;
    @Autowired
    private SEAlphaAssociationRepository alphaAssociationRepository;
    @Autowired
    private SEWorkProductManifestRepository workProductManifestRepository;
    
    private final Map<String, MongoRepository> typeRepositoryMap; 
    
    public RandomRepositoryUtil(){
        typeRepositoryMap = new HashMap<>();
        typeRepositoryMap.put("SEKernel", kernelRepository);
        typeRepositoryMap.put("SELibrary", libraryRepository);
        typeRepositoryMap.put("SEMethod", methodRepository);
        typeRepositoryMap.put("SEPractice", practiceRepository);
        typeRepositoryMap.put("SEPracticeAsset", practiceAssetRepository);
        typeRepositoryMap.put("SEState", stateRepository);
        typeRepositoryMap.put("SEActivitySpace", activitySpaceRepository);
        typeRepositoryMap.put("SELevelOfDetail", levelOfDetailRepository);
        typeRepositoryMap.put("SECompetencyLevel", competencyLevelRepository);
        typeRepositoryMap.put("SECheckpoint", checkPointRepository);
        typeRepositoryMap.put("SECriterion", entryCriterionRepository);
        typeRepositoryMap.put("SEMergeResolution", mergeResolutionRepository);
        typeRepositoryMap.put("SEUserDefinedType", userDefinedTypeRepository);
        typeRepositoryMap.put("SEAction", actionRepository);
        typeRepositoryMap.put("SEAlphaContainment", alphaContainmentRepository);
        typeRepositoryMap.put("SEAlphaAssociation", alphaAssociationRepository);
        typeRepositoryMap.put("SEWorkProductManifest", workProductManifestRepository);
    }
        
    
    
    public void fillSELaguageElementFields(SELanguageElement elementToPersistence, LanguageElementDto languageElementDto) {
        if (languageElementDto.getSuppressable() != null)
            elementToPersistence.setSuppressable(languageElementDto.getSuppressable());
        
        if (languageElementDto.getOwner() != null){
            SEElementGroup owner = getCorrectDocument(languageElementDto.getOwner().getType(), languageElementDto.getOwner().getIdOwner());
            if (owner != null)
                elementToPersistence.setOwner(owner);
        }
        if (languageElementDto.getTag() != null){
            languageElementDto.getTag().stream().map((tag) -> (SETag) getDocument(tag.getIdTag(), SETag.class)).filter((seTag) -> (seTag != null)).forEachOrdered((seTag) -> {
                elementToPersistence.getTag().add(seTag);
        });}
        if (languageElementDto.getResource() != null){
            languageElementDto.getResource().stream().map((resource) -> (SEResource) getDocument(resource.getIdResource(), SEResource.class)).filter((seResource) -> (seResource != null)).forEachOrdered((seResource) -> {
                elementToPersistence.getResource().add(seResource);
        });}
        if (languageElementDto.getProperties() != null){
            languageElementDto.getProperties().stream().map((property) -> (SEEndeavorProperty) getDocument(property.getIdProperties(), SEEndeavorProperty.class)).filter((seEndeavorProperty) -> (seEndeavorProperty != null)).forEachOrdered((seEndeavorProperty) -> {
                elementToPersistence.getProperties().add(seEndeavorProperty);
        });}
        if (languageElementDto.getViewSelection() != null){
            languageElementDto.getViewSelection().stream().map((viewSelection) -> (SEViewSelection) getDocument(viewSelection.getIdViewSelection(), SEViewSelection.class)).filter((seViewSelection) -> (seViewSelection != null)).forEachOrdered((seViewSelection) -> {
                elementToPersistence.getViewSelection().add(seViewSelection);
        });}
        if (languageElementDto.getFeatureSelection() != null){
            languageElementDto.getFeatureSelection().stream().map((featureSelection) -> (SEFeatureSelection) getDocument(featureSelection.getIdFeatureSelection(), SEFeatureSelection.class)).filter((seFeatureSelection) -> (seFeatureSelection != null)).forEachOrdered((seFeatureSelection) -> {
                elementToPersistence.getFeatureSelection().add(seFeatureSelection);
        });}
        if (languageElementDto.getExtension() != null){
            languageElementDto.getExtension().stream().map((extension) -> (SEExtensionElement) getDocument(extension.getIdExtension(), SEExtensionElement.class)).filter((seExtension) -> (seExtension != null)).forEachOrdered((seExtension) -> {
                elementToPersistence.getExtension().add(seExtension);
        });}
        if (languageElementDto.getReferrer() != null){
            languageElementDto.getReferrer().stream().map((referrer) -> getCorrectDocument(referrer.getType(), referrer.getIdReferrer())).filter((seElementGroup) -> (seElementGroup != null)).forEachOrdered((seElementGroup) -> {
                elementToPersistence.getReferrer().add(seElementGroup);
        });}
        if (languageElementDto.getPatternAssociation() != null){
            languageElementDto.getPatternAssociation().stream().map((patternAssociation) -> (SEPatternAssociation) getDocument(patternAssociation.getIdPatternAssociation(), SEPatternAssociation.class)).filter((sePatternAssociation) -> (sePatternAssociation != null)).forEachOrdered((sePatternAssociation) -> {
                elementToPersistence.getPatternAssociation().add(sePatternAssociation);
        });}
    }
    
    public void fillSEBasicElementFields( SEBasicElement elementToPersistence, BasicElementDto basicElementDto) {
        
        fillSELaguageElementFields(elementToPersistence, basicElementDto);
        
        //properties of SEBasicElement
        elementToPersistence.setName( basicElementDto.getName());
        elementToPersistence.setBriefDescription( basicElementDto.getBriefDescription());
        elementToPersistence.setDescription( basicElementDto.getDescription());
        
        // TODO pendiente implementar GraphicalElement
        //    "icon":                 GraphicalElement
    }
    
    public void fillSEElementGroupFields( SEElementGroup elementToPersistence, ElementGroupDto elementGroupDto) {
        
        fillSELaguageElementFields(elementToPersistence, elementGroupDto);
        
        //properties of SEElementGroup
        elementToPersistence.setName( elementGroupDto.getName());
        elementToPersistence.setBriefDescription(elementGroupDto.getBriefDescription());
        elementToPersistence.setDescription(elementGroupDto.getDescription());
        // TODO pendiente implementar GraphicalElement
        //    "icon":                 GraphicalElement
        
        if (elementGroupDto.getMergeResolution() != null){
            elementGroupDto.getMergeResolution().stream().map((mergeResolution) -> (SEMergeResolution) getDocument(mergeResolution.getIdMergeResolution(), SEMergeResolution.class)).filter((seMergeResolution) -> (seMergeResolution != null)).forEachOrdered((seMergeResolution) -> {
                elementToPersistence.getMergeResolution().add(seMergeResolution);
            });
        }
        
        // TODO: correctDocument for SELanguageElement objects
        if ( elementGroupDto.getOwnedElements() != null){
            for (OwnedElement ownedElement : elementGroupDto.getOwnedElements()){
                SELanguageElement seOwnedElement = getCorrectDocument( ownedElement.getType(), ownedElement.getIdOwnedElements() );
            if (seOwnedElement != null)
                elementToPersistence.getOwnedElements().add(seOwnedElement);
                
            }
        }
        
            
        
        
        if (map.containsKey("referredElements") && map.get("referredElements") != null ){
            List<SELanguageElement> referredElements = getCorrectDocument( (String) map.get("referredElements") );
            if (referredElements != null)
                elementToPersistence.setReferredElements( referredElements );
        }
    }
    
    public void fillSEResourceFields( SEResource elementToPersistence, Map<String, Object> map){
        //TODO fillSELaguageElementFields(elementToPersistence, map);
        
        //properties of SEBasicElement
        elementToPersistence.setContent((String) map.get("content"));
        
        /* TODO: correctDocument for SELanguageElement objects
        if (map.containsKey("languageElement") && map.get("languageElement") != null ){
            SELanguageElement languageElement = getCorrectDocument( (String) map.get("languageElement"));
            if ( languageElement != null )
                elementToPersistence.setLanguageElement(languageElement);
        }*/
    }
    
    private List getDocsCollection(Map<String, Object> map, String key, Class clazz){
        
        String iDs = map.get(key).toString().substring(1, map.get(key).toString().length()-1 );
        List<String> iDsArray = Arrays.asList( iDs.split(","));
        List collection = new ArrayList<>();
        for (String tagID: iDsArray){
            if (getDocument( tagID.trim(), clazz ) != null )
                collection.add( getDocument(tagID.trim(), clazz ) );
        }
        return collection;
    }
    
    public List getDocuments(ArrayList<String> arrayDocs, Class clazz){
        List collection = new ArrayList<>();
        for (String tagID: arrayDocs){
            Object document = getDocument( tagID.trim(), clazz );
            if ( document != null )
                collection.add( document );
        }
        return collection;
    }
    
    public SECriterion getCorrectCriterionDocument( String type, String id) {
        SECriterion criterion;
        switch (type){
            case "completitionCriterion":
                criterion = completionCriterionRepository.findOne(id);
            break;
            case "entryCriterion":
                criterion = entryCriterionRepository.findOne(id);
            break;
            default:
                criterion = null;
            break;
        }
        
        return criterion;
    }
    
    private SEElementGroup getCorrectDocument( String type, String id) {
        SEElementGroup elementGroup;
        switch (type){
            case "kernel":
                elementGroup = kernelRepository.findOne(id);
            break;
            case "library":
                elementGroup = libraryRepository.findOne(id);
            break;
            case "method":
                elementGroup = methodRepository.findOne(id);
            break;
            case "practice":
                elementGroup = practiceRepository.findOne(id);
            break;
            case "practiceAsset":
                elementGroup = practiceAssetRepository.findOne(id);
            break;
            default:
                elementGroup = null;
            break;
        }
        return elementGroup;
    }
    
    private SEElementGroup getCorrectDocument( String id) {
        SEElementGroup elementGroup;
        elementGroup = kernelRepository.findOne(id);
        if (elementGroup == null)
            elementGroup = libraryRepository.findOne(id);
        if (elementGroup == null)
            elementGroup = methodRepository.findOne(id);
        if (elementGroup == null)
            elementGroup = practiceRepository.findOne(id);
        if (elementGroup == null)
            elementGroup = practiceAssetRepository.findOne(id);
        
        return elementGroup;
    }
    
    public Object getDocument( String id, Class clazz){
        Object element;
        if (typeRepositoryMap.containsKey(clazz.getSimpleName()))
            element = typeRepositoryMap.get(clazz.getSimpleName()).findOne(id);
        else
            element = null;
        return element;
    }
}