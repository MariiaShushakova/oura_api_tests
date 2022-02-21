package com.oura.ring.api.tests.pub_api1;

import com.oura.ring.api.tests.TestInit;
import com.oura.ring.api.tests.util.RestAssuredAuthentication;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AccountTests extends TestInit {

    @Test
    public void demoAPITest(){
        RestAssuredAuthentication.useToken(TOKEN);
        Response response = given()
                .when()
                .get(HOST + "/v1/userinfo");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
