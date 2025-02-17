package uploads.models;


	import java.lang.reflect.Member;
	import java.util.List;

	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;

	import io.restassured.RestAssured;
	import io.restassured.common.mapper.TypeRef;
	import io.restassured.http.Header;
	import io.restassured.response.Response;
	import io.restassured.specification.RequestSpecification;

	public class DeseralizeComplexJsonResponse {
		RequestSpecification httpRequest;
		Header acceptHeader;
		
		@BeforeMethod
		public void setUp() {
			RestAssured.basePath = "/api/authors";
			RestAssured.baseURI = "http://localhost:5002 ";
			acceptHeader = new Header("Accept","application/json");
			
		}
		
		@Test(enabled = true)
		public void deseralizeSingleMember() {
			RestAssured.basePath += "{id}";
			httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader).pathParam("id",2);
			Response response = httpRequest.when().get().andReturn();
			Author author = response.getBody().as(Author.class);
			System.out.println(author.toString());
			
			
		}
		
		@Test(enabled = true)
		public void deseralizeSingleMemberUsingTypeRef() {
			RestAssured.basePath += "{id}";
			httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader).pathParam("id",4);
			Response response = httpRequest.when().get().andReturn();
			Author author = response.getBody().as(new TypeRef<Author>() {});
			
			System.out.println(author.toString());
			
			
		}
		
		@Test(enabled = true)
		public void deseralizeListofMembers() {
			httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader);
			Response response = httpRequest.when().get().andReturn();
			Author[] authors = response.getBody().as(Author[].class);
			
			for(Author m:authors)
			System.out.println(m.toString());
			
			
		}
		
		@Test(enabled = true)
		public void deseralizeListofMembersusingTypeRef() {
			httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader);
			Response response = httpRequest.when().get().andReturn();
			List<Author> authors = response.getBody().as(new TypeRef<List<Author>>() {});
			
			for(Author m:authors)
			System.out.println(m.toString());
			
			
		}

/*		@Test(enabled = true)
		public void deseralizeSingleMemeberNotFoundFailure() {
			RestAssured.basePath += "{id}";

			httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader).pathParam("id", 50);
			Response response = httpRequest.when().get().andReturn();
			MemberNotFoundFailure memberNotFoundFailure = response.getBody().as(MemberNotFoundFailure.class);
			
			System.out.println(memberNotFoundFailure.toString());
			
			
		}
*/		
		

//	}


}
