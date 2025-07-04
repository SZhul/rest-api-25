import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;

public class SelenoidTests {

    // дока: github.com/rest-assured/wiki/Usage
    /*
    1. Сделать запрос по адресу https://selenoid.autotests.cloud/status
    2. Получить ответ в формате JSON
    {
total: 5,
used: 0,
queued: 0,
pending: 1,
browsers:
{
chrome: {
127.0: { },
128.0: { }
},
firefox: {
124.0: { },
125.0: { }
},
opera: {
108.0: { },
109.0: { }
}
}
}
    3. Проверить, что total = 5
     */

    @Test
    void selenoidRestAssuredTest() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    void selenoidRestAssuredWithGivenTest() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    void selenoidRestAssuredWithLogsTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    void selenoidRestAssuredWithSomeLogsTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(5));
    }

    @DisplayName("Проверяем значение в массиве")
    @Test
    void selenoidRestAssuredCheckChromeVersionTest() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("127.0"));
    }


    @Test
    void checkWdHubStatusTest() {
        given()
                .log().uri()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void checkWdHubStatusWithAuthTest() {
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
