package mx.infotec.dads.sekc.admin.practice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.CatalogsDto;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;

/**
 *
 * @author wisog
 */
public interface CatalogsService {

    public ResponseWrapper save(CatalogsDto practice);

    public ResponseWrapper findAll(Pageable pag);

    public ResponseWrapper findOne(String id, List<String> includeFields);

    public ResponseWrapper delete(String id);
}
