package mx.infotec.dads.sekc.admin.repositories.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import mx.infotec.dads.sekc.admin.repositories.dto.RepositoryDTO;
import mx.infotec.dads.sekc.admin.repositories.service.RepositoryService;
import mx.infotec.dads.sekc.web.rest.util.ApiConstant;
import mx.infotec.dads.sekc.web.rest.util.HeaderUtil;
import mx.infotec.dads.sekc.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Repository.
 * 
 * @author Daniel Cortes Pichardo
 */
@RestController
@RequestMapping(ApiConstant.API_PATH)
public class RepositoryResource {

    private final Logger log = LoggerFactory.getLogger(RepositoryResource.class);

    private static final String ENTITY_NAME = "repository";

    private final RepositoryService repositoryService;

    public RepositoryResource(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * POST /repositories : Create a new repository.
     *
     * @param repositoryDTO
     *            the repositoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     *         new repositoryDTO, or with status 400 (Bad Request) if the
     *         repository has already an ID
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PostMapping("/repositories")
    @Timed
    public ResponseEntity<RepositoryDTO> createRepository(@Valid @RequestBody RepositoryDTO repositoryDTO)
            throws URISyntaxException {
        log.debug("REST request to save Repository : {}", repositoryDTO);
        if (repositoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                    "A new repository cannot already have an ID")).body(null);
        }
        RepositoryDTO result = repositoryService.save(repositoryDTO);
        return ResponseEntity.created(new URI("/api/repositories/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /repositories : Updates an existing repository.
     *
     * @param repositoryDTO
     *            the repositoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         repositoryDTO, or with status 400 (Bad Request) if the
     *         repositoryDTO is not valid, or with status 500 (Internal Server
     *         Error) if the repositoryDTO couldnt be updated
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PutMapping("/repositories")
    @Timed
    public ResponseEntity<RepositoryDTO> updateRepository(@Valid @RequestBody RepositoryDTO repositoryDTO)
            throws URISyntaxException {
        log.debug("REST request to update Repository : {}", repositoryDTO);
        if (repositoryDTO.getId() == null) {
            return createRepository(repositoryDTO);
        }
        RepositoryDTO result = repositoryService.save(repositoryDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, repositoryDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /repositories : get all the repositories.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         repositories in body
     */
    @GetMapping("/repositories")
    @Timed
    public ResponseEntity<List<RepositoryDTO>> getAllRepositories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Repositories");
        Page<RepositoryDTO> page = repositoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/repositories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /repositories/:id : get the "id" repository.
     *
     * @param id
     *            the id of the repositoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         repositoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/repositories/{id}")
    @Timed
    public ResponseEntity<RepositoryDTO> getRepository(@PathVariable String id) {
        log.debug("REST request to get Repository : {}", id);
        RepositoryDTO repositoryDTO = repositoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(repositoryDTO));
    }

    /**
     * DELETE /repositories/:id : delete the "id" repository.
     *
     * @param id
     *            the id of the repositoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/repositories/{id}")
    @Timed
    public ResponseEntity<Void> deleteRepository(@PathVariable String id) {
        log.debug("REST request to delete Repository : {}", id);
        repositoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
