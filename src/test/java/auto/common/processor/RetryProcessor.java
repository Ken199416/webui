package auto.common.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.ResourceBundle;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/7 11:43
 * Desc ： 用例执行失败处理器，添加到@auto.test(retryAnalyzer = RetryProcessor.class)中即可生效
 */
public class RetryProcessor implements IRetryAnalyzer {
    private static Logger logger = LoggerFactory.getLogger(RetryProcessor.class);

    private static int COUNT = 0;

    /**
     * @Author : haomingjian , 2019/11/26 16:36
     * @param result
     * @retrun : {@link boolean}
     * @Description : 当用例失败时，会读取全局配置中的重试次数，去重试该用例，超过重试次数，则开始执行下一条用例
     */
    @Override
    public boolean retry(ITestResult result) {
        ResourceBundle resource = ResourceBundle.getBundle("global");
        int maxRetryCount =  Integer.parseInt(resource.getString("test.retry.count"));
        if (!result.isSuccess()) {
            if (COUNT < maxRetryCount) {
                COUNT++;
                logger.info("用例执行失败，即将重试第{}次",COUNT);

//                设置用例失败
                result.setStatus(ITestResult.FAILURE);
                return true;
            } else {
                logger.info("已重试{}次，一共执行{}次，全部失败...",maxRetryCount,maxRetryCount+1);
                result.setStatus(ITestResult.FAILURE);
            }
        } else {
            result.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
