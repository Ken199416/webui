package auto.service.impl;

import auto.page.*;
import auto.service.LoginService;
import auto.utils.BrowserUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/20 15:48
 * Desc ： 登录服务
 */
@Service
public class LoginServiceImpl implements LoginService {
    public static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private static ResourceBundle resourceBundle;
    static {
        resourceBundle = ResourceBundle.getBundle("global");
    }

    /**
     * @Author : haomingjian , 2019/12/2 17:25
     * @param driver
     * @param username
     * @param password
     * @param isRememberPassword 是否记住密码？ 0：不记住，1：记住
     * @retrun :
     * @Description :
     */
    @Override
    public void loginAndRemember(String env, WebDriver driver, String username, String password, String isRememberPassword) {
        LoginPage loginPage = new LoginPage(driver);
        driver.get(resourceBundle.getString(env +".page.login"));
        if (Integer.parseInt(isRememberPassword) == 1){
            BrowserUtils.clickByElement(driver,loginPage.rememberPasswordRadio);
            logger.info("已勾选记住密码...");
        }
        loginPage.usernameInput.clear();
        loginPage.usernameInput.sendKeys(username);
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginBtn.click();
    }

    /**
     * @Author : haomingjian , 2019/12/2 17:25
     * @param driver
     * @param username
     * @param password
     * @retrun :
     * @Description :
     */
    @Override
    public void login(String env, WebDriver driver,String username,String password) {

        logger.info("用户开始在登录页进行登陆");

        LoginPage loginPage = new LoginPage(driver);
//        使用工具类的toURl，在打开页面之前会先最大化浏览器
        BrowserUtils.toUrl(driver, resourceBundle.getString(env +".page.login"));
        loginPage.usernameInput.clear();
        loginPage.usernameInput.sendKeys(username);
        loginPage.passwordInput.clear();
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginBtn.click();
    }


//  是否登录
    @Override
    public Boolean isLogin(WebDriver driver) {
        logger.info("通过ticket判断用户是否已登陆");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("ticket")){
                logger.info("用户已登录，ticket ： {}",cookie.getValue());
                return true;
            }
        }
        logger.info("用户未登录");
        return false;
    }

//    清除登录的ticket
    public void clearLoginTicket(WebDriver driver){
        driver.manage().deleteCookieNamed("ticket");
    }
}
