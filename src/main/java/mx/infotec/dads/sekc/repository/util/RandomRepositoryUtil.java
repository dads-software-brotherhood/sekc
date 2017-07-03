package mx.infotec.dads.sekc.repository.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import mx.infotec.dads.essence.model.foundation.SEBasicElement;
import mx.infotec.dads.essence.model.foundation.SEElementGroup;
import mx.infotec.dads.essence.model.foundation.SEEndeavorProperty;
import mx.infotec.dads.essence.model.foundation.SEExtensionElement;
import mx.infotec.dads.essence.model.foundation.SEPatternAssociation;
import mx.infotec.dads.essence.model.foundation.SEResource;
import mx.infotec.dads.essence.model.foundation.SETag;
import mx.infotec.dads.essence.model.view.SEFeatureSelection;
import mx.infotec.dads.essence.model.view.SEViewSelection;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.essence.repository.SELibraryRepository;
import mx.infotec.dads.essence.repository.SEMethodRepository;
import mx.infotec.dads.essence.repository.SEPracticeAssetRepository;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
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
    
    
    
    public void fillSEBasicElementFields( SEBasicElement elementToPersistence, Map<String, Object> map) {
        
        elementToPersistence.setName((String) map.get("name"));
        elementToPersistence.setBriefDescription((String) map.get("briefDescription"));
        elementToPersistence.setDescription((String) map.get("description"));
        if (map.containsKey("suppressable"))
            elementToPersistence.setSuppressable((boolean) map.get("suppressable"));
        
        if (map.containsKey("owner") && map.get("owner") != null ){
            SEElementGroup owner = getCorrectDocument( (String) map.get("owner") );
            if (owner != null)
                elementToPersistence.setOwner( owner );
        }
        
        if (map.containsKey("tag") && map.get("tag") != null ){
            List<SETag> collectionTags = getDocsCollection(map, "tag", SETag.class);
            if (!collectionTags.isEmpty())
                elementToPersistence.setTag(collectionTags);
        }
        
        if (map.containsKey("resource") && map.get("resource") != null ){
            List<SEResource> collectionResources = getDocsCollection(map, "resource", SEResource.class);
            if (!collectionResources.isEmpty())
                elementToPersistence.setResource(collectionResources);
        }
        
        if (map.containsKey("properties") && map.get("properties") != null ){
            List<SEEndeavorProperty> collectionPropertys = getDocsCollection(map, "properties", SEEndeavorProperty.class);
            if (!collectionPropertys.isEmpty())
                elementToPersistence.setProperties(collectionPropertys);
        }
        
        if (map.containsKey("viewSelection") && map.get("viewSelection") != null ){
            List<SEViewSelection> collectionviewSelections = getDocsCollection(map, "viewSelection", SEViewSelection.class);
            if (!collectionviewSelections.isEmpty())
                elementToPersistence.setViewSelection(collectionviewSelections);
        }
        
        if (map.containsKey("featureSelection") && map.get("featureSelection") != null ){
            List<SEFeatureSelection> collectionfeatureSelections = getDocsCollection(map, "featureSelection", SEFeatureSelection.class);
            if (!collectionfeatureSelections.isEmpty())
                elementToPersistence.setFeatureSelection(collectionfeatureSelections);
        }
        
        if (map.containsKey("extension") && map.get("extension") != null ){
            List<SEExtensionElement> collectionextensions = getDocsCollection(map, "extension", SEExtensionElement.class);
            if (!collectionextensions.isEmpty())
                elementToPersistence.setExtension(collectionextensions);
        }
        
        if (map.containsKey("referrer") && map.get("referrer") != null ){
            SEElementGroup referrer = getCorrectDocument( (String) map.get("owner") );
            if (referrer != null)
                elementToPersistence.setOwner( referrer );
        }
        
        if (map.containsKey("patternAssociation") && map.get("patternAssociation") != null ){
            List<SEPatternAssociation> collectionpatternAssociations = getDocsCollection( map, "patternAssociation", SEPatternAssociation.class);
            if (!collectionpatternAssociations.isEmpty())
                elementToPersistence.setPatternAssociation(collectionpatternAssociations);
        }
        
        // TODO pendiente implementar GraphicalElement
        //    "icon":                 GraphicalElement
    }
    
    private List getDocsCollection(Map<String, Object> map, String key, Class clazz){
        
        String iDs = map.get(key).toString().substring(1, map.get(key).toString().length()-1 );
        List<String> iDsArray = Arrays.asList( iDs.split(","));
        List collection = new ArrayList<>();
        for (String tagID: iDsArray){
            if (getValue( tagID.trim(), clazz ) != null )
                collection.add( getValue(tagID.trim(), clazz ) );
        }
        return collection;
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
    
    private SEElementGroup getValue( String id, Class clazz){
        SEElementGroup element;
        switch ( clazz.getSimpleName()){
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
            default:
                element  = null;
        }
        return element;
    }
}