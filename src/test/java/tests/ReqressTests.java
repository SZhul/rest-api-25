package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;

public class ReqressTests {

    /*
    Тесты для платформы https://reqres.in
     */

    @Test
    void reqressApiTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    void reqressAuthorizePostTest(){

        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void missingPasswordTest(){

        String data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .contentType("application/json")
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    // has item проверяет, что в массивах внутри data есть хотя бы один такой элемент
    @Test
    void emailFromMassiveTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", hasItem("byron.fields@reqres.in"));
    }

    @DisplayName("Указываем конкретный объект в массиве data и ищем в нем имя")
    @Test
    void emailFromDirectMassiveTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.find {it.id == 2}.name", is("fuchsia rose"));
    }
}
