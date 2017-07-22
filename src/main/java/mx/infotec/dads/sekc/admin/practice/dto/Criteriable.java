package mx.infotec.dads.sekc.admin.practice.dto;

import java.util.List;

/**
 * Criteriable interface used for process common operations into the PracticeDTO
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface Criteriable {

    /**
     * getAlphaStates
     * 
     * @return AlphaState
     */
    public AlphaState getAlphaStates();

    /**
     * get WorkProductsLevelOfDetail
     * 
     * @return WorkProductsLevelofDetail
     */
    public WorkProductsLevelofDetail getWorkProductsLevelofDetail();

    /**
     * get OtherConditions
     * 
     * @return List<String>
     */
    public List<String> getOtherConditions();

}
