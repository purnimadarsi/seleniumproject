package basics;
//QA Box Lets test
import static org.testng.Assert.assertEquals;


import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;

public class GetBasics {
	@BeforeMethod
	public void setUp() {
		
		RestAssured.baseURI = "http://localhost:5002";
		RestAssured.basePath = "/api/members";

		
	}
	@Test(enabled=false)
	public void getNumber() {
		//arrange
		
	//	RestAssured.baseURI = "http://localhost:5002";
	//	RestAssured.basePath = "/api/members";
		
		RestAssured.authentication = RestAssured.basic("admin", "admin");
		RequestSpecification httpRequest = RestAssured.given();
		Header header = new Header("Accept", "application/json");
		
		httpRequest.header(header);
		
		//act
		Response response = httpRequest.request(Method.GET);
		response.then().body("size()",equalTo(4)).body("id", contains(1,2,3,4));
		
		//assert
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
		
		Headers headers = response.headers();
		for(Header hd: headers) {
			System.out.println("name: " + hd.getName() + " value: " +  hd.getValue());
		}
		
		System.out.println(response.asPrettyString());
		
	}


@Test(enabled=false)
public void getFemaleMember() {
	//RestAssured.baseURI = "http://localhost:5002";
	Map<String,String> params = new HashMap<>();
	params.put("gender", "Female");
	
	
	Response res = RestAssured.given()
			                  .header("Accept","application/json")
			                  .auth()
			                  .basic("admin", "admin")
			                  .queryParams(params)
			                  .when()
			                  .get()
			                  .then()
			                  .extract()
			                  .response();
	
	System.out.println(res.asPrettyString());
	System.out.println(res.jsonPath().getString("[0].name"));
	
}
@Test
public void getSpecificMember() {
	//RestAssured.baseURI = "http://localhost:5002";
	
	RestAssured.basePath += "/{id}";
	
	Response res = RestAssured.given()
			                  .header("Accept","application/json")
			                  .auth()
			                  .basic("admin", "admin")
			                  .pathParam("id", 3)
			                  .log()
			                  .all()
			                  .when()
			                  .get()
			                  .then()
			                  .statusCode(200)
			                  .body(containsString("Male"))
			                  .extract()
			                  .response();
	
	System.out.println(res.asPrettyString());
	System.out.println(res.jsonPath().getString("name"));
	
}

}

/*
 * import io.restassured.RestAssured; import org.testng.annotations.Test;
 * 
 * import static io.restassured.RestAssured.*; import static
 * org.hamcrest.Matchers.*;
 * 
 * public class JSONValidationTest {
 * 
 * @Test public void validateResponse() { RestAssured.baseURI =
 * "http://localhost:5002";
 * 
 * given() .header("Accept", "application/json") .when() .get("/api/members")
 * .then() .statusCode(200) .contentType("application/json") .body("size()",
 * equalTo(4)) .body("id", contains(1, 2, 3, 4)) .body("gender",
 * everyItem(anyOf(equalTo("Male"), equalTo("Female")))) .body("name",
 * hasItems("Monil", "Ramona", "Lion", "Shawn"))
 * .body("find { it.name == 'Shawn' }.id", equalTo(4)) .time(lessThan(2000L)); }
 * }
 * response.then()
    .body("id", containsInAnyOrder(4, 3, 2, 1));  // Order doesn't matter

Response response = httpRequest.request(Method.GET);
List<Object> members = response.jsonPath().getList("$"); // Extracts the entire JSON array
assert members.size() == 4;

 */