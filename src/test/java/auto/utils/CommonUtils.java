package auto.utils;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/7 9:52
 */
public class CommonUtils {
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);


    public static final ResourceBundle resourceBundle;
//加载全局配置文件
    static {
        resourceBundle = ResourceBundle.getBundle("global");
    }

    /**
     *
     * @return
     * @throws IOException
     * @Description : 获取根目录地址
     */
    public static String getRootPath() throws IOException {
        return new File("./").getCanonicalPath();
    }


    /**
     * @Author : haomingjian , 2019/12/2 18:17
     * @param
     * @retrun : {@link Map< String, String>}
     * @Description : 获取节点的地址，并封装到HashMap中 , 已弃用
     */
//    public static Map<String,String> getGridNodeUrl(){
//        String nodeUrl = resourceBundle.getString("grid.node.url");
//        String [] nodeUrls = nodeUrl.split(",");
//        Map<String,String> nodeMap = new HashMap<>();
//        String [] currentNode;
//        for (String node:nodeUrls) {
//            currentNode = node.split("<=>");
//            nodeMap.put(currentNode[0],currentNode[1]);
//        }
//        return nodeMap;
//    }

    /**
     * @Author : haomingjian , 2019/11/21 15:34
     * @param alertContent
     * @retrun : {@link Map< String, String>}
     * @Description : 获取弹窗的定位方式和定位表达式String [0]: 定位方式，String [1] :表达式
     */

//    public static FindElementConfig getAlertConfig(String alertContent){
//        String alertStr = resourceBundle.getString("alert."+alertContent);
//        String [] alertConfig = alertStr.split(":");
//        FindElementConfig findElementConfig = new FindElementConfig();
//        findElementConfig.setWay(alertConfig[0]);
//        findElementConfig.setExpression(alertConfig[1]);
//        return findElementConfig;
//    }


    /**
     * @Author : haomingjian , 2019/11/26 16:48
     * @param findElementConfig
     * @retrun : {@link By}
     * @Description : 根据定位方式和表达式，返回一个By对象
     */
//    public static By getByWay(FindElementConfig findElementConfig){
////        way = way.toLowerCase();
//        By by = null;
//        switch (findElementConfig.getWay()){
//            case "id" : by = By.id(findElementConfig.getExpression());break;
//            case "name" : by = By.name(findElementConfig.getExpression());break;
//            case "className" : by = By.className(findElementConfig.getExpression());break;
//            case "xpath" : by = By.xpath(findElementConfig.getExpression());break;
//            case "cssSelector" : by = By.cssSelector(findElementConfig.getExpression());break;
//            case "linkText" : by = By.linkText(findElementConfig.getExpression());break;
//            case "tagName" : by = By.tagName(findElementConfig.getExpression());break;
//            case "partialLinkText" : by = By.partialLinkText(findElementConfig.getExpression());break;
//            default:
//                logger.error("定位方式有误...");
//        }
//        return by;
//    }


    /**
     * @Author : haomingjian , 2019/12/6 17:45
     * @param packageNames
     * @retrun : {@link List< Class<?>>}
     * @Description : 获取所有包下的类名，暂时用不到，以后优化框架会使用到
     */
    public static List<Class<?>> getAllClasses(String... packageNames){
        List<Class<?>> allClasses = new ArrayList<>();
        for (String packageName : packageNames) {
            List<Class<?>> classList = getClasses(packageName);
            allClasses.addAll(classList);
        }
        return allClasses;
    }

    /**
     * @Author : haomingjian , 2019/12/6 17:17
     * @param packageName
     * @retrun : {@link List< Class<?>>}
     * @Description : 获取单个包名下的所有类名
     */
    public static List<Class<?>> getClasses(String packageName){
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()){
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)){
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive){
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }
//  以扫描文件的方式
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //传入进程名称processName
    public static boolean findAddKillProcess(String processName) {
        BufferedReader bufferedReader = null;
        try {
            Process proc = Runtime.getRuntime().exec("c:\\windows\\system32\\tasklist -fi " + '"' + "imagename eq " + processName +'"');
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                logger.info("findProcess()获取到的进程信息：" + line);
                if (line.contains(processName)) {
                    killProcess(processName);
                    logger.info("找到并且杀死进程：" + processName);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {}
            }
        }
    }


    //通过进程名称杀死进程
    public static void killProcess(String processName) {
        try {
            if(processName != null) {
                Process pro = Runtime.getRuntime().exec("c:\\windows\\system32\\taskkill /F /im "+ processName);
                BufferedReader brStd = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                BufferedReader brErr = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                long time = System.currentTimeMillis();
                while (true) {
                    if (brStd.ready()) {
                        logger.info("killProcess()进程正常返回:" + processName);
                        break;
                    }
                    if (brErr.ready()) {
                        logger.info("killProcess()进程出错返回:" + processName);
                        break;
                    }
                    if(System.currentTimeMillis() - time > 3000) {
                        logger.info("killProcess()等待超时:" + processName);
                        return;
                    }
                }
            }
        } catch ( IOException e1) {
            e1.printStackTrace();
        }
    }

//  关闭掉端口，
    public static boolean killGridPort() throws IOException {
        BufferedReader bufferedReader = null;
        Process proc = Runtime.getRuntime().exec("cmd /c netstat -ano | findstr \"4444\"");
        bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
        String line = null;
        Set<String> pidList = new HashSet<>();
        while ((line = bufferedReader.readLine()) != null) {
            line = line.substring(line.lastIndexOf(" "), line.length());
            pidList.add(line);
        }
        for (String pid : pidList) {
            proc = Runtime.getRuntime().exec("taskkill -f -pid " + pid);
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
            String killLine = null;
            StringBuffer sb = new StringBuffer();
            while ((killLine = bufferedReader.readLine()) != null) {
                sb.append(killLine + "\n");
            }
            logger.info(sb.toString());
        }
        return true;
    }


    public static String executeCommand(String command) throws IOException{
        BufferedReader bufferedReader = null;
        Process proc = Runtime.getRuntime().exec(command);
        bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
        String line = null;
        StringBuffer resultStr = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            resultStr.append(line);
        }
       return resultStr.toString();
    }

}
