package mx.infotec.dads.sekc.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECompletionCriterion;
import mx.infotec.dads.essence.model.activityspaceandactivity.SECriterion;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEEntryCriterion;

import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SELanguageElement;
import mx.infotec.dads.essence.model.foundation.extention.SEAreaOfConcern;

/**
 * EssenceFilter
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class EssenceFilter {

    private EssenceFilter() {

    }

    /**
     * SEAreaOfConcern filter, it filter the SEAreaOfConcern elements into a
     * SEKernel object
     * 
     * 
     * @param SEKernel
     * @return
     */
    public static List<SEAreaOfConcern> filterAreaOfConcerns(SEKernel seKernel) {
        return seKernel.getOwnedElements().parallelStream()
                .filter(languageElement -> languageElement instanceof SEAreaOfConcern)
                .map(languageElement -> (SEAreaOfConcern) languageElement).collect(Collectors.toList());
    }

    /**
     * SEAreaOfConcernsFilter, it filter the SEAreaOfConcern elements into a
     * Collection<SELanguageElements>
     * 
     * 
     * @param languageElements
     * @return
     */
    public static List<SEAreaOfConcern> filterAreaOfConcerns(Collection<SELanguageElement> languageElements) {
        return languageElements.parallelStream().filter(languageElement -> languageElement instanceof SEAreaOfConcern)
                .map(languageElement -> (SEAreaOfConcern) languageElement).collect(Collectors.toList());
    }

    /**
     * Generic Filter, it filter the elements specified into the targetClass
     * param
     * 
     * @param languageElements
     * @param targetClass
     * @return
     */
    public static <T> List<T> filterLanguageElement(Collection<SELanguageElement> languageElements,
            Class<T> targetClass) {
        return languageElements.parallelStream().filter(element -> targetClass.isInstance(element))
                .map(element -> targetClass.cast(element)).collect(Collectors.toList());
    }
    
    public static <T> Collection<SECriterion> filterCriterionElement(Collection<SECriterion> criterionElements, boolean entry){
            if (entry)
                return  criterionElements.parallelStream().filter(element -> element instanceof SEEntryCriterion)
                    .map(element -> (SECriterion) element).collect(Collectors.toList());
            return  criterionElements.parallelStream().filter(element -> element instanceof SECompletionCriterion)
                    .map(element -> (SECriterion) element).collect(Collectors.toList());
    }
}
