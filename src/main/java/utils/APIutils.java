package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;

public class APIutils {
	
	HttpResponse response;
	public static HttpURLConnection con;
	private static String CONFIG_FILE="src/main/resources/config.properties";
    public static String getresponseContent;
	public static String responseContent;
	
	public HttpURLConnection triggerRequestWithHttpConnection(String endpoint,String autherizationToken,String dynamicParam)
	{
		
	//	String Hosturl="https://customer-gw-sit05.api.extnp.national.com.au/";//eb.lookupProperty(CONFIG_FILE,hostname);
		String Hosturl="http://localhost:8282/";
		try 
		{
		String url = Hosturl+endpoint+dynamicParam;
		URL obj = new URL(url);
		con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("accept", "application/json");
	
		con.addRequestProperty("x-nab-key", "6d947aee-b2d8-4f97-947c-4c80e763ac81");
		con.setRequestProperty("x-nab-key", "6d947aee-b2d8-4f97-947c-4c80e763ac81");
		
		con.setRequestProperty("Authorization", autherizationToken);
		
		

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//responseContent=getHTTPConectionResponseContent(con);
		//System.out.println("Account Summery Response11: "+responseContent);
		
		//print result
		System.out.println("Reading Get Request response: "+response.toString());
		
		getresponseContent = response.toString();
		}
		
		catch(Exception e)
		{
			
		}
		
		return con;
	}
	
	public HttpURLConnection triggerPostRequestHttpConnection(String JSONPath, String endpoint)
	{
		HttpURLConnection httpcon = null;
		try {
			String Hosturl="http://localhost:8282/";//"https://customer-gw-sit05.api.extnp.national.com.au/";//eb.lookupProperty(CONFIG_FILE,hostname);
			// Hosturl="https://customer-gw-sit05.api.extnp.national.com.au/";
			
			String endpointurl = Hosturl+endpoint;	
			System.out.println("URL : "+endpointurl);
			System.out.println("JSONPath : "+JSONPath);
			URL url = new URL(endpointurl);
			String message =readJSONFileAndConvertToJSONObject(JSONPath);
			
		
			//CucumberBase.scenario.write("Input JSON: "+message+"  Endpoint "+endpointurl);
			//Modify JSON data before posting

			httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setRequestMethod("POST");
			
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("accept", "application/json");
			//httpcon.setRequestProperty("x-nab-key", "6d947aee-b2d8-4f97-947c-4c80e763ac81");
			httpcon.addRequestProperty("x-nab-key", "6d947aee-b2d8-4f97-947c-4c80e763ac81");
			httpcon.setRequestProperty("x-nab-key", "6d947aee-b2d8-4f97-947c-4c80e763ac81");
			
			
			OutputStreamWriter output = new OutputStreamWriter(
					httpcon.getOutputStream());
			output.write(message);
			output.flush();
			output.close();
			httpcon.connect();
			
		} catch (Throwable t) {
			t.printStackTrace();
			Assert.assertFalse( "Connection is refused", true);
		}
		return httpcon;
	
	}
	
	public static String readJSONFileAndConvertToJSONObject(String jsonFile){
		String fileContent = null;
		String filePath = System.getProperty("user.dir")+jsonFile;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
			String responseString;
			while ((responseString = br.readLine()) != null) {
				sb.append(responseString);
			}
			fileContent = sb.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return fileContent;
	}
	
	public static  String getHTTPConectionResponseContent(HttpURLConnection httpcon) {
		String serverResponseContent = null;
		try {
			String serverResponseCodeDescription = httpcon.getResponseMessage();
			int serverResponseCode = httpcon.getResponseCode();

			if (serverResponseCode == 200 || serverResponseCode == 202) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(httpcon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String responseString;
				while ((responseString = br.readLine()) != null) {
					sb.append(responseString);
				}
				serverResponseContent = sb.toString();
				System.out.println("Account Summery Response if : "+serverResponseContent);
				return serverResponseContent;
			} else {
				System.out.println("Account Summery Response else: "+serverResponseCodeDescription);
				return serverResponseCodeDescription;
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return null;

		}
	}
	
	
	
	

	
}
