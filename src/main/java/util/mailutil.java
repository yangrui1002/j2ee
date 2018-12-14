package util;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class mailutil {

    private static String myEmailSMTPHost = "smtp.qq.com";
    private static String myEmailAccount = "2041437740@qq.com";
    private static String myEmailPassword = "gioyikeqianmeicj";



    /**

     * 邮件单发（自由编辑短信，并发送，适用于私信）

     *

     * @param toEmailAddress 收件箱地址

     * @param emailTitle 邮件主题

     * @param emailContent 邮件内容

     * @throws Exception

     */

    public static void sendEmail(String toEmailAddress, String emailTitle, String emailContent) throws Exception{
        Properties props = new Properties();
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 端口号
        props.put("mail.smtp.port", 465);
        // 设置邮件服务器主机名
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        //设置是否使用ssl安全连接（一般都使用）
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        //创建会话
        Session session = Session.getInstance(props);
        //获取邮件对象
        //发送的消息，基于观察者模式进行设计的
        Message msg = new MimeMessage(session);
        //设置邮件标题
        msg.setSubject(emailTitle);
        //使用StringBuilder，因为StringBuilder加载速度会比String快，而且线程安全性也不错
        StringBuilder builder = new StringBuilder();
        //写入内容
        builder.append("\n" + emailContent);
        //定义要输出日期字符串的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //在内容后加入邮件发送的时间
        builder.append("\n时间：" + sdf.format(new Date()));
        //设置显示的发件时间
        msg.setSentDate(new Date());
        //设置邮件内容
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress(myEmailAccount,"交易网", "UTF-8"));
        Transport transport = session.getTransport();

        transport.connect( myEmailSMTPHost, myEmailAccount, myEmailPassword);
        transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });
        //将该邮件保存到本地
        OutputStream out = new FileOutputStream("MyEmail.eml");
        msg.writeTo(out);
        out.flush();
        out.close();
        transport.close();
    }


}
