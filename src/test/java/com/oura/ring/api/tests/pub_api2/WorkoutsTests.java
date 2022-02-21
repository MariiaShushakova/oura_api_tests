package com.oura.ring.api.tests.pub_api2;

import com.oura.ring.api.tests.TestInit;
import com.oura.ring.api.constants.DateStamp;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.oura.ring.api.constants.ErrorMsg.BAD_REQUEST_400;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WorkoutsTests extends TestInit {

    @Test
    public void verifySchemaOfWorkoutsTest() {
        String startDate = "2022-02-01";
        String endDate = "2022-02-02";

        given()
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .when()
                .get(HOST + "/v2/usercollection/workout")
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsons/WorkoutSchema.json"))
                //check a few values
                .body("data[0].activity", equalTo("cycling"))
                .body("data[0].calories", equalTo(135.525F))
                .body("data[0].intensity", equalTo("moderate"))
                .body("data[0].source", equalTo("confirmed"));
    }

    @Test
    public void todayWorkoutTest() {
        Response response = given()
                .queryParam("start_date", DateStamp.DATE_TODAY)
                .queryParam("end_date", DateStamp.DATE_TOMORROW)
                .when()
                .get(HOST + "/v2/usercollection/workout");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verify400ForWorkoutsTest() {
        String errorMsgResponse = String.format("Start date is greater than end date: [start_date: %s; end_date: %s]", DateStamp.DATE_TOMORROW, DateStamp.DATE_TODAY);
        Response response = given()
                .queryParam("start_date", DateStamp.DATE_TOMORROW)
                .queryParam("end_date", DateStamp.DATE_TODAY)
                .when()
                .get(HOST + "/v2/usercollection/workout");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().path("detail"), errorMsgResponse);
        Assert.assertEquals(response.getStatusLine(), BAD_REQUEST_400);
    }

    @Test(enabled = false) //todo simulate old mobile v of App?
    public void verify426ForWorkoutsTest() {
        Response response = given()
                .when()
                .get(HOST + "/v2/usercollection/workout");

        Assert.assertEquals(response.getStatusCode(), 426);
    }
}
