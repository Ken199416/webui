package auto.page;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/4 9:55
 * Desc ： 首页
 */
@Data
public class IndexPage {

    //  首页搜索框
    @FindBy(xpath = "//*[@id=\"search2019\"]/div[1]/input")
    public WebElement searchBox;

    //  首页搜索按钮
    @FindBy(xpath = "//*[@id=\"search2019\"]/div[1]/a")
    public WebElement searchBtn;

//    四周年期间弹窗的关闭按钮，仅在四周年内有
//    @FindBy(className = "tanchuang-close-btn")
//    public WebElement fourYearAlertCloseBtn;

    //    顶部导航 - 我的订单
    @FindBy(xpath = "//*[@class=\"fr right\"]/li[1]/a")
    public WebElement topMyOrder;

    //    顶部导航 - 会员中心
    @FindBy(xpath = "//*[@class=\"fr right\"]/li[2]/a")
    public WebElement topMemberCenter;

    //    顶部导航 - 用户名
    @FindBy(className = "fl get-name")
    public WebElement topUserName;

    //    未登录时去注册
    @FindBy(xpath = "//div[@class=\"fl top-login\"]/div[@class=\"login-btn\"]/a[@class=\"login-link link-go-register\"]")
    public WebElement topRegister;

    //    未登录时去登录
    @FindBy(xpath = "//div[@class=\"fl top-login\"]/div[@class=\"login-btn\"]/a[@class=\"login-link login link-go-login\"]")
    public WebElement topLogin;

    //    俱乐部title
    @FindBy(className = "homeClub_title")
    public WebElement homeClubTitle;

    //    大牌推荐title
    @FindBy(className = "fl Brandfl")
    public WebElement brandTitle;

    //    服务平台title
    @FindBy(className = "homedoService_title")
    public WebElement homedoServiceTitle;

    //    战略合作title
    @FindBy(className = "BrandBusiness_title")
    public WebElement brandBusinessTitle;

    //    猜您喜欢title
    @FindBy(className = "fl guessfl")
    public WebElement guessYouLike;

    public IndexPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }



}
