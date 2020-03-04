package auto.common.test;

import auto.AutoTestApplication;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/6 15:31
 */
@SpringBootTest(classes = AutoTestApplication.class)
public class BaseTest extends AbstractTestNGSpringContextTests {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    //环境
    public static String env = System.getProperty("envID");
    //浏览器
    public static String browserName = System.getProperty("browserName");
    //启动模式，本地还是远程
    public static String workMode = System.getProperty("workMode");

//=========================================================================================
//    调试用的配置
//    public static String env = "pro";
//    public static String browserName = "firefox";

    //查询时长
    public static final int WAIT_TIME = 10;
    //查询频率（S）
    public static final int WAIT_FREQUENCY = 1;

    public WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

}
