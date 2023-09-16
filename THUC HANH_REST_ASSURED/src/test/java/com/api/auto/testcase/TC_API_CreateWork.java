package test.java.com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import main.java.com.api.auto.utils.PropertiesFileUtils;

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;

	private String myWork = "tester";
	private String myExperience = "1";
	private String myEducation = "funix";

	@BeforeClass
	public void init() {
		// Init data
		String baseUrl = PropertiesFileUtils.getProperty("baseurl");
		String createWorkPath = PropertiesFileUtils.getProperty("createWorkPath");
		String token = PropertiesFileUtils.getToken("token");

		RestAssured.baseURI = baseUrl;

		// make body
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("nameWork", myWork);
		body.put("experience", myExperience);
		body.put("education", myEducation);

		RequestSpecification request = RestAssured.given().contentType(ContentType.JSON).header("token", token)
				.body(body);
		response = request.post(createWorkPath);
		responseBody = response.getBody();
		jsonBody = responseBody.jsonPath();

		System.out.println(" " + responseBody.asPrettyString());
	}

	@Test(priority = 0)
	public void TC01_Validate201Created() {
		assertEquals(201, response.getStatusCode(), "Status Check Failed!");
	}

	@Test(priority = 1)
	public void TC02_idChecked() {
		assertTrue(responseBody.asString().contains("id"), " id field Check Failed!");
	}

	@Test(priority = 2)
	public void TC03_nameWorkMatched() {
		assertTrue(responseBody.asString().contains("nameWork"), " nameWork field Check Failed!");
		String work = jsonBody.getString("nameWork");
		assertEquals(myWork, work, "work does not match");
	}

	@Test(priority = 3)
	public void TC04_experienceMatched() {
		assertTrue(responseBody.asString().contains("experience"), " experience field Check Failed!");
		String exper = jsonBody.getString("experience");
		assertEquals(myExperience, exper, "experience does not match");
	}

	@Test(priority = 4)
	public void TC05_educationMatched() {
		assertTrue(responseBody.asString().contains("education"), " education field Check Failed!");
		String edu = jsonBody.getString("education");
		assertEquals(myEducation, edu, "education does not match");
	}
}
