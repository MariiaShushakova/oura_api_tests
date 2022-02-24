package com.oura.ring.api.tests.pub_api2;

import com.oura.ring.api.tests.TestInit;
import com.oura.ring.api.constants.DateStamp;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.oura.ring.api.constants.ErrorMsg.BAD_REQUEST_400;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DailyActivityTests extends TestInit {

    @Description("Check 'daily_activity' endpoint for particular date")
    @Story("Public APIv2")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifySchemaOfDailyActivityTest() {
        String startDate = "2022-01-29";
        String endDate = "2022-01-30";

        given()
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .when()
                .get(HOST + "/v2/usercollection/daily_activity")
                .then()
                .assertThat()
                .statusCode(200)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsons/DailyActivitySchema.json"))
                //check a few values
                .body("data[0].score", equalTo(84))
                .body("data[0].active_calories", equalTo(294))
                .body("data[0].steps", equalTo(8590))
                .body("data[0].day", equalTo(startDate));
    }

    @Test
    public void todayDailyActivityTest() {
        Response response = given()
                .queryParam("start_date", DateStamp.DATE_TODAY)
                .queryParam("end_date", DateStamp.DATE_TOMORROW)
                .when()
                .get(HOST + "/v2/usercollection/daily_activity");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verify400ForDailyActivityTest() {
        String errorMsgResponse = String.format("Start date is greater than end date: [start_date: %s; end_date: %s]", DateStamp.DATE_TOMORROW, DateStamp.DATE_TODAY);
        Response response = given()
                .queryParam("start_date", DateStamp.DATE_TOMORROW)
                .queryParam("end_date", DateStamp.DATE_TODAY)
                .when()
                .get(HOST + "/v2/usercollection/daily_activity");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().path("detail"), errorMsgResponse);
        Assert.assertEquals(response.getStatusLine(), BAD_REQUEST_400);
    }

    @Test(enabled = false) //todo simulate old mobile v of App?
    public void verify426ForDailyActivityTest() {
        Response response = given()
                .when()
                .get(HOST + "/v2/usercollection/daily_activity");

        Assert.assertEquals(response.getStatusCode(), 426);
    }
}
