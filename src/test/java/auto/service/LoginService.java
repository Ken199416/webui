package auto.service;

import org.openqa.selenium.WebDriver;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/20 15:47
 */
public interface LoginService {


    void login(String env, WebDriver driver, String username, String password);

    void loginAndRemember(String env, WebDriver driver, String username, String password, String isRememberPassword);

    Boolean isLogin(WebDriver driver);

    void clearLoginTicket(WebDriver driver);


}
