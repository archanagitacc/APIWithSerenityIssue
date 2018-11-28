Feature: Validate API Response

@WireMockTest
Scenario Outline: Notification validation - <testName> - <testcase>
    Given I have a request for <Hostname> service with <endPoint>
    Then Verify Response code <responseCode> for request
    Then Validate response message <expectedResponse> with expected value 

    Examples:
    |testcase |testName |Hostname | endPoint | responseCode|expectedResponse|
    |Valdate response for NAB account|Home Loan account|devUrl|product/p0001|200|\\src\\test\\resources\\testdata\\homeloan\\Request_BadRequest.json|
		
#Scenario Outline: Jeddis validate response code - <workflowName> - <testcase>
#Given I submit a DiagnosticRequest message via HTTP POST, sending <requestJSON> in the body to <endPoint>
#Then Verify Post Response <responseCode>
#Examples:
#      |testcase|workflowName| requestJSON                                                                                          | endPoint         | responseCode |
#      |negative HTTP Status codes returned in the response|DPU status workflow| \\src\\test\\resources\\testdata\\dpustatus\\postDpuStatusTest\\04_diagnosticRequest_BadRequest.json | v1/dpuStatusTest | 400          |
    