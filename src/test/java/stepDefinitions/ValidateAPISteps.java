package stepDefinitions;

import com.nab.common.APIreusables;


import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ValidateAPISteps{

	APIreusables apiReusables = new APIreusables();
	
	@When("^I trigger (.*?) request with (.*?)$")
	public void triggerRequest(String serviceName,String endpoint) throws Throwable {
		
		apiReusables.triggerGetrequest(serviceName,endpoint);
		
	}

	@Then("^Verify Response code (.*?) for request$")
	public void validateresponseCode(int responseCode) throws Throwable {
	
		apiReusables.validateResponseCode(responseCode);	
	}
	
	@Then("^Validate response message (.*?) with expected value$")
	public void validateresponse(String responsefile) throws Throwable {
	
		apiReusables.validateResponse(responsefile);	
	}

	@Given("^I submit a LoginRequest message via HTTP POST, sending (.*?) in the body to (.*?)$")
	public void makeHTTPPostRequest(String jsonFile, String endPoint) {
       
		apiReusables.triggerPostrequest(jsonFile,endPoint);
    }

}
