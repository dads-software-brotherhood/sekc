package mx.infotec.dads.sekc.admin.practice;

import java.util.ArrayList;
import java.util.Arrays;
import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SELevelOfDetail;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEWorkProduct;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.competency.SECompetencyLevel;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.sekc.SekcApp;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.sekc.admin.kernel.rest.util.ResponseWrapper;
import mx.infotec.dads.sekc.admin.practice.dto.Action;
import mx.infotec.dads.sekc.admin.practice.dto.Activity;
import mx.infotec.dads.sekc.admin.practice.dto.AlphaState;
import mx.infotec.dads.sekc.admin.practice.dto.Approach;
import mx.infotec.dads.sekc.admin.practice.dto.Competency;
import mx.infotec.dads.sekc.admin.practice.dto.CompletitionCriterion;
import mx.infotec.dads.sekc.admin.practice.dto.Conditions;
import mx.infotec.dads.sekc.admin.practice.dto.Criteriable;
import mx.infotec.dads.sekc.admin.practice.dto.Entries;
import mx.infotec.dads.sekc.admin.practice.dto.EntryCriterion;
import mx.infotec.dads.sekc.admin.practice.dto.Resource;
import mx.infotec.dads.sekc.admin.practice.dto.Results;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToDo;
import mx.infotec.dads.sekc.admin.practice.dto.ThingsToWorkWith;
import mx.infotec.dads.sekc.admin.practice.dto.WorkProductsLevelofDetail;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import mx.infotec.dads.sekc.admin.practice.service.impl.PracticeServiceImpl;
import org.springframework.http.HttpStatus;
/**
 *
 * @author wisog
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SekcApp.class)
public class CreatePracticeFromDtoTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private PracticeService practiceService;
    
    PracticeDto practiceDto;

    @Before
    public void buildDto() {
        practiceDto = new PracticeDto();
        generateGeneralInfo(practiceDto);
        generateRelatesPractices(practiceDto);
        generateConditions(practiceDto);
        generateThingsToWorkWith(practiceDto);
        generateThingsToDo(practiceDto);
    }
    public void generateGeneralInfo(PracticeDto dto){
        //mapGeneralInfo(from, practice);
        SEKernel seKernel = new SEKernel();
        EssenceMapping.fillSEElementGroup(seKernel);
        seKernel.setName("K3rnel");
        mongoTemplate.save(seKernel);
        dto.setIdKernel(seKernel.getId());
        dto.setName("Práctica de Arquitectura");
        dto.setObjective("Objetivo");
        dto.setBriefDesciption("Breve descripción");
        dto.setDescription("Descripción detallada, se puede utilizar texto rico");
        dto.setConsistencyRules("Reglas de consistencia de la práctica");
        dto.setAuthor("Autor");
        dto.setKeywords(Arrays.asList("keyword1", "keyword2", "keyword3" ));
        
    }
    public void generateRelatesPractices(PracticeDto dto){
        SEPractice sePractice1 = new SEPractice();
        EssenceMapping.fillPractice(sePractice1);
        sePractice1.setDescription("relatedPractice1");
        mongoTemplate.save(sePractice1);
        SEPractice sePractice2 = new SEPractice();
        EssenceMapping.fillPractice(sePractice2);
        sePractice2.setDescription("relatedPractice2");
        mongoTemplate.save(sePractice2);
        dto.setRelatedPractices(Arrays.asList(sePractice1.getId(), sePractice2.getId()));
    }
    public void generateCriterableContent(Criteriable crit){
        //mapConditions(from.getConditions(), practice);
        crit.setAlphaStates(Arrays.asList(generateAlphaState()));
        crit.setWorkProductsLevelofDetail(Arrays.asList(generateWorkProductsLevelofDetail()));
        crit.setOtherConditions(Arrays.asList("Condicion1"));
    }
    public Criteriable generateEntryCondition(){
        Entries entries = new Entries();
        generateCriterableContent(entries);
        return entries;
    }
    public Criteriable generateCompletionCondition(){
        Results results = new Results();
        generateCriterableContent(results);
        return results;
    }
    public Criteriable generateEntryCriterion(){
        EntryCriterion entries = new EntryCriterion();
        generateCriterableContent(entries);
        return entries;
    }
    public Criteriable generateCompletitionCriterion(){
        CompletitionCriterion results = new CompletitionCriterion();
        generateCriterableContent(results);
        return results;
    }
    public void generateConditions(PracticeDto dto){
        Conditions cond = new Conditions();
        cond.setEntries((Entries) generateEntryCondition());
        cond.setResults((Results) generateCompletionCondition());
        cond.setMeasures(Arrays.asList("measure1", "measure2"));
        dto.setConditions(cond);
    }
    public AlphaState generateAlphaState(){
        SEAlpha seAlpha = new SEAlpha();
        EssenceMapping.fillBasicElement(seAlpha);
        mongoTemplate.save(seAlpha);
        SEState seState = new SEState();
        EssenceMapping.fillSELanguageElements(seState);
        mongoTemplate.save(seState);
        AlphaState alphaState = new AlphaState();
        alphaState.setIdAlpha(seAlpha.getId());
        alphaState.setIdState(seState.getId());
        return alphaState;
    }
    public WorkProductsLevelofDetail generateWorkProductsLevelofDetail(){
        SEWorkProduct seWorkProduct = new SEWorkProduct();
        EssenceMapping.fillBasicElement(seWorkProduct);
        mongoTemplate.save(seWorkProduct);
        SELevelOfDetail seLevelOfDetail = new SELevelOfDetail();
        EssenceMapping.fillSELanguageElements(seLevelOfDetail);
        mongoTemplate.save(seLevelOfDetail);
        WorkProductsLevelofDetail workProductsLevelofDetail = new WorkProductsLevelofDetail();
        workProductsLevelofDetail.setIdWorkProduct(seWorkProduct.getId());
        workProductsLevelofDetail.setIdLevelOfDetail(seLevelOfDetail.getId());
        return workProductsLevelofDetail;
    }
    public void generateThingsToWorkWith(PracticeDto dto){
        //mapThingsToWorkWith(from.getThingsToWorkWith(), practice);
        SEAlpha seAlpha1 = new SEAlpha();
        SEAlpha seAlpha2 = new SEAlpha();
        mongoTemplate.save(seAlpha1);
        mongoTemplate.save(seAlpha2);
        ThingsToWorkWith tToWork = new ThingsToWorkWith();
        tToWork.setAlphas(Arrays.asList(seAlpha1.getId(), seAlpha2.getId()));
        SEWorkProduct wp1 = new SEWorkProduct();
        SEWorkProduct wp2 = new SEWorkProduct();
        mongoTemplate.save(wp1);
        mongoTemplate.save(wp2);
        tToWork.setWorkProducts(Arrays.asList(wp1.getId(), wp2.getId()));
        dto.setThingsToWorkWith(tToWork);
    }
    public void generateThingsToDo(PracticeDto dto){
        //mapThingsToDo
        ThingsToDo tToDo = new ThingsToDo();
        SEActivitySpace actSpace = new SEActivitySpace();
        EssenceMapping.fillBasicElement(actSpace);
        mongoTemplate.save(actSpace);
        Activity activity = new Activity();
        activity.setIdActivitySpace(actSpace.getId());
        activity.setName("activity1");
        activity.setBriefDesciption("breve descripcion");
        activity.setDescription("descripcion");
        SECompetency competency = new SECompetency();
        EssenceMapping.fillBasicElement(competency);
        SECompetencyLevel competencyLevel = new SECompetencyLevel();
        EssenceMapping.fillSELanguageElements(competencyLevel);
        mongoTemplate.save(competency);
        mongoTemplate.save(competencyLevel);
        Competency comp = new Competency();
        comp.setIdCompetency(competency.getId());
        comp.setIdCompetencyLevel(competencyLevel.getId());
        activity.setCompetencies(Arrays.asList(comp));
        Approach ap1 = new Approach();
        ap1.setName("approach1");
        ap1.setDescription("desc Approach");
        activity.setApproaches(Arrays.asList(ap1));
        Action action = new Action();
        action.setIdActionKind("READ");
        action.setAlphaStates(Arrays.asList(generateAlphaState()));
        action.setWorkProductsLevelofDetail(Arrays.asList(generateWorkProductsLevelofDetail()));
        activity.setActions(Arrays.asList(action));
        activity.setEntryCriterion((EntryCriterion) generateEntryCriterion());
        activity.setCompletitionCriterion((CompletitionCriterion) generateCompletitionCriterion());
        Resource res = new Resource();
        res.setContent("<p>texto rico <strong>description</strong></p>");
        res.setFile("dmFyIGFwcCA9IGFuZ3VsYXIubW9kdWxlKCdwbHVua2VyJywgW10pOwo");
        res.setIdTypeResource("IMAGE");
        activity.setResources(Arrays.asList(res));
        tToDo.setActivities(Arrays.asList(activity));
        dto.setThingsToDo(tToDo);
    }
            
    @Test
    public void practiceDTOSaveTest() throws Exception {
        ResponseWrapper responseData;
        responseData = practiceService.save(practiceDto);
        assertThat(responseData.getResponse_code()).isEqualTo(HttpStatus.OK);
        assertThat( ((SEPractice) responseData.getResponseObject()).getId() ).hasSize(24);
    }
    @Test
    public void practiceDTOWithoutAlphaStatesOnEntryTest() throws Exception {
        ResponseWrapper responseData;
        practiceDto.getConditions().getEntries().setAlphaStates(new ArrayList<>());
        responseData = practiceService.save(practiceDto);
        assertThat(responseData.getResponse_code()).isEqualTo(HttpStatus.OK);
        assertThat( ((SEPractice) responseData.getResponseObject()).getId() ).hasSize(24);
    }
    @Test
    public void practiceDTOWithfakeIdKernel() throws Exception {
        ResponseWrapper responseData;
        practiceDto.setIdKernel("noExiste");
        responseData = practiceService.save(practiceDto);
        assertThat(responseData.getResponse_code()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseData.getError_message()).isEqualToIgnoringCase("The id noExiste doesn't exist on SEKernel");
    }
    
}
