package mx.infotec.dads.sekc.admin.repositories.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.infotec.dads.sekc.admin.repositories.dto.RepositoryDTO;
import mx.infotec.dads.sekc.admin.repositories.repository.RepositoryRepository;
import mx.infotec.dads.sekc.admin.repositories.service.RepositoryService;
import mx.infotec.dads.sekc.admin.repositories.service.util.RepositoryMapper;
import mx.infotec.dads.sekc.domain.Repository;

/**
 * Service Implementation for managing Repository.
 * 
 * @author Daniel Cortes Pichardo
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final Logger log = LoggerFactory.getLogger(RepositoryServiceImpl.class);

    @Autowired
    private RepositoryRepository repositoryRepository;

    /**
     * Save a repository.
     *
     * @param repositoryDTO
     *            the entity to save
     * @return the persisted entity
     */
    @Override
    public RepositoryDTO save(RepositoryDTO repositoryDTO) {
        log.debug("Request to save Repository : {}", repositoryDTO);
        Repository repository = RepositoryMapper.toEntity(repositoryDTO);
        repository = repositoryRepository.save(repository);
        return RepositoryMapper.toDto(repository);
    }

    /**
     * Get all the repositories.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Override
    public Page<RepositoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Repositories");
        return repositoryRepository.findAll(pageable).map(RepositoryMapper::toDto);
    }

    /**
     * Get one repository by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Override
    public RepositoryDTO findOne(String id) {
        log.debug("Request to get Repository : {}", id);
        Repository repository = repositoryRepository.findOne(id);
        return RepositoryMapper.toDto(repository);
    }

    /**
     * Delete the repository by id.
     *
     * @param id
     *            the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Repository : {}", id);
        repositoryRepository.delete(id);
    }
}
