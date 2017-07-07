Feature: Practice management

    Scenario: Try to create a new Practice and fail
        When I send a request whit all the Fields except 'name'
        Then I receive a error BAD_REQUEST
        
    Scenario: Try to create a new Practice and success
        When I send a POST request whit all the Fields
        Then I receive a OK
        And the atribute 'name' have this value 'Name of the Practice'
        
	Scenario: Getting a OK and the Data I send from a GET Request
        When I send a GET request whit the id param 'my id'
        Then I receive a OK
        And the atribute 'consistencyRules' have this value 'my id'