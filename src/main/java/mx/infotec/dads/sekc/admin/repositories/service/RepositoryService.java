package mx.infotec.dads.sekc.admin.repositories.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.infotec.dads.sekc.admin.repositories.dto.RepositoryDTO;

/**
 * Service Interface for managing Repository.
 * 
 * @author Daniel Cortes Pichardo
 */
public interface RepositoryService {

    /**
     * Save a repository.
     *
     * @param repositoryDTO
     *            the entity to save
     * @return the persisted entity
     */
    RepositoryDTO save(RepositoryDTO repositoryDTO);

    /**
     * Get all the repositories.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    Page<RepositoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" repository.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    RepositoryDTO findOne(String id);

    /**
     * Delete the "id" repository.
     *
     * @param id
     *            the id of the entity
     */
    void delete(String id);
}
