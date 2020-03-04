package auto.test.zdebug;

import auto.common.data.ExcelProvider;
import auto.common.processor.RetryProcessor;
import auto.common.test.BaseTest;
import auto.common.test.DriverUtils;
import auto.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/25 18:16
 */
@Test
public class LoginTestCase extends BaseTest {

    @Autowired
    public LoginService loginService;

    @BeforeTest
    public void open(){

        //        本地测试
        env = "pro";
        workMode = "local";
        browserName = "chrome";


        driver = DriverUtils.getDriver(workMode,browserName);
        logger.info("{}_Driver已创建...",browserName);
    }


    @Test(dataProvider = "data",description = "LoginTest(登录流程)",retryAnalyzer = RetryProcessor.class)
    public void loginTest(Map<String, String> data){
        logger.info("开始执行case ： {}",data.get("caseID"));
        loginService.login(env,driver,data.get("username"),data.get("password"));
        Assert.assertTrue(loginService.isLogin(driver));
    }

    @AfterTest
    public void close(){
        DriverUtils.securityCloseDriver(driver);
    }



    @DataProvider(name="data")
    public Iterator<Object[]> getData() throws IOException { return new ExcelProvider(env,this, 0);}



}
