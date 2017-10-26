/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.dads.sekc.admin.kernel.service.util;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.sekc.admin.practice.consult.dto.Alpha;
import mx.infotec.dads.sekc.admin.practice.consult.dto.State;
import mx.infotec.dads.sekc.admin.practice.consult.dto.WorkProduct;
import static mx.infotec.dads.sekc.admin.practice.service.util.ClassMatcher.matchState;
import static mx.infotec.dads.sekc.admin.practice.service.util.ClassMatcher.matchWorkProduct;

/**
 *
 * @author wisog
 */
public class AlphaConsultDtoMapper {
    
    private AlphaConsultDtoMapper(){}
    
    
    public static Alpha toDto(SEAlpha seAlpha){
        Alpha alpha = new Alpha();
        
        if (seAlpha == null) return alpha; // this launches the HttpStatus.NOT_FOUND
        
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
    
}
