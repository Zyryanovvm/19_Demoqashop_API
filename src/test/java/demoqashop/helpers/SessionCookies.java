package demoqashop.helpers;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class SessionCookies {

    public String GetCookie(String login, String password, String rememberMe, String CookieKey) {
       String result =  given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .formParam("RememberMe", rememberMe)
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract().cookie(CookieKey);
        return result.toString();
    }

    public SessionCookies AddCookie(String cookieName, String cookie){
        open("/content/images/thumbs/0000215.png");
        Cookie authCookie = new Cookie(cookieName, cookie);
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        return this;
    }

}
