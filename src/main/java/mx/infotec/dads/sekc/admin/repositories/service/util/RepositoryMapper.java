package mx.infotec.dads.sekc.admin.repositories.service.util;

import java.util.ArrayList;
import java.util.List;

import mx.infotec.dads.sekc.admin.repositories.dto.RepositoryDTO;
import mx.infotec.dads.sekc.domain.Repository;

/**
 * RepositoryMapper, used for Mapping Repository Entity to RepositoryDTO
 * 
 * @author Daniel Cortes Pichardo
 */
public class RepositoryMapper {

    private RepositoryMapper() {

    }

    /**
     * Mapper for convert RepositoryDTO to Repository entity
     * 
     * @param dto
     * @return Repository
     */
    public static Repository toEntity(RepositoryDTO dto) {
        Repository entity = new Repository();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUrl(dto.getUrl());
        return entity;
    }

    /**
     * Mapper for convert Repository entity to RepositoryDTO
     * 
     * @param entity
     * @return RepositoryDTO
     */
    public static RepositoryDTO toDto(Repository entity) {
        RepositoryDTO dto = new RepositoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUrl(entity.getUrl());
        return dto;
    }

    /**
     * Mapper for convert a List<RepositoryDTO> to List<Repository> entity list
     * 
     * @param dtoList
     * @return List<Repository>
     */

    public static List<Repository> toEntity(List<RepositoryDTO> dtoList) {
        List<Repository> repositoryList = new ArrayList<>();
        for (RepositoryDTO repositoryDTO : dtoList) {
            repositoryList.add(toEntity(repositoryDTO));
        }
        return repositoryList;
    }

    /**
     * Mapper for convert a List<Repository> entity list to List<RepositoryDTO>
     * 
     * @param entityList
     * @return List<RepositoryDTO>
     */
    public static List<RepositoryDTO> toDto(List<Repository> entityList) {
        List<RepositoryDTO> repositoryList = new ArrayList<>();
        for (Repository repository : entityList) {
            repositoryList.add(toDto(repository));
        }
        return repositoryList;

    }

}
