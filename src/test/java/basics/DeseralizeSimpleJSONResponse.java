package basics;

import java.lang.reflect.Member;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeseralizeSimpleJSONResponse {
	RequestSpecification httpRequest;
	Header acceptHeader;
	
	@BeforeMethod
	public void setUp() {
		RestAssured.basePath = "/api/members";
		RestAssured.baseURI = "http://localhost:5002 ";
		acceptHeader = new Header("Accept","application/json");
		
	}
	
	@Test(enabled = true)
	public void deseralizeSingleMember() {
		RestAssured.basePath += "{id}";
		httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader).pathParam("id",4);
		Response response = httpRequest.when().get().andReturn();
		Member memeber = response.getBody().as(Member.class);
		System.out.println(memeber.toString());
		
		
	}
	
	@Test(enabled = true)
	public void deseralizeSingleMemberUsingTypeRef() {
		RestAssured.basePath += "{id}";
		httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader).pathParam("id",4);
		Response response = httpRequest.when().get().andReturn();
		Member memeber = response.getBody().as(new TypeRef<Member>() {});
		System.out.println(memeber.toString());
		
		
	}
	
	@Test(enabled = true)
	public void deseralizeListofMembers() {
		httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader);
		Response response = httpRequest.when().get().andReturn();
		Member[] members = response.getBody().as(Member[].class);
		
		for(Member m:members)
		System.out.println(m.toString());
		
		
	}
	
	@Test(enabled = true)
	public void deseralizeListofMembersusingTypeRef() {
		httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader);
		Response response = httpRequest.when().get().andReturn();
		List<Member> members = response.getBody().as(new TypeRef<List<Member>>() {});
		
		for(Member m:members)
		System.out.println(m.toString());
		
		
	}

	@Test(enabled = true)
	public void deseralizeSingleMemeberNotFoundFailure() {
		RestAssured.basePath += "{id}";

		httpRequest = RestAssured.given().auth().basic("admin", "admin").header(acceptHeader).pathParam("id", 50);
		Response response = httpRequest.when().get().andReturn();
		MemberNotFoundFailure memberNotFoundFailure = response.getBody().as(MemberNotFoundFailure.class);
		
		System.out.println(memberNotFoundFailure.toString());
		
		
	}
	
	

}
