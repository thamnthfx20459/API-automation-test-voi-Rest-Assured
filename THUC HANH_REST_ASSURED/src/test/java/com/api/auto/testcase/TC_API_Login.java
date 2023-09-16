package test.java.com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
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

public class TC_API_Login {
	// khai báo
	private String account;
	private String password;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;

	@BeforeClass
	public void init() {

		// init data
		account = PropertiesFileUtils.getProperty("account");
		password = PropertiesFileUtils.getProperty("password");
		String baseurl = PropertiesFileUtils.getProperty("baseurl");
		String loginPath = PropertiesFileUtils.getProperty("loginPath");
		RestAssured.baseURI = baseurl;
		// make body
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("account", account);
		body.put("password", password);
		RequestSpecification request = RestAssured.given().contentType(ContentType.JSON).body(body);
		response = request.post(loginPath);
		responseBody = response.getBody();
		jsonBody = responseBody.jsonPath();

		System.out.println(" " + responseBody.asPrettyString());
	}

	@Test(priority = 0)
	public void TC01_Validate200Ok() {
		assertEquals(200, response.getStatusCode(), "Status Check Failed!");
	}

	@Test(priority = 1)
	public void TC02_ValidateMessage() {
		// kiểm chứng response body có chứa trường message hay không
		assertTrue(responseBody.asString().contains("message"), "message field check Failed!");
		// kiểm chứng trường message có = "Đăng nhập thành công
		assertTrue(responseBody.asString().contains("Đăng nhập thành công"), "không hiển thị nội dung");
	}

	@Test(priority = 2)
	public void TC03_ValidateToken() throws IOException {
		// kiểm chứng response body có chứa trường token hay không
		String verifyToken = jsonBody.getString("token");
		assertTrue(responseBody.asString().contains("token"), "response body not contain token field!");
		// lưu lại token
		PropertiesFileUtils.saveToken(verifyToken);

	}

	@Test(priority = 3)
	public void TC05_ValidateUserType() {
		// kiểm chứng response body có chứa thông tin user và trường type hay không
		assertTrue(responseBody.asString().contains("user") && responseBody.asString().contains("type"),
				"Không có trường 'user'/'type'!");
		// kiểm chứng trường type có phải là “UNGVIEN”
		String userType = jsonBody.getString("user.type");
		assertEquals("UNGVIEN", userType, "sai user");
	}

	@Test(priority = 4)
	public void TC06_ValidateAccount() {
		// kiểm chứng response chứa thông tin user và trường account hay không
		String userAccount = jsonBody.getString("user.account");
		assertNotNull(userAccount, "cant find 'account' in user!");
		// Kiểm chứng trường account có khớp với account đăng nhập
		assertEquals(account, userAccount);

		// kiểm chứng response chứa thông tin user và trường password hay không
		String userPassword = jsonBody.getString("user.password");
		assertNotNull(userPassword, "cant find 'password' in user!");
		// Kiểm chứng trường password có khớp với password đăng nhập
		assertEquals(password, userPassword);
	}
}
