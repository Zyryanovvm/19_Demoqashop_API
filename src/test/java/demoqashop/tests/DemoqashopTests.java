package demoqashop.tests;

import demoqashop.config.WebConfig;
import demoqashop.helpers.SessionCookies;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemoqashopTests extends TestBase {
    SessionCookies sessionCookies = new SessionCookies();
    static WebConfig config = ConfigFactory.create(WebConfig.class);

    String login = config.userLogin(),
            password = config.userPassword(),
            rememberMe = config.rememberMe(),
            authCookieName = "NOPCOMMERCE.AUTH",
            requestTokenBody = "d1CSYKDCNwRiND5gXYSdhc8fgJ_36HnnyITaqIFj5hM-ztXhnLpu4H3w53LsiA" +
                    "JBZ13sesfXRwDYVJHxRc8ZqGue36RFVRPiTEZOkMwB7XJNvp7cPimoQQOZQjNeGzrB0",
            requestTokenCookie = "HpYk1BodWMnIi16_T9bQQ69xaxwMe6mQC2l8pDjX63r7hrF00UdyO6bRNmAn" +
                    "SQoVXo5AFMeI1ercxS0HUabTuxwaCUh6LN3_D8ajHKgDSzA1";
    static String newNameUser = System.getProperty("userName", "1");

    @Test
    @DisplayName("Registration Authorization")
    void loginTest() {

        step("Create new session cookies", () -> {
            String authCookieValue = sessionCookies.GetCookie(login, password, rememberMe, authCookieName);

            step("Added cookies in session web browser", () ->
                    sessionCookies.AddCookie(authCookieName, authCookieValue));
        });

        step("Open web page", () ->
                open(""));
        step("assert Authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    @DisplayName("In user account Update name for API")
    void updateNameApiTest() {
        step("Create new session cookies for API", () -> {
            String authCookieValue = sessionCookies.GetCookie(login, password, rememberMe, authCookieName);
            step("Update name for API", () -> {
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("__RequestVerificationToken", requestTokenBody)
                        .formParam("Gender", "M")
                        .formParam("FirstName", newNameUser)
                        .formParam("LastName", "guru")
                        .formParam("Email", "qaguru@qa.guru")
                        .formParam("save-info-button", "Save")
                        .cookie(authCookieName, authCookieValue)
                        .cookie("__RequestVerificationToken", requestTokenCookie)
                        .log().all()
                        .when()
                        .log().all()
                        .post("/customer/info")
                        .then()
                        .log().all()
                        .statusCode(302);
            });
            step("Added cookies in session web browser", () ->
                    sessionCookies.AddCookie(authCookieName, authCookieValue));
        });
        step("Open web page", () ->
                open("/customer/info"));
        step("assert Authorization", () ->
                $("#FirstName").getValue().equals(newNameUser));

    }
}
