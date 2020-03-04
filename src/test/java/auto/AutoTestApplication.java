package auto;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/11 13:48
 * Desc : 启动类，scanBasePackages扫描其中报下的所有依赖注入注解
 */

@SpringBootApplication( exclude={DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class},scanBasePackages = {"auto"})
public class AutoTestApplication {


}
