package basics;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class FileDownloadBasics {
	RequestSpecification httpRequest;
	Response response;
	
	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI = "";
		RestAssured.basePath = "";
		httpRequest = RestAssured.given().auth().basic("admin", "admin");
		response = httpRequest.queryParam("name","yey.jpg").when().get().andReturn();
		
	}
	
	@Test
	public void downloadFileByByte() {
		    try {
			    byte[] downloadFileBytes = response.asByteArray();

		    	FileOutputStream  fos = new FileOutputStream(System.getProperty("user.dir")+"/src/test/resources/Downloads/Test.jpg");
		    	fos.write(downloadFileBytes);
		    	
		    }
		    catch(Exception ex) {
		    	
		    }
	}
	public void downloadFileByInputStream() {
	    try {
		    InputStream downloadFileasStream = response.asInputStream();

	    	File  targetFile = new File(System.getProperty("user.dir")+"/src/test/resources/Downloads/Test.jpg");
	    	Files.copy(downloadFileasStream, targetFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
	    	
	    }
	    catch(Exception ex) {
	    	
	    }
}

	
	@AfterMethod
	public void tearDown() {
		response.then().header("Transfer-Encoding",equalTo("chunked"));
	}

}
