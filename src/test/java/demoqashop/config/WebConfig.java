package demoqashop.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${web}.properties"
})
public interface WebConfig extends Config {

    @Key("browser.name")
    @DefaultValue("CHROME")
    String browserName();

    @Key("browser.version")
    @DefaultValue("101")
    String browserVersion();

    @Key("browser.size")
    @DefaultValue("1200x800")
    String browserSize();

    @Key("UrlWeb")
    @DefaultValue("")
    String URLWeb();

    @Key("UrlApi")
    @DefaultValue("")
    String URIApi();

    @Key("userLogin")
    @DefaultValue("qaguru@qa.guru")
    String userLogin();

    @Key("userPassword")
    @DefaultValue("qaguru@qa.guru1")
    String userPassword();

    @Key("rememberMe")
    @DefaultValue("true")
    String rememberMe();

    @Key("remoteUser")
    @DefaultValue("user1")
    String remoteUser();

    @Key("remotePassword")
    @DefaultValue("1234")
    String remotePassword();

    @Key("remote.URL")
    String remoteUrl();
}
