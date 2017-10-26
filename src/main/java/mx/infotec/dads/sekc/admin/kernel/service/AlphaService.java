package mx.infotec.dads.sekc.admin.kernel.service;

import java.util.List;
import mx.infotec.dads.sekc.admin.kernel.dto.AlphaDto;
import mx.infotec.dads.sekc.admin.practice.consult.dto.Alpha;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author wisog
 */
public interface AlphaService {
    
    public ResponseWrapper save( AlphaDto alpha );
    public Page<Alpha> findAll(Pageable pag);
    public Alpha findOne(String id);
    public ResponseWrapper findWorkProductList(String id);
    public ResponseWrapper delete(String id);
}
