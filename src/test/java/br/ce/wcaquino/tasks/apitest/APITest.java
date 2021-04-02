package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {

		RestAssured.given().log().all().when().get("/todo").then().statusCode(200).log().all();

	}

	@Test
	public void deveAdicionaTarefaComSucesso() {
		RestAssured.given()
		.body("{\"task\": \"vinicin\",\"dueDate\": \"2040-12-30\"}")
		.contentType(ContentType.JSON)
		.log().all()
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201);
		
	}
	
	@Test
	public void naoDeveAdicionaTarefaComSucesso() {
		RestAssured.given()
		.body("{\"task\": \"vinicin\",\"dueDate\": \"2010-12-30\"}")
		.contentType(ContentType.JSON)
		.log().all()
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
		
	}


}
