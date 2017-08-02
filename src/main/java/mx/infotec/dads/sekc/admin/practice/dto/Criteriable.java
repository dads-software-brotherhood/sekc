package mx.infotec.dads.sekc.admin.practice.dto;

import java.util.List;

/**
 * Criteriable interface used for process common operations into the PracticeDTO
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public interface Criteriable {

    public void setAlphaStates(List<AlphaState> asList);
    public void setWorkProductsLevelofDetail(List<WorkProductsLevelofDetail> asList);
    public void setOtherConditions(List<String> asList);
    
    /**
     * getAlphaStates
     * 
     * @return AlphaState
     */
    public List<AlphaState> getAlphaStates();

    /**
     * get WorkProductsLevelOfDetail
     * 
     * @return WorkProductsLevelofDetail
     */
    public List<WorkProductsLevelofDetail> getWorkProductsLevelofDetail();

    /**
     * get OtherConditions
     * 
     * @return List<String>
     */
    public List<String> getOtherConditions();

}
