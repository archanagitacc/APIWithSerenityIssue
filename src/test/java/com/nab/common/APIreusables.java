package com.nab.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import utils.APIutils;

public class APIreusables extends APIutils {

	APIutils api=new APIutils();
	HttpResponse response;
	//HttpURLConnection con;
	//String responseContent;
	String AutherizationToken;
	String AccountToken;
	int statusCode;
	String dynamicParam;
	String accountToken;
	String PaymentToken;
	 
	public void triggerGetrequest(String serviceName,String endpoint)
	{
		try
		{
		if(serviceName.equalsIgnoreCase("AccountListSummery"))
		{
		dynamicParam="";	
		con=api.triggerRequestWithHttpConnection(endpoint,AutherizationToken,dynamicParam);
		statusCode=con.getResponseCode();
		//responseContent = api.getHTTPConectionResponseContent(con);
		 
	//	System.out.println("Account Summery Response code: "+statusCode);
		//System.out.println("Account Summery Response new: "+getresponseContent);
		accountToken=getAccountToken();
		//accountToken="QdsxgsZCi6g3bZH1LLax-I2ZakhAJH_FQ3-iNxatbtAGtP99OV7kXgWwq6PQXI9H";
		}
		else if(serviceName.equalsIgnoreCase("AccountDetails")){	
			con=api.triggerRequestWithHttpConnection(endpoint,AutherizationToken,accountToken);
			statusCode=con.getResponseCode();
		//	System.out.println("Account Details Response code: "+statusCode);
		//	System.out.println("Account Details Response new: "+getresponseContent);
		//	responseContent = api.getHTTPConectionResponseContent(con);
		}
		else if(serviceName.equalsIgnoreCase("RetriveHomeLoanPayments")){	
			System.out.println("**********RetriveHomeLoanPayments");
			con=api.triggerRequestWithHttpConnection(endpoint,AutherizationToken,accountToken);
	//		responseContent = api.getHTTPConectionResponseContent(con);
		//	System.out.flush();
		//	System.out.println("RetriveHomeLoanPayments Response code: "+statusCode);
			System.out.println("RetriveHomeLoanPayments Response new: "+getresponseContent);
			statusCode=con.getResponseCode();
			PaymentToken=getPaymentToken();
		}
		else if(serviceName.equalsIgnoreCase("RetrivePaymentDetails")){	
			con=api.triggerRequestWithHttpConnection(endpoint,AutherizationToken,PaymentToken);
			statusCode=con.getResponseCode();
			System.out.println("RetrivePaymentDetails Response code: "+statusCode);
			System.out.println("RetrivePaymentDetails Response new: "+getresponseContent);
		}
		else if(serviceName.equalsIgnoreCase("RetriveRepaymentOptions")){	
			con=api.triggerRequestWithHttpConnection(endpoint,AutherizationToken,accountToken);
			statusCode=con.getResponseCode();
			System.out.println("RetriveRepaymentOptions Response code: "+statusCode);
			System.out.println("RetriveRepaymentOptions Response new: "+getresponseContent);
		}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void triggerPostrequest(String jsonFile, String endpoint)
	{
		con=api.triggerPostRequestHttpConnection(jsonFile,endpoint);
		try {
			statusCode=con.getResponseCode();
			System.out.println("response code :"+statusCode);
			getAuthorizationToken();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAuthorizationToken()
	{
		responseContent = api.getHTTPConectionResponseContent(con);
        System.out.println("API Response : " + responseContent);
        System.out.println("API CorrelationID:"+con.getHeaderField("X-CorrelationID"));

        JSONObject myResponse;
		try {
			myResponse = new JSONObject(responseContent);
		
	      AutherizationToken = myResponse.getJSONObject("loginResponse").getJSONArray("tokens").getJSONObject(0).getString("value");
	     
	     
	     System.out.println("AuthrisationToken"+AutherizationToken);
	     
	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return AutherizationToken;
	}
	
	public String getPaymentToken()
	{
		
        System.out.println("API Response : " + getresponseContent);
        System.out.println("API CorrelationID:"+con.getHeaderField("X-CorrelationID"));

        JSONObject myResponse;
		try {
			myResponse = new JSONObject(getresponseContent);
		
			
	      int records = myResponse.getJSONObject("paymentsResponse").getJSONArray("payments").length();
	      for(int i=0; i< records; i++)
	      {
	    	  PaymentToken = myResponse.getJSONObject("paymentsResponse").getJSONArray("payments").getJSONObject(i).getString("paymentToken");
	 	     
	    		//  AccountToken = myResponse.getJSONObject("accountsResponse").getJSONArray("accounts").getJSONObject(i).getString("accountToken");
	    	  
	      
	      }
	     System.out.println("paymentTokenToken"+PaymentToken);
	     
	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return PaymentToken;
	}
	
	public String getAccountToken()
	{
		System.out.println("Inside get account Token: "+getresponseContent);
        
        JSONObject myResponse;
        
		try {
			myResponse = new JSONObject(getresponseContent);
		
	      int records = myResponse.getJSONObject("accountsResponse").getJSONArray("accounts").length();
	      for(int i=0; i< records; i++)
	      {
	    	  
	    	String actual_HL_accountIdDisplay =   myResponse.getJSONObject("accountsResponse").getJSONArray("accounts").getJSONObject(i).getString("accountIdDisplay");
	    	System.out.println("actual_HL_accountIdDisplay: "+actual_HL_accountIdDisplay.trim());
	    	String expected_HL_accountIdDisplay = "083047-880169858";
	    	 
	    	System.out.println("expected_HL_accountIdDisplay: "+expected_HL_accountIdDisplay.trim());
	    	  if(actual_HL_accountIdDisplay.equals(expected_HL_accountIdDisplay)) {
	    		  
	    		  System.out.println("account found");
	    		  AccountToken = myResponse.getJSONObject("accountsResponse").getJSONArray("accounts").getJSONObject(i).getString("accountToken");
	    	  }
	    	  else {
	    		  System.out.println("Account Not found");
	    	  }
	      
	      }
	     System.out.println("AccountToken: "+AccountToken);
	//     http://localhost:8282/banking/nab/account/QdsxgsZCi6g3bZH1LLax-I2ZakhAJH_FQ3-iNxatbtAGtP99OV7kXgWwq6PQXI9H
	//     http://localhost:8282/banking/nab/account/nG5wkxfjFO2fH1-xDy4ijuudOwKqLxmM7M_EkpvTHfb1NfdQVh_xL-e8mrXf9Q15	 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return AccountToken;
	}
	
	public void validateResponseCode(int responseCode)
	{
		if (statusCode == responseCode) {
           
              System.out.println("Actual response code matches with expected response code. Response code value: "+statusCode);
              
          } 
		else 
		{
              Assert.assertFalse("Actual response code not matches with expected response code" + statusCode, true);
              
         }

	}
	
	public void validateResponse(String responseFile)
	{
		try 
		{
		
		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println("Response message: "+result);
		
		//JSONAssert.assertEquals(
			//	  "{id:123,name:\"John\"}", result, JSONCompareMode.LENIENT);
		}
		catch(Exception e)
		{
			
		}
	}
	
	
}

