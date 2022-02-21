package com.oura.ring.api.tests.pub_api2;

import com.oura.ring.api.tests.TestInit;
import com.oura.ring.api.constants.DateStamp;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PersonalInfoTests extends TestInit {

    @Test
    public void personalInfoTest() {
        Response response = given()
                .when()
                .get(HOST + "/v2/usercollection/personal_info");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verifyBodyOfPersonalInfoTest() {
        given()
                .when()
                .get(HOST + "/v2/usercollection/personal_info")
                .then()
                .assertThat()
                .statusCode(200)
                .body("age", equalTo(25))
                .body("weight", equalTo(55.0F))
                .body("height", equalTo(1.7F))
                .body("biological_sex", equalTo("female"))
                .body("email", equalTo("mariia.shushakova@ouraring.com"));
    }

    @Test
    public void verifyPersonalInfoWithDatesTest() {
        given()
                .when()
                .queryParam("start_date", DateStamp.DATE_TODAY)
                .queryParam("end_date", DateStamp.DATE_TOMORROW)
                .get(HOST + "/v2/usercollection/personal_info")
                .then()
                .assertThat()
                .statusCode(200)
                .body("age", equalTo(25))
                .body("weight", equalTo(55.0F))
                .body("height", equalTo(1.7F))
                .body("biological_sex", equalTo("female"))
                .body("email", equalTo("mariia.shushakova@ouraring.com"));
    }

    @Test(enabled = false) //todo simulate old mobile v of App?
    public void verify426ForPersonalInfoTest() {
        Response response = given()
                .when()
                .get(HOST + "/v2/usercollection/personal_info");

        Assert.assertEquals(response.getStatusCode(), 426);
    }
}
