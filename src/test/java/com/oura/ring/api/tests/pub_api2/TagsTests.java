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

public class TagsTests extends TestInit {
    @Test
    public void verifySchemaOfTagsTest() {
        String startDate = "2022-02-01";
        String endDate = "2022-02-02";

        given()
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .when()
                .get(HOST + "/v2/usercollection/tag")
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsons/TagSchema.json"))
                //check a few values
                .body("data[0].day", equalTo(startDate))
                .body("data[0].text", equalTo("A lot of work "))
                .body("data[0].tags[0]", equalTo("tag_generic_anxiety"));
    }

    @Test
    public void todayTagsTest() {
        Response response = given()
                .queryParam("start_date", DateStamp.DATE_TODAY)
                .queryParam("end_date", DateStamp.DATE_TOMORROW)
                .when()
                .get(HOST + "/v2/usercollection/tag");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verify400ForTagsTest() {
        String errorMsgResponse = String.format("Start date is greater than end date: [start_date: %s; end_date: %s]", DateStamp.DATE_TOMORROW, DateStamp.DATE_TODAY);
        Response response = given()
                .queryParam("start_date", DateStamp.DATE_TOMORROW)
                .queryParam("end_date", DateStamp.DATE_TODAY)
                .when()
                .get(HOST + "/v2/usercollection/tag");

        //response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().path("detail"), errorMsgResponse);
        Assert.assertEquals(response.getStatusLine(), BAD_REQUEST_400);
    }

    @Test(enabled = false) //todo simulate old mobile v of App?
    public void verify426ForTagsTest() {
        Response response = given()
                .when()
                .get(HOST + "/v2/usercollection/tag");

        Assert.assertEquals(response.getStatusCode(), 426);
    }
}
