package basics;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PostBasics {
	RequestSpecification httpRequest;
	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI = "http://localhost:5002";
		RestAssured.basePath = "/api/members";
		Header acceptHeader = new Header("Accept","application/json");
		Header contentTypeHeader = new Header("Content-Type","application/json");
		List<Header> headers = new ArrayList();
		headers.add(acceptHeader);
		headers.add(contentTypeHeader);
		
		Headers allHeaders = new Headers(headers);
		httpRequest = RestAssured.given().auth().basic("admin", "admin").headers(allHeaders);
		
		
	}
	
	@Test(enabled=false)
	public void CreateMember() {
		String body = "{\r\n"
				+ "    \"name\":\"Hari\",\r\n"
				+ "    \"gender\":\"male\"\r\n"
				+ "}";
		Response response = httpRequest.body(body).when().post();
		System.out.println(response.asPrettyString());
	}
	
	@Test(enabled=false)
	public void createMemberUsingMapBDD() {
		Map<String,String> body = new HashMap<>();
		body.put("name", "sam");
		body.put("gender", "female");
		Response res = httpRequest.body(body).when().post().andReturn();
		System.out.println(res.asPrettyString());
		
		
	}
	@Test(enabled=false)
	public void createMemberUsingJSONObject() {
		JsonObject body = new JsonObject();
		body.addProperty("name", "siddu");
		body.addProperty("gender", "male");
		Response res = httpRequest.body(body.toString()).when().post().andReturn();
		System.out.println(res.asPrettyString());
		
		
	}
	@Test(enabled=false)
	public void createMemberUsingJSONFile() {
		File body = new File(System.getProperty("user.dir")+"/src/test/resources/payloads/inputMember.json");
		Response res = httpRequest.body(body).when().post().andReturn();
		System.out.println(res.asPrettyString());
		
		
	}
	@Test(enabled=false)
	public void createMemberUsingJSONFileStream() {
	InputStream body = 	getClass().getClassLoader().getResourceAsStream("payloads/inputMember.json");
		Response res = httpRequest.body(body).when().post().andReturn();
		System.out.println(res.asPrettyString());
		
		
	}
	@Test(enabled=false)
	public void createMemberUsingJSONFileByteArray() throws IOException {
	byte[] body = 	Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/src/test/resources/payloads/inputMember.json"));
		Response res = httpRequest.body(body).when().post().andReturn();
		System.out.println(res.asPrettyString());
		
		
	}
	@Test(enabled=false)
	public void createMembersUsingModelNonTransientBDD() {
		
		Members body = new Members( "steve","Male");
		
		Response res = httpRequest.body(body).when().post().then().assertThat().body("msg",equalTo("Please provide only name and gender"))
				.extract().response();
		System.out.print(res.asPrettyString());
		
		
	}
	@Test(enabled=false)
	public void createMembersUsingModelTransientBDD() {
		
		Members body = new Members( "Rose","female");
		System.out.println(body.toString());
		
		Response res = httpRequest.body(body).log().all().post().andReturn();
		System.out.print(res.asPrettyString());
		
		
	}
	@Test(enabled=false)

	public void createMembersUsingModelGsonExposeBDD() {
		
		Members member = new Members( "Malathy","female");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String body = gson.toJson(member);
		System.out.println(body.toString());
		
		Response res = httpRequest.body(body).log().all().post().andReturn();
		System.out.print(res.asPrettyString());
		
		
	}
	@Test
	public void uploadFile() {
		RestAssured.basePath = "api/upload";
		RestAssured.baseURI = "http://localhost:5002";
		Header acceptHeader = new Header("Accept","application/json");
		RequestSpecification httpFileRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader);

       

		File file = new File(System.getProperty("user.dir") + "\\src\\test\\java\\uploads\\cat.jpg");
		if (file.exists()) {
		    System.out.println("File found: " + file.getAbsolutePath());
		} else {
		    System.out.println("File not found!");
		}


		Response response = httpFileRequest.multiPart("file",file).formParam("name","QA Box Lets test").when().post().andReturn();
		System.out.print(response.asPrettyString());

	}


}

//div[@class='lnnVSe']//div[contains(@class,'wM6W7d') and @role='presentation']
//By.cssSelector("ul[jsname='bw4e9b'][role='listbox']"
