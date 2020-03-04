package auto.test;

import auto.common.test.BaseTest;
import auto.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by haomingjian ,
 * Date ： 2019/12/2 13:54
 */
@Test
public class SuiteTestCase extends BaseTest {
    
    private static Logger logger = LoggerFactory.getLogger(SuiteTestCase.class);

    @BeforeSuite
    public void start() throws Exception {
        logger.info("UI 自动化测试开始...,开始时间：{}",new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("global");
//        String webdriverWorkMode = resourceBundle.getString("webdriver.work.mode");
        String chromeDriverPath = resourceBundle.getString("webdriver.chrome.driver.path");
        String firefoxDriverPath = resourceBundle.getString("webdriver.firefox.driver.path");
        String ieDriverPath = resourceBundle.getString("webdriver.ie.driver.path");
        String gridJarPath = resourceBundle.getString("grid.server.path")+"\\selenium-server-standalone-3.13.0.jar";
        String hubUrl = resourceBundle.getString("grid.hub.url");
        String nodePort = resourceBundle.getString("grid.node.port");
        if (workMode.equals("grid")) {
//            先关闭所有远程节点
            CommonUtils.killGridPort();
//          启动hub节点
            String startHubCommand = "java -jar " + gridJarPath + " -role hub";
            Runtime.getRuntime().exec(startHubCommand) ;
            logger.info("启动hub节点中... Command:" + startHubCommand);
            String startNodeCommand = "java -Dwebdriver.chrome.driver=\"" + chromeDriverPath + "\" " +
                    "-Dwebdriver.gecko.driver=\"" +  firefoxDriverPath+ "\" " +
                    "-Dwebdriver.ie.driver=\"" + ieDriverPath + "\" " +
                    "-jar " + gridJarPath + " -role node -port " + nodePort + " -hub "+hubUrl;
            Runtime.getRuntime().exec(startNodeCommand);
            logger.info("启动node节点中... Command:" + startNodeCommand);
//            断言服务是否启动,查看4444端口的服务个数
            Thread.sleep(5000);
            logger.info("共启用{}个服务",getPidNum(true));
            Assert.assertEquals(Integer.parseInt(resourceBundle.getString("grid.pid.num")),getPidNum(false));
        }else {
            logger.info("未启用远程节点...");
        }
    }

    @AfterSuite
    public void end() throws Exception{
        ResourceBundle resourceBundle = ResourceBundle.getBundle("global");
//        String webdriverWorkMode = resourceBundle.getString("webdriver.work.mode");
        if (workMode.equals("grid")){
            CommonUtils.killGridPort();
            Assert.assertEquals(0,getPidNum(false));
            logger.info("关闭远程节点 Success ...");
        }
        logger.info("UI 自动化测试结束...,结束时间：{}",new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date()));
    }


    /**
     * @Author : haomingjian , 2019/12/13 14:16
     * @param output
     * @retrun : {@link int}
     * @Description : 获取当前端口号4444 有关的服务个数，参数output：true则输出cmd返回的结果和个数,false则的不输出，只返回个数
     */
    private int getPidNum(boolean output) throws Exception{
        Process proc = Runtime.getRuntime().exec("cmd /c netstat -ano | findstr \"4444\"");
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
        String line = null;
        Set<String> pidList = new HashSet<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (output){
                logger.info(line);
            }
            pidList.add(line.substring(line.lastIndexOf(" "), line.length()));
        }
        return pidList.size();
    }

}
