package testNG_java;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import utils.ConfigReader;

import java.util.List;

public class Football {
    @Test
    public void getCompetitionsTest() {
        RestAssured.baseURI = ConfigReader.readProperty("QA_football_base_url");
        RestAssured.basePath = ConfigReader.readProperty("QA_football_base_path");
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .body("competitions.find{it.id==2166}.name", Matchers.is("AFC Champions League"))
                .body("competitions.findAll{it.area.name=='Africa'}.name", Matchers.hasItems("WC Qualification CAF", "AFC Champions League"))
                .and()
                .body("competitions.min{it.id}.name", Matchers.equalTo("FIFA World Cup"))
                .body("competitions.collect{it.id}.sum()", Matchers.greaterThan(10000))
                .extract().response();
        List<String> africaCompetitionsList = response.path("competitions.findAll{it.area.name=='Africa'}.name");
        System.out.println(africaCompetitionsList);

    }
}