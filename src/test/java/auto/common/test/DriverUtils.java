package auto.common.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/2 14:33
 *
 */
public class DriverUtils {
    //    定义路径
    private static final String FILE_PATH = "\\screen";
    private static Logger logger = LoggerFactory.getLogger(DriverUtils.class);
    private static  ResourceBundle resourceBundle;
    static {
        resourceBundle = ResourceBundle.getBundle("global");
    }

    /**
     * @param DriverName
     * @Author : haomingjian , 2019/11/2 15:16
     * @retrun :
     * @Description : 设置driver的驱动
     */
    public static WebDriver getDriverByName(String DriverName) {
        if (DriverName.equals("chrome")) {
            String chromeDriverPath = resourceBundle.getString("webdriver.chrome.driver.path");
            logger.info("webdriver.chrome.driver.path="+chromeDriverPath);
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            logger.info("启用{}浏览器",DriverName);
            return new ChromeDriver();
        }else if (DriverName.equals("firefox")){
            System.setProperty("webdriver.gecko.driver",resourceBundle.getString("webdriver.firefox.driver.path"));
            logger.info("启用{}浏览器",DriverName);

            return new FirefoxDriver();
        }else if (DriverName.equals("ie")){
            System.setProperty("webdriver.ie.driver", resourceBundle.getString("webdriver.ie.driver.path"));
            logger.info("启用{}浏览器",DriverName);
            return new InternetExplorerDriver();
        }
        logger.error("未找到对应浏览器配置，请检查是否存在‘{}’浏览器",DriverName);
//        后续会考虑设置默认浏览器
        return null;
    }


//    获取远程节点driver
    public static WebDriver getGridWebDriver(String browserName){
        DesiredCapabilities capabilities = null;
        WebDriver driver = null;
        String gridUrl = resourceBundle.getString("grid.node.url");
        try {
            if (browserName.equals("chrome")) {
                capabilities = DesiredCapabilities.chrome();
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(Platform.WINDOWS);
                logger.info("节点地址：{}，浏览器：{}",gridUrl,browserName);
                driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
            }else if (browserName.equals("ie")){
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setBrowserName("internet explorer");
                capabilities.setPlatform(Platform.WINDOWS);
                logger.info("节点地址：{}，浏览器：{}",gridUrl,browserName);
                driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
            }else if (browserName.equals("firefox")){
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setPlatform(Platform.WINDOWS);
                logger.info("节点地址：{}，浏览器：{}",gridUrl,browserName);
                driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }


    public static WebDriver getDriver(String workMode, String browserName){
        WebDriver webDriver = null;
//        String webdriverWorkMode = resourceBundle.getString("webdriver.work.mode");
        if (workMode.equals("local")){
            logger.info("使用本地driver");
            webDriver = getDriverByName(browserName);
        }else{
            logger.info("使用远程driver");
            webDriver = getGridWebDriver(browserName);
        }
        return webDriver;
    }



    /**
     * @Author : haomingjian , 2019/12/13 10:26
     * @param driver
     * @retrun :
     * @Description : 安全的关闭driver，防止测试用例执行没有问题时，关闭WebDriver之前， WebDriver crash导致测试用例失败
     */
    public static void securityCloseDriver(WebDriver driver){
        try{
            driver.close();
            logger.info("{}_WebDriver已关闭...", BaseTest.browserName);
        }catch (NoSuchSessionException e){
            logger.error("未找到可关闭的{}_WebDriver，请查看测试报告结果...", BaseTest.browserName);
        }
    }

}
