Feature: Validate Login API Response

@RepaymentOptionTest
Scenario Outline: Validate Repayment options - <testcase>
    Given I submit a LoginRequest message via HTTP POST, sending <LoginRequestJSON> in the body to <LoginEndPoint>
    Then Verify Response code <LoginResponseCode> for request
    When I trigger <AccountListSummeryService> request with <AccountListEndPoint>
    Then Verify Response code <AccountListResponseCode> for request
    When I trigger <AccountDetailsService> request with <AccountDetailsEndPoint>
    Then Verify Response code <AccountDetailsResponseCode> for request
    When I trigger <RetriveHomeLoanPaymentsService> request with <RetriveHomeLoanPaymentsEndPoint>
    Then Verify Response code <RetriveHomeLoanPaymentsResponseCode> for request
    
    Examples:
    |testcase|LoginEndPoint|LoginResponseCode|LoginRequestJSON|AccountListSummeryService|AccountListEndPoint|AccountListResponseCode|AccountDetailsService|AccountDetailsEndPoint|AccountDetailsResponseCode|RetriveHomeLoanPaymentsService|RetriveHomeLoanPaymentsEndPoint|RetriveHomeLoanPaymentsResponseCode|
    |Monthly contractual frequency|init/auth/ |200|\\src\\test\\resources\\Data\\LoginRequest.json|AccountListSummery|banking/nab/accounts/summary|200|AccountDetails|banking/nab/account/|200|RetriveHomeLoanPayments|banking/nab/payments/_/1/3000/_/_/_/_/_/_/_/|200|
    