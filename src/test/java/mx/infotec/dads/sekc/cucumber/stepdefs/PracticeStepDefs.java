package mx.infotec.dads.sekc.cucumber.stepdefs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import mx.infotec.dads.sekc.admin.practice.service.PracticeService;
import static mx.infotec.dads.sekc.web.rest.util.ApiConstant.API_PATH;
/*
 * @author: alejandro aguayo
 * 
 * TO DO: 
 * 	Update Step Def once the "essence-impl" is imported
 * 	Delete the SEPractice inner class
 * 
 */

public class PracticeStepDefs extends StepDefs {
	
	@Autowired
    private PracticeService practicesService;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(practicesService).build();
    }

    @When("^I send a request whit all the Fields except '(.*)'$")
    public void i_send_a_request_with_one_missing_field( String field ) throws Throwable {
    	
    	SEPractice practiceObject = this.createPracticeObject();
    	
    	practiceObject = this.setNullIn( practiceObject , field );
    	
    	Gson gson = new Gson();
        String json = gson.toJson( practiceObject );

    	
        actions = restUserMockMvc.perform( post( API_PATH + "/practice" )
        		.contentType( MediaType.APPLICATION_JSON).content( json ) );
    }
    
    @When("^I send a GET request whit the id param '(.*)'$")
    public void i_send_a_GET_request( String id ) throws Throwable {
    	actions = restUserMockMvc.perform( get( API_PATH + "/practice" )
        		.param( "id" , id )
        		.accept(MediaType.APPLICATION_JSON));  	
    }
    
    @When("^I send a POST request whit all the Fields$")
    public void i_send_a_POST_request_with_all_the_field( ) throws Throwable {
    	
    	SEPractice jsonClass = this.createPracticeObject();

    	Gson gson = new Gson();
        String json = gson.toJson( jsonClass );
        
    	actions = restUserMockMvc.perform( post( API_PATH + "/practice" )
    			.contentType( MediaType.APPLICATION_JSON).content( json ) );
    }

    @Then("^I receive a error BAD_REQUEST$")
    public void receive_bad_request() throws Throwable {
        actions
            .andExpect( status().isBadRequest() );
            //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
    
    @Then("^the atribute '(.*)' have this value '(.*)'$")
    public void the_atribute_is( String atribute , String value ) throws Throwable {
    	actions
    		.andExpect( jsonPath( "$." + atribute ).value( value ) );
    }
    
    @Then("^I receive a OK$")
    public void receive_ok() throws Throwable {
        actions
            .andExpect( status().isOk() )
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
    
    /*
     * Method that set a NULL in the FIELD "atribute" of the Object "practice" 
     */
    public SEPractice setNullIn( SEPractice practice , String atribute ){

    	try{
    		String name;
    		Field[] fields = practice.getClass().getDeclaredFields();
    		for(Field field : fields) {
    			name = field.getName();
    			if( name.equals( atribute ) ){
    				field.setAccessible( true );
    				field.set(practice, null);
    			}
    		}
		}catch( Exception e ){ }
    	return practice;
    }
    
    public SEPractice createPracticeObject(){
    	SEPractice practice = new SEPractice();

    	// Practice
    	practice.consistencyRules = "Consistency Rules";
    	practice.entry = Arrays.asList("Requeriments:Alpha", "Software:Architecture");
    	practice.measures = Arrays.asList("Timing", "five minutes pear meeting");
    	practice.objective = "The Objetive of the practice";
    	practice.result = (Arrays.asList("Requeriments:Alpha", "Software:Architecture"));
        // ElementGroup
    	practice.briefDescription = ("Practice Brief Descrition");
    	practice.description = ("Practice Description");
    	practice.name = ("Name of the Practice");
        //LenguajeElement
    	practice.isSupresable = false;
    	
        return practice;
    }
    
    
    class SEPractice{
		/**** FIELDS required ****/
		// Practice
		String consistencyRules;
		List<String> entry;
		List<String> measures;
		String objective;
		List<String> result;
		        
		// ElementGroup
		String briefDescription;
		String description;
		//practice.setIcon(new SEGraphicalElement());
		//Stringpractice.setMergeResolution(Arrays.asList(new SEMergeResolution()));
		String name;
		//practice.setOwnedElements(ownedElements);

		//LenguajeElement
		Boolean isSupresable;
    }
}
