package com.oura.ring.api.tests;

import com.oura.ring.api.tests.util.RestAssuredAuthentication;
import org.testng.annotations.BeforeMethod;

public class TestInit {
    public static final String HOST = "https://api.ouraring.com";
    public static final String TOKEN = "BGYBP5WUJPYIJQBP42KXVLWOIWQ6HJNR";

    @BeforeMethod
    public void auth() {
        RestAssuredAuthentication.useToken(TOKEN);
    }
}
