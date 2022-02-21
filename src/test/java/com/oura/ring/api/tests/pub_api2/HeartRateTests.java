package com.oura.ring.api.tests.pub_api2;

import com.oura.ring.api.tests.TestInit;
import com.oura.ring.api.constants.DateStamp;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.oura.ring.api.constants.ErrorMsg.BAD_REQUEST_400;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HeartRateTests extends TestInit {
    @Test
    public void verifySchemaOfHeartRateTest() {
        String startDateTime = "2022-01-28T00:00:00-08:00";
        String endDateTime = "2022-01-29T00:00:00-08:00";

        given()
                .queryParam("start_datetime", startDateTime)
                .queryParam("end_datetime", endDateTime)
                .when()
                .get(HOST + "/v2/usercollection/heartrate")
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsons/HeartRateSchema.json"))
                //check a few values
                .body("data[0].bpm", equalTo(95))
                .body("data[0].source", equalTo("awake"))
                .body("data[0].timestamp", containsString(startDateTime.substring(0,10)));
    }

    @Test
    public void todayHeartRateTest() {
        Response response = given()
                .queryParam("start_datetime", DateStamp.DATE_TIME_TODAY)
                .queryParam("end_datetime", DateStamp.DATE_TIME_TOMORROW)
                .when()
                .get(HOST + "/v2/usercollection/heartrate");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verify400ForHeartRateTest() {
        String errorMsgResponse = String.format("Start time is greater than end time: [start_time: %s; end_time: %s]", DateStamp.DATE_TIME_TOMORROW2, DateStamp.DATE_TIME_TODAY2);
        Response response = given()
                .queryParam("start_datetime", DateStamp.DATE_TIME_TOMORROW)
                .queryParam("end_datetime", DateStamp.DATE_TIME_TODAY)
                .when()
                .get(HOST + "/v2/usercollection/heartrate");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().path("detail"), errorMsgResponse);
        Assert.assertEquals(response.getStatusLine(), BAD_REQUEST_400);
    }

    @Test(enabled = false) //todo simulate old mobile v of App?
    public void verify426ForHeartRateTest() {
        Response response = given()
                .when()
                .get(HOST + "/v2/usercollection/heartrate");

        Assert.assertEquals(response.getStatusCode(), 426);
    }
}
