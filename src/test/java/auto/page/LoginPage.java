package auto.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/2 14:30
 * Desc ： 登录Page
 */
public class LoginPage {

    //    用户名输入框
    @FindBy(xpath = "//*[@id=\"login\"]/div[2]/div[1]/ul/li[1]/input")
    public WebElement usernameInput;
    //    密码输入框
    @FindBy(xpath = "//*[@id=\"login\"]/div[2]/div[1]/ul/li[2]/input")
    public WebElement passwordInput;
    //    登录按钮
    @FindBy(xpath = "//*[@id=\"login\"]/div[2]/div[1]/a")
    public WebElement loginBtn;
    //    登录错误提示元素
    @FindBy(xpath = "//*[@id=\"login\"]/div[2]/div[1]/div[1]/div")
    public WebElement loginErrorContent;
    //    记住密码选项
    @FindBy(xpath = "//*[@id=\"login\"]/div[2]/div[1]/div[2]/div[1]/p")
    public WebElement rememberPasswordRadio;
    //    忘记密码
    @FindBy(xpath = "//*[@class=\"remember-box\"]/a[1]")
    public WebElement forgetPasswordBtn;
    //    注册按钮
    @FindBy(xpath = "//*[@id=\"login\"]/div[3]/a")
    public WebElement registerBtn;


    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

}
