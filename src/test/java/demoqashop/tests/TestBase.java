package demoqashop.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import demoqashop.config.WebConfig;
import demoqashop.helpers.Attach;
import org.aeonbits.owner.ConfigFactory;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    static WebConfig config = ConfigFactory.create(WebConfig.class);
    static String useRemote = System.getProperty("web", "remote");

    @BeforeAll
    static void config() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browser = config.browserName();
        Configuration.browserVersion = config.browserVersion();
        Configuration.browserSize = config.browserSize();
        Configuration.baseUrl = config.URLWeb();
        RestAssured.baseURI = config.URIApi();

        if (useRemote.equals("remote")) {
            Configuration.remote = ("https://" + config.remoteUser() + ":"
                    + config.remotePassword() + "@" + config.remoteUrl());
        }
    }

    @AfterEach
    void afterEach() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();

        if (useRemote.equals("remote")) {
            Attach.addVideo();
        }

        closeWebDriver();
    }
}
