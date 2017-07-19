package mx.infotec.dads.sekc.admin.repositories.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import mx.infotec.dads.sekc.SekcApp;
import mx.infotec.dads.sekc.admin.repositories.dto.RepositoryDTO;
import mx.infotec.dads.sekc.admin.repositories.repository.RepositoryRepository;
import mx.infotec.dads.sekc.admin.repositories.service.RepositoryService;
import mx.infotec.dads.sekc.admin.repositories.service.util.RepositoryMapper;
import mx.infotec.dads.sekc.domain.Repository;
import mx.infotec.dads.sekc.web.rest.TestUtil;
import mx.infotec.dads.sekc.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the RepositoryResource REST controller.
 *
 * @see RepositoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SekcApp.class)
public class RepositoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRepositoryMockMvc;

    private Repository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RepositoryResource repositoryResource = new RepositoryResource(repositoryService);
        this.restRepositoryMockMvc = MockMvcBuilders.standaloneSetup(repositoryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repository createEntity() {
        Repository repository = new Repository().name(DEFAULT_NAME).url(DEFAULT_URL).description(DEFAULT_DESCRIPTION);
        return repository;
    }

    @Before
    public void initTest() {
        repositoryRepository.deleteAll();
        repository = createEntity();
    }

    @Test
    public void createRepository() throws Exception {
        int databaseSizeBeforeCreate = repositoryRepository.findAll().size();

        // Create the Repository
        RepositoryDTO repositoryDTO = repositoryMapper.toDto(repository);
        restRepositoryMockMvc.perform(post("/api/repositories").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repositoryDTO))).andExpect(status().isCreated());

        // Validate the Repository in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeCreate + 1);
        Repository testRepository = repositoryList.get(repositoryList.size() - 1);
        assertThat(testRepository.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRepository.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testRepository.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createRepositoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repositoryRepository.findAll().size();

        // Create the Repository with an existing ID
        repository.setId("existing_id");
        RepositoryDTO repositoryDTO = repositoryMapper.toDto(repository);

        // An entity with an existing ID cannot be created, so this API call
        // must fail
        restRepositoryMockMvc.perform(post("/api/repositories").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repositoryDTO))).andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = repositoryRepository.findAll().size();
        // set the field null
        repository.setName(null);

        // Create the Repository, which fails.
        RepositoryDTO repositoryDTO = repositoryMapper.toDto(repository);

        restRepositoryMockMvc.perform(post("/api/repositories").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repositoryDTO))).andExpect(status().isBadRequest());

        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = repositoryRepository.findAll().size();
        // set the field null
        repository.setUrl(null);

        // Create the Repository, which fails.
        RepositoryDTO repositoryDTO = repositoryMapper.toDto(repository);

        restRepositoryMockMvc.perform(post("/api/repositories").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repositoryDTO))).andExpect(status().isBadRequest());

        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRepositories() throws Exception {
        // Initialize the database
        repositoryRepository.save(repository);

        // Get all the repositoryList
        restRepositoryMockMvc.perform(get("/api/repositories?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(repository.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getRepository() throws Exception {
        // Initialize the database
        repositoryRepository.save(repository);

        // Get the repository
        restRepositoryMockMvc.perform(get("/api/repositories/{id}", repository.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(repository.getId()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingRepository() throws Exception {
        // Get the repository
        restRepositoryMockMvc.perform(get("/api/repositories/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void updateRepository() throws Exception {
        // Initialize the database
        repositoryRepository.save(repository);
        int databaseSizeBeforeUpdate = repositoryRepository.findAll().size();

        // Update the repository
        Repository updatedRepository = repositoryRepository.findOne(repository.getId());
        updatedRepository.name(UPDATED_NAME).url(UPDATED_URL).description(UPDATED_DESCRIPTION);
        RepositoryDTO repositoryDTO = repositoryMapper.toDto(updatedRepository);

        restRepositoryMockMvc.perform(put("/api/repositories").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repositoryDTO))).andExpect(status().isOk());

        // Validate the Repository in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeUpdate);
        Repository testRepository = repositoryList.get(repositoryList.size() - 1);
        assertThat(testRepository.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRepository.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testRepository.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingRepository() throws Exception {
        int databaseSizeBeforeUpdate = repositoryRepository.findAll().size();

        // Create the Repository
        RepositoryDTO repositoryDTO = repositoryMapper.toDto(repository);

        // If the entity doesn't have an ID, it will be created instead of just
        // being updated
        restRepositoryMockMvc.perform(put("/api/repositories").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(repositoryDTO))).andExpect(status().isCreated());

        // Validate the Repository in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRepository() throws Exception {
        // Initialize the database
        repositoryRepository.save(repository);
        int databaseSizeBeforeDelete = repositoryRepository.findAll().size();

        // Get the repository
        restRepositoryMockMvc
                .perform(delete("/api/repositories/{id}", repository.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Repository.class);
        Repository repository1 = new Repository();
        repository1.setId("id1");
        Repository repository2 = new Repository();
        repository2.setId(repository1.getId());
        assertThat(repository1).isEqualTo(repository2);
        repository2.setId("id2");
        assertThat(repository1).isNotEqualTo(repository2);
        repository1.setId(null);
        assertThat(repository1).isNotEqualTo(repository2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepositoryDTO.class);
        RepositoryDTO repositoryDTO1 = new RepositoryDTO();
        repositoryDTO1.setId("id1");
        RepositoryDTO repositoryDTO2 = new RepositoryDTO();
        assertThat(repositoryDTO1).isNotEqualTo(repositoryDTO2);
        repositoryDTO2.setId(repositoryDTO1.getId());
        assertThat(repositoryDTO1).isEqualTo(repositoryDTO2);
        repositoryDTO2.setId("id2");
        assertThat(repositoryDTO1).isNotEqualTo(repositoryDTO2);
        repositoryDTO1.setId(null);
        assertThat(repositoryDTO1).isNotEqualTo(repositoryDTO2);
    }
}
