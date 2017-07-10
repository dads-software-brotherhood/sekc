package mx.infotec.dads.sekc.admin.kernel.service;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author wisog
 */
public interface TypedResourceService {
    
    public ResponseWrapper save( Object typedResource );
    public ResponseWrapper findAll(Pageable pag);
    public ResponseWrapper findOne(String id, java.util.List includeFields);
    public ResponseWrapper delete(String id);
}
