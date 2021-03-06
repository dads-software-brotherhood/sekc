package mx.infotec.dads.sekc.admin.practice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeConsultDto;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;

/**
 *
 * @author wisog
 */
public interface PracticeService {

    public ResponseWrapper save(PracticeDto practice);

    public ResponseWrapper findOne(String id, List<String> includeFields);

    public ResponseWrapper delete(String id);
    
    public ResponseWrapper update(PracticeDto practice);
    /**
     * Get all the practices.
     *
     * @param pageable
     *            the pagination information
     * @param keywords
     *            the keywords to use as filter
     * @return the list of entities
     */
    Page<PracticeConsultDto> findAll(Pageable pageable, List<String> keywords);
}
