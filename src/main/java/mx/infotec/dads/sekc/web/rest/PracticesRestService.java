package mx.infotec.dads.sekc.web.rest;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest Service to CRUD Practices
 */
@RestController
@RequestMapping("/api")
public class PracticesRestService {
	
	private final Logger log = LoggerFactory.getLogger( PracticesRestService.class );
	
	/*
	 * Create Practice, Method: POST
	 */
	@PostMapping("/practice")
	public ResponseEntity createPractice( 
			@RequestBody Object practice
	){
		/************* TO DO: import the "essence-impl" project *****************/
		
		Map< String , Object > practiceMap;
		SEPractice practiceToPersiste = new SEPractice();
				
		/*
		 * Try to convert the JSON sended from the FRONT into a Map<String,Object>, get all the required fields and  
		 * do the persistence of this new Practice.
		 * Any Exception will send a BAD_REQUEST to the FRONT.
		 * If everything is correct, send a OK status to the FRONT.
		 */
		try{
			practiceMap = (Map< String , Object >) practice;
			
			practiceToPersiste.setConsistencyRules( (String) practiceMap.get( "consistencyRules" ) );
			practiceToPersiste.setEntry( (List<String>) practiceMap.get( "entry" ) );
			practiceToPersiste.setMeasures( (List<String>) practiceMap.get( "measures" ) );
			practiceToPersiste.setObjective( (String) practiceMap.get( "objective" ) );
			practiceToPersiste.setResult( (List<String>) practiceMap.get( "result" ) );
			practiceToPersiste.setBriefDescription( (String) practiceMap.get( "briefDescription" ) );
	        practiceToPersiste.setDescription( (String) practiceMap.get( "description" ) );
	        practiceToPersiste.setName( (String) practiceMap.get( "name" ) );
	        practiceToPersiste.setSupresable( (Boolean) practiceMap.get( "isSupresable" ) );
			
	        //After the implementation of the "essence-impl" project, it is a MUST to create a function 
	        //to check the Data in the object, to prevent to persiste Practices with Null values in required FIELDS
	        if( !practiceToPersiste.checkData() ){
	        	log.debug( "Some of the required FIELDS are NULL, it can't be persisted" );
	        	return new ResponseEntity( HttpStatus.BAD_REQUEST );
	        }
	        
	        //After the implementation of the "essence-impl" project, in this lines we're going to save the new Practice
	        return new ResponseEntity( practiceToPersiste , HttpStatus.OK );
	        
		}catch( Exception e ){
			log.debug( "Fail to obtain the FIELDS needed" );
			return new ResponseEntity( HttpStatus.BAD_REQUEST );
		}

	}
	
	/*
	 * Testing private class to emulate the SEPractice until the "essence-impl" project is imported 
	 */
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

		//Check if all the REQUIRED FIELDS are NOT NULL
		public boolean checkData(){
			if(
					this.consistencyRules == null
					|| this.measures == null
					|| this.entry == null
					|| this.objective == null
					|| this.result == null
					|| this.briefDescription == null
					|| this.description == null
					|| this.name == null
										 
					){
				return false;
			}
			return true;
		}
		
		//getters and setters
		public String getConsistencyRules() {
			return consistencyRules;
		}

		public void setConsistencyRules(String consistencyRules) {
			this.consistencyRules = consistencyRules;
		}

		public List<String> getEntry() {
			return entry;
		}

		public void setEntry(List<String> entry) {
			this.entry = entry;
		}

		public List<String> getMeasures() {
			return measures;
		}

		public void setMeasures(List<String> measures) {
			this.measures = measures;
		}

		public String getObjective() {
			return objective;
		}

		public void setObjective(String objective) {
			this.objective = objective;
		}

		public List<String> getResult() {
			return result;
		}

		public void setResult(List<String> result) {
			this.result = result;
		}

		public String getBriefDescription() {
			return briefDescription;
		}

		public void setBriefDescription(String briefDescription) {
			this.briefDescription = briefDescription;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isSupresable() {
			return isSupresable;
		}

		public void setSupresable(boolean isSupresable) {
			this.isSupresable = isSupresable;
		}
		
		
	}
	

}
