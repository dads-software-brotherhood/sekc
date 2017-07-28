package mx.infotec.dads.sekc.admin.practice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.sekc.admin.kernel.repository.RandomRepositoryUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.RandomUtil;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeConsultDto;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import mx.infotec.dads.sekc.admin.practice.service.util.PracticeConsultDtoMapper;
import mx.infotec.dads.sekc.web.rest.errors.ErrorConstants;

/**
 *
 * @author wisog
 */
@Service
public class PracticeServiceImpl implements PracticeService {

    @Autowired
    private RandomRepositoryUtil repositoryUtil;
    @Autowired
    private SEPracticeRepository practiceRepository;

    private final Logger LOG = LoggerFactory.getLogger(PracticeServiceImpl.class);
    private ResponseWrapper response;

    private boolean getPracticeFromRequest(PracticeDto practiceDto, SEPractice sePractice) {
        try {
            EssenceMapping.fillPractice(sePractice);
            sePractice.setAuthor(practiceDto.getAuthor());
            sePractice.setBriefDescription(practiceDto.getBriefDesciption());
            sePractice.setConsistencyRules(practiceDto.getConsistencyRules());
            sePractice.setKeyWords(practiceDto.getKeywords());
            sePractice.setName(practiceDto.getName());
            sePractice.setDescription(practiceDto.getDescription());
            sePractice.setObjective(practiceDto.getObjective());

            if (practiceDto.getConditions().getMeasures() != null)
                sePractice.setMeasures(practiceDto.getConditions().getMeasures());

            if (practiceDto.getIdKernel() != null) {
                SEKernel seKernel = (SEKernel) repositoryUtil.getDocument(practiceDto.getIdKernel(), SEKernel.class);
                if (seKernel != null)
                    sePractice.setOwner(seKernel);
            }
            // DCP -> Comendado para adapar de acuerdo al nuevo PracticeDTO.

            // entryCriterion
            // if (practiceDto.getConditions().getEntries() != null){
            // Collection<SECriterion> entryCriterionList = new ArrayList<>();
            // practiceDto.getConditions().getEntries().forEach((entry) -> {
            // SEEntryCriterion entryCriterion = new SEEntryCriterion();
            //
            // SEState state = (SEState) repositoryUtil.getDocument(
            // entry.getAlphaStates().getIdState() , SEState.class);
            // if (state != null)
            // entryCriterion.setState(state);
            // SELevelOfDetail levelofDetail = (SELevelOfDetail)
            // repositoryUtil.getDocument(
            // entry.getWorkProductsLevelofDetail().getIdLevelOfDetail() ,
            // SELevelOfDetail.class);
            // entryCriterion.setLevelOfDetail(levelofDetail);
            // entryCriterionList.add(entryCriterion);
            // sePractice.getEntry().addAll(entry.getOtherConditions());
            // });
            // sePractice.setEntryCriterion(entryCriterionList);
            // }
            // // ---- entryCriterion
            // // resultCriterion
            // if (practiceDto.getConditions().getResults() != null){
            // Collection<SECriterion> resultCriterionList = new ArrayList<>();
            // practiceDto.getConditions().getResults().forEach((result) -> {
            // SECompletionCriterion resultCriterion = new
            // SECompletionCriterion();
            //
            // SEState state = (SEState) repositoryUtil.getDocument(
            // result.getAlphaStates().getIdState() , SEState.class);
            // if (state != null)
            // resultCriterion.setState(state);
            // SELevelOfDetail levelofDetail = (SELevelOfDetail)
            // repositoryUtil.getDocument(
            // result.getWorkProductsLevelofDetail().getIdLevelOfDetail() ,
            // SELevelOfDetail.class);
            // resultCriterion.setLevelOfDetail(levelofDetail);
            // resultCriterionList.add(resultCriterion);
            // sePractice.getResult().addAll(result.getOtherConditions());
            // });
            // sePractice.setResultCriterion(resultCriterionList);
            // }
            // ---- resultCriterion

            return true;
        } catch (Exception e) {
            LOG.debug("Fail to obtain the needed FIELDS ", e);
            return false;
        }
    }

    @Override
    public ResponseWrapper save(PracticeDto practiceDto) {
        SEPractice sePractice = new SEPractice();
        response = new ResponseWrapper();
        if (!getPracticeFromRequest(practiceDto, sePractice)) {
            response.setError_message(ErrorConstants.ERR_MALFORMED_REQUEST);
            response.setResponse_code(HttpStatus.BAD_REQUEST);
        } else {
            practiceRepository.save(sePractice);
            response.setResponseObject(sePractice);
            response.setResponse_code(HttpStatus.OK);
        }

        // After object recreaion, before save it is a MUST to create a function
        // to check the Data in the object
        // if (ValidationsController.checkValid(practiceToPersistence) ){
        // response.setError_message( "Invalid practice object, it can't be
        // persisted" );
        // response.setError_message( ErrorConstants.ERR_MALFORMED_REQUEST);
        // }
        return response;
    }

    /**
     * Get all the repositories.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Override
    public Page<PracticeConsultDto> findAll(Pageable pageable) {
        LOG.debug("Request to get all Repositories");
        return practiceRepository.findAll(pageable).map(PracticeConsultDtoMapper::toDto);
    }

    @Override
    public ResponseWrapper findOne(String id, List includeFields) {

        response = new ResponseWrapper();
        Object document = practiceRepository.findOne(id);
        if (document != null) {
            response.setResponse_code(HttpStatus.OK);
            if (includeFields != null)
                document = RandomUtil.filterResponseFields(document, includeFields);
            response.setResponseObject(document);
        } else {
            response.setError_message(ErrorConstants.ERR_RECORD_NOT_FOUND);
            response.setResponse_code(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public ResponseWrapper delete(String id) {
        response = new ResponseWrapper();
        response.setResponseObject(practiceRepository.findOne(id));
        practiceRepository.delete(id);
        response.setResponse_code(HttpStatus.OK);
        return response;
    }

}
