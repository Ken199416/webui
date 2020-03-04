package auto.common.listener;

import auto.common.test.BaseTest;
import auto.utils.BrowserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/7 9:46
 * Desc ： 用例执行监听器，用例成功，失败，跳过时，会执行其中对应的方法
 */
public class TestExecuteListener extends TestListenerAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(TestExecuteListener.class);

    /**
     * @Author : haomingjian , 2019/11/26 16:32
     * @param tr
     * @retrun :
     * @Description : 用例成功后，输出日志
     */
    @Override
    public void onTestSuccess(ITestResult tr) {
        logger.info(tr.getMethod().getDescription()+"   -------   " + BaseTest.browserName + "浏览器 执行通过");
        super.onTestSuccess(tr);
    }

    /**
     * @Author : haomingjian , 2019/11/26 16:32
     * @param tr
     * @retrun :
     * @Description : 用例被跳过，输出日志
     */
    @Override
    public void onTestSkipped(ITestResult tr) {
        logger.info(tr.getMethod().getDescription()+"   -------   " + BaseTest.browserName + "浏览器 用例被跳过");
        super.onTestSkipped(tr);
    }

    /**
     * @Author : haomingjian , 2019/11/26 16:33
     * @param tr
     * @retrun :
     * @Description : 用例失败时，截取当前屏幕截图并保存到screen路径下
     */
    @Override
    public void onTestFailure(ITestResult tr) {
//        获取父类，拿到driver，执行截图
        BaseTest baseTest = (BaseTest)tr.getInstance();
        BrowserUtils.screenByDriver(baseTest.getDriver());
        logger.info(tr.getMethod().getDescription()+"   -------   " + BaseTest.browserName + "浏览器 执行失败，已截图");
    }


}
