package mx.infotec.dads.sekc.admin.practice.service.util;

import java.util.ArrayList;
import java.util.List;

import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeConsultDto;

/**
 * RepositoryMapper, used for Mapping Repository Entity to PracticeConsultDto
 * 
 * @author Daniel Cortes Pichardo
 */
public class PracticeConsultDtoMapper {

    private PracticeConsultDtoMapper() {

    }

    /**
     * Mapper for convert PracticeConsultDto to Repository entity
     * 
     * @param dto
     * @return Repository
     */
    public static SEPractice toEntity(PracticeConsultDto dto) {
        SEPractice entity = new SEPractice();
        entity.setId(dto.getIdPractice());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setBriefDescription(dto.getDescription());
        return entity;
    }

    /**
     * Mapper for convert Repository entity to PracticeConsultDto
     * 
     * @param entity
     * @return PracticeConsultDto
     */
    public static PracticeConsultDto toDto(SEPractice entity) {
        PracticeConsultDto dto = new PracticeConsultDto();
        dto.setIdPractice(entity.getId());
        dto.setName(entity.getName());
        dto.setObjective(entity.getObjective());
        dto.setDescription(entity.getDescription());
        dto.setBriefDesciption(entity.getBriefDescription());
        dto.setKeywords(entity.getKeyWords());
        dto.setLastModifiedDate(entity.getLastModifiedDate().toString());
        return dto;
    }

    /**
     * Mapper for convert a List<PracticeConsultDto> to List<Repository> entity
     * list
     * 
     * @param dtoList
     * @return List<Repository>
     */

    public static List<SEPractice> toEntity(List<PracticeConsultDto> dtoList) {
        List<SEPractice> practiceList = new ArrayList<>();
        for (PracticeConsultDto PracticeConsultDto : dtoList) {
            practiceList.add(toEntity(PracticeConsultDto));
        }
        return practiceList;
    }

    /**
     * Mapper for convert a List<Repository> entity list to
     * List<PracticeConsultDto>
     * 
     * @param entityList
     * @return List<PracticeConsultDto>
     */
    public static List<PracticeConsultDto> toDto(List<SEPractice> entityList) {
        List<PracticeConsultDto> practiceList = new ArrayList<>();
        for (SEPractice practice : entityList) {
            practiceList.add(toDto(practice));
        }
        return practiceList;

    }

}
