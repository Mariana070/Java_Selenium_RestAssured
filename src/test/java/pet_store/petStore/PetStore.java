package pet_store.petStore;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pet_store.pojo.PetstorePojo;
import utils.ConfigReader;
import utils.PayloadUtils;

import java.io.File;
import java.util.Map;

public class PetStore {
    @Test
    public void createPetTest(){
        RestAssured.baseURI= ConfigReader.readProperty("QA_petstore_base_url");
        RestAssured.basePath= ConfigReader.readProperty("QA_petstore_base_path");
        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload(1056787,"Rocky")).when().post()
                .then().statusCode(200).extract().response();
        PetstorePojo parsedResponse = response.as(PetstorePojo.class);
        String actualName = parsedResponse.getName();
        Assert.assertEquals("Rocky",actualName);
    }
    @Test
    public void createPetWithJsonFileTest(){
        RestAssured.baseURI=ConfigReader.readProperty("QA_petstore_base_url");
        RestAssured.basePath=ConfigReader.readProperty("QA_petstore_base_path");
        File jsonFile=new File("src/test/resources/pet.json");
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonFile)
                .when().post()
                .then().statusCode(200)
                .log().body();
        PetstorePojo pet = new PetstorePojo();
        pet.setId(78923);
        pet.setName("Bella");
        pet.setStatus("barking");
        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
                .when().post()
                .then()
                .log().body().statusCode(200).extract().response();
        JsonPath parsedResponse= response.jsonPath();
        String actualName= parsedResponse.getString("name");
        Assert.assertEquals("Bella",actualName);

         parsedResponse.getString("category.name");

    }
    @Test
    public void deletePetTest(){
        //1. Create a pet
        RestAssured.baseURI= ConfigReader.readProperty("QA_petstore_base_url");
        RestAssured.basePath=ConfigReader.readProperty("QA_petstore_base_path");
        int petId= 576849;
        String petName="Marley";
        String petStatus = "playing";

        PetstorePojo  petPayload = new PetstorePojo();
        petPayload.setId(petId);
        petPayload.setName(petName);
        petPayload.setStatus(petStatus);
        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(petPayload)
                .when().post()
                .then().statusCode(200).extract().response();
        PetstorePojo parsedResponse= response.as(PetstorePojo.class);
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petStatus,parsedResponse.getStatus());
        response= RestAssured.given().accept(ContentType.JSON)
                .when().delete(String.valueOf(petId))
                .then().statusCode(200).extract().response();
        Map<String,Object> deserializationResponse= response.as(new TypeRef<Map<String,Object>>() {});
        String actualMessage= (String) deserializationResponse.get("message");
        int actualStatusCode = (int) deserializationResponse.get("code");
        Assert.assertEquals(200,actualStatusCode);
        Assert.assertEquals(String.valueOf(petId),actualMessage);
        RestAssured.given().accept(ContentType.JSON)
                .when().delete(String.valueOf(petId))
                .then().statusCode(404);
    }
}