package auto.common.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ReportEmail {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ReportEmail.class);
        // 创建一个Property文件对象
        Properties props = new Properties();

        // 设置邮件服务器的信息，设置smtp主机名称
        props.put("mail.smtp.host", "你的对应邮箱stmp");

        // 设置socket factory 的端口
        props.put("mail.smtp.socketFactory.port", "465");

        // 设置socket factory
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        // 设置需要身份验证
        props.put("mail.smtp.auth", "true");

        // 设置SMTP的端口，阿里邮箱的smtp端口是25
        props.put("mail.smtp.port", "25");

        // 身份验证实现
        Session session = Session.getDefaultInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 第二个参数，开启smtp的授权码
                return new PasswordAuthentication("你的邮箱地址", "授权码");
            }

        });

        try {

            // 创建一个MimeMessage类的实例对象
            Message message = new MimeMessage(session);

            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress("发件人邮箱地址"));

            // 设置收件人邮箱地址
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("收件人邮箱地址"));

            // 设置邮件主题
            message.setSubject("测试发送邮件");

            // 创建一个MimeBodyPart的对象，以便添加内容
            BodyPart messageBodyPartTitle = new MimeBodyPart();

            // 设置邮件正文内容
            messageBodyPartTitle.setText("UI自动化测试报告");

            // 创建另外一个MimeBodyPart对象，以便添加其他内容
            MimeBodyPart messageBodyPartHtml = new MimeBodyPart();

            // 设置邮件中附件文件的路径
            String path = new File("./").getCanonicalPath();
            String reportFilename = path + "\\target\\surefire-reports\\emailable-report.html";

//            String reportFilename = path + "\\test-report\\testReport.html";

            // 创建一个datasource对象，并传递文件
            DataSource source = new FileDataSource(reportFilename);

            // 设置handler
            messageBodyPartHtml.setDataHandler(new DataHandler(source));

            // 加载文件
            messageBodyPartHtml.setFileName("emailable-report.html");

            // 创建一个MimeMultipart类的实例对象
            Multipart multipart = new MimeMultipart();

            // 添加正文1内容
            multipart.addBodyPart(messageBodyPartTitle);

            // 添加正文2内容
            multipart.addBodyPart(messageBodyPartHtml);

            // 设置内容
            message.setContent(multipart);

            // 最终发送邮件
            Transport.send(message);

            logger.info("============= 测试报告邮件已经发送 =============");

        } catch (MessagingException e) {

            throw new RuntimeException(e);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
