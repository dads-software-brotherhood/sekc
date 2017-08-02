package mx.infotec.dads.sekc.admin.kernel.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import mx.infotec.dads.essence.repository.SEActivityAssociationRepository;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.essence.repository.SELibraryRepository;
import mx.infotec.dads.essence.repository.SEMethodRepository;
import mx.infotec.dads.essence.repository.SEPracticeAssetRepository;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.essence.repository.SEStateRepository;
import mx.infotec.dads.essence.repository.SEActivitySpaceRepository;
import mx.infotec.dads.essence.repository.SEAlphaAssociationRepository;
import mx.infotec.dads.essence.repository.SEAlphaContainmentRepository;
import mx.infotec.dads.essence.repository.SEAlphaRepository;
import mx.infotec.dads.essence.repository.SECheckpointRepository;
import mx.infotec.dads.essence.repository.SECompetencyLevelRepository;
import mx.infotec.dads.essence.repository.SECompetencyRepository;
import mx.infotec.dads.essence.repository.SECompletionCriterionRepository;
import mx.infotec.dads.essence.repository.SEEntryCriterionRepository;
import mx.infotec.dads.essence.repository.SEExtensionElementRepository;
import mx.infotec.dads.essence.repository.SEFeatureSelectionRepository;
import mx.infotec.dads.essence.repository.SELevelOfDetailRepository;
import mx.infotec.dads.essence.repository.SEMergeResolutionRepository;
import mx.infotec.dads.essence.repository.SEPatternAssociationRepository;
import mx.infotec.dads.essence.repository.SEPatternRepository;
import mx.infotec.dads.essence.repository.SEResourceRepository;
import mx.infotec.dads.essence.repository.SETagRepository;
import mx.infotec.dads.essence.repository.SEUserDefinedTypeRepository;
import mx.infotec.dads.essence.repository.SEViewSelectionRepository;
import mx.infotec.dads.essence.repository.SEWorkProductManifestRepository;
import mx.infotec.dads.essence.repository.SEWorkProductRepository;
import mx.infotec.dads.sekc.admin.kernel.dto.BasicElementDto;
//import mx.infotec.dads.sekc.admin.kernel.dto.ElementGroupDto;
import mx.infotec.dads.sekc.admin.kernel.dto.Extension;
import mx.infotec.dads.sekc.admin.kernel.dto.FeatureSelection;
import mx.infotec.dads.sekc.admin.kernel.dto.LanguageElementDto;
//import mx.infotec.dads.sekc.admin.kernel.dto.MergeResolution;
//import mx.infotec.dads.sekc.admin.kernel.dto.OwnedElement;
import mx.infotec.dads.sekc.admin.kernel.dto.PatternAssociation;
import mx.infotec.dads.sekc.admin.kernel.dto.Property;
//import mx.infotec.dads.sekc.admin.kernel.dto.ReferredElement;
import mx.infotec.dads.sekc.admin.kernel.dto.Referrer;
import mx.infotec.dads.sekc.admin.kernel.dto.Resource;
import mx.infotec.dads.sekc.admin.kernel.dto.Tag;
import mx.infotec.dads.sekc.admin.kernel.dto.ViewSelection;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private SECompletionCriterionRepository completitionCriterionRepository;
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
    @Autowired
    private SEActivityAssociationRepository activityAssociationRepository;
    @Autowired
    private SEExtensionElementRepository extensionElementRepository;
    @Autowired
    private SEPatternAssociationRepository patternAssociationRepository;
    @Autowired
    private SEResourceRepository resourceRepository;
    @Autowired
    private SETagRepository tagRepository;
    @Autowired
    private SEPatternRepository patternRepository;
    @Autowired
    private SEWorkProductRepository workProductRepository;
    @Autowired
    private SEAlphaRepository alphaRepository;
    @Autowired
    private SECompetencyRepository competencyRepository;
    @Autowired
    private SEFeatureSelectionRepository featureSelectionRepository;
    @Autowired
    private SEViewSelectionRepository viewSelectionRepository;
    @Autowired
    public MongoTemplate mongoTemplate;
    
    
    
    public void fillSELaguageElementFields(SELanguageElement elementToPersistence, LanguageElementDto languageElementDto) {
        if (languageElementDto.getSuppressable() != null)
            elementToPersistence.setSuppressable(languageElementDto.getSuppressable());
        
        if (languageElementDto.getOwner() != null){
            SEElementGroup owner = (SEElementGroup) getCorrectDocument(languageElementDto.getOwner().getType(), languageElementDto.getOwner().getIdOwner());
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
                elementToPersistence.getReferrer().add((SEElementGroup) seElementGroup);
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
    /* TODO:
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
        
        if ( elementGroupDto.getOwnedElements() != null){
            elementGroupDto.getOwnedElements().stream().map((ownedElement) -> getCorrectDocument( ownedElement.getType(), ownedElement.getIdOwnedElements() )).filter((seOwnedElement) -> (seOwnedElement != null)).forEachOrdered((seOwnedElement) -> {
                elementToPersistence.getOwnedElements().add(seOwnedElement);
            });
        }
        
        if ( elementGroupDto.getReferredElements()!= null){
            elementGroupDto.getReferredElements().stream().map((referredElement) -> getCorrectDocument( referredElement.getType(), referredElement.getIdReferredElements() )).filter((seReferredElement) -> (seReferredElement != null)).forEachOrdered((seReferredElement) -> {
                elementToPersistence.getReferredElements().add(seReferredElement);
            });
        }
    }*/
    
    public void fillSEResourceFields( SEResource elementToPersistence, Map<String, Object> map){
        //TODO fillSELaguageElementFields(elementToPersistence, map);
        
        //properties of SEBasicElement
        elementToPersistence.setContent((String) map.get("content"));
        
        /*
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
    
    private SELanguageElement getCorrectDocument( String type, String id) {
        SELanguageElement languageElement;
        switch (type){
            case "kernel":
                languageElement = kernelRepository.findOne(id);
            break;
            case "library":
                languageElement = libraryRepository.findOne(id);
            break;
            case "method":
                languageElement = methodRepository.findOne(id);
            break;
            case "practice":
                languageElement = practiceRepository.findOne(id);
            break;
            case "practiceAsset":
                languageElement = practiceAssetRepository.findOne(id);
            break;
            case "SEAction":
                languageElement = actionRepository.findOne(id);
            break;
            case "SEActivityAssociation":
                languageElement = activityAssociationRepository.findOne(id);
            break;
            case "alphaAssociation":
                languageElement = alphaAssociationRepository.findOne(id);
            break;
            case "SEAlphaContainment":
                languageElement = alphaContainmentRepository.findOne(id);
            break;
            case "SELevelOfDetail":
                languageElement = levelOfDetailRepository.findOne(id);
            break;
            case "SEState":
                languageElement =  stateRepository.findOne(id );
            break;
            case "workProductManifest":
                languageElement = workProductManifestRepository.findOne(id);
            break;
            case "workProduct":
                languageElement = workProductRepository.findOne(id);
            break;
            case "SECompetencyLevel":
                languageElement = competencyLevelRepository.findOne(id);
            break;
            case "competency":
                languageElement = competencyRepository.findOne(id);
            break;
            case "SECheckpoint":
                languageElement = checkPointRepository.findOne(id);
            break;
            case "SEExtensionElement":
                languageElement = extensionElementRepository.findOne(id);
            break;
            case "SEMergeResolution":
                languageElement = mergeResolutionRepository.findOne(id);
            break;
            case "SEPatternAssociation":
                languageElement = patternAssociationRepository.findOne(id);
            break;
            case "SEResource":
                languageElement = resourceRepository.findOne(id);
            break;
            case "SETag":
                languageElement = tagRepository.findOne(id);
            break;
            case "SEPattern":
                languageElement = patternRepository.findOne(id);
            break;
            case "SEActivitySpace":
                languageElement = activitySpaceRepository.findOne(id);
            break;
            case "SEFeatureSelection":
                languageElement = featureSelectionRepository.findOne(id);
            break;
            case "SECriterion":
                languageElement = entryCriterionRepository.findOne(id);
            break;
            case "SEUserDefinedType":
                languageElement = userDefinedTypeRepository.findOne(id);
            break;
            case "SEViewSelection":
                languageElement = viewSelectionRepository.findOne(id);
            break;
            case "completitionCriterion":
                languageElement = completitionCriterionRepository.findOne(id);
            break;
            case "entryCriterion":
                languageElement = entryCriterionRepository.findOne(id);
            break;
            default:
                languageElement = null;
            break;
        }
        Objects.requireNonNull(languageElement, "The id " + id + " doesn't exists on " + type);
        return languageElement;
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
        Objects.requireNonNull(elementGroup, "The id " + id + " doesn't exists");
        return elementGroup;
    }
    
    public Object getDocument( String id, Class clazz){
        Object element;
        switch ( clazz.getSimpleName()){
            case "SEAction":
                element = actionRepository.findOne(id);
            break;
            case "SEAlpha":
                element = alphaRepository.findOne(id);
            break;
            case "SEActivityAssociation":
                element = activityAssociationRepository.findOne(id);
            break;
            case "SEAlphaAssociation":
                element = alphaAssociationRepository.findOne(id);
            break;
            case "SEAlphaContainment":
                element = alphaContainmentRepository.findOne(id);
            break;
            case "SELevelOfDetail":
                element = levelOfDetailRepository.findOne(id);
            break;
            case "SEState":
                element =  stateRepository.findOne(id );
            break;
            case "SEWorkProductManifest":
                element = workProductManifestRepository.findOne(id);
            break;
            case "SEWorkProduct":
                element = workProductRepository.findOne(id);
            break;
            case "SECompetencyLevel":
                element = competencyLevelRepository.findOne(id);
            break;
            case "SECompetency":
                element = competencyRepository.findOne(id);
            break;
            case "SECheckpoint":
                element = checkPointRepository.findOne(id);
            break;
            case "SEExtensionElement":
                element = extensionElementRepository.findOne(id);
            break;
            case "SEMergeResolution":
                element = mergeResolutionRepository.findOne(id);
            break;
            case "SEPatternAssociation":
                element = patternAssociationRepository.findOne(id);
            break;
            case "SEResource":
                element = resourceRepository.findOne(id);
            break;
            case "SETag":
                element = tagRepository.findOne(id);
            break;
            case "SEPattern":
                element = patternRepository.findOne(id);
            break;
            case "SEKernel":
                element = kernelRepository.findOne(id);
            break;
            case "SELibrary":
                element =  libraryRepository.findOne(id );
            break;
            case "SEMethod":
                element =  methodRepository.findOne(id );
            break;
            case "SEPractice":
                element =  practiceRepository.findOne(id );
            break;
            case "SEPracticeAsset":
                element =  practiceAssetRepository.findOne(id );
            break;
            case "SEActivitySpace":
                element = activitySpaceRepository.findOne(id);
            break;
            case "SEFeatureSelection":
                element = featureSelectionRepository.findOne(id);
            break;
            case "SECriterion":
                element = entryCriterionRepository.findOne(id);
            break;
            case "SEUserDefinedType":
                element = userDefinedTypeRepository.findOne(id);
            break;
            case "SEViewSelection":
                element = viewSelectionRepository.findOne(id);
            break;
            default:
                element  = null;
            break;
        }
        Objects.requireNonNull(element, "The id " + id + " doesn't exist on " + clazz.getSimpleName());
        return element;
    }
}