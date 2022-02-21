package com.oura.ring.api.tests.util;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.parsing.Parser;

import static com.oura.ring.api.tests.TestInit.HOST;

public class RestAssuredAuthentication {

    public static void useToken(String token){
        PreemptiveOAuth2HeaderScheme authScheme = new PreemptiveOAuth2HeaderScheme();
        authScheme.setAccessToken(token);
        RestAssured.baseURI = HOST;
        RestAssured.authentication = authScheme;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.useRelaxedHTTPSValidation();
    }
}
