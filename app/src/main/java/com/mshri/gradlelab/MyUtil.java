package com.mshri.gradlelab;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// For mail


public class MyUtil {
    
    private static final Logger log = LogManager.getLogger(MyUtil.class);
    
    public static void printMsg(String msg) {
        System.out.println("Message:\n" + msg);
    }

    public static void testError(String msg){
        try {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10);
            if(randomNum % 2 == 0){
                System.out.println(msg);
            }else{
                throw new IOException("Odd number");
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void sendEmail(String subject, String msg, boolean isHtml, String to, String cc, String bcc, List<MailEmbedObject> embedObjs, String attachment) {
        try {
            // Set mail properties
            Properties mailProps = System.getProperties();
            System.out.println(mailProps);
            mailProps.put("mail.smtp.host", Config.getProperty("smtp_host"));
            mailProps.put("mail.smtp.port", Config.getProperty("smtp_port"));
            mailProps.put("mail.smtp.ssl.enable", true);
            mailProps.put("mail.smtp.auth", true);

            // Create session
            Session mailSession = Session.getInstance(mailProps, new Authenticator(){
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Config.getProperty("smtp_username"), Config.getProperty("smtp_password"));
                }
            });

            // mailSession.setDebug(true);

            // Compose mail
            MimeMessage mail = new MimeMessage(mailSession);

            mail.setFrom(Config.getProperty("smtp_sender"));

            mail.setSubject(subject);

            if(to != null){
                for (String toAddr : to.split(",|;")) {
                    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
                }
            }

            if(cc != null){
                for (String ccAddr : cc.split(",|;")) {
                    mail.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddr));
                }
            }

            if(bcc != null){
                for (String bccAddr : bcc.split(",|;")) {
                    mail.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccAddr));
                }
            }

            MimeMultipart multipartMsg = new MimeMultipart();
            
            MimeBodyPart textPart = new MimeBodyPart();
            if(isHtml){
                textPart.setContent(msg, "text/html");
            }else{
                textPart.setText(msg, "utf-8");
            }
            multipartMsg.addBodyPart(textPart);

            if(embedObjs != null){
                for (MailEmbedObject obj : embedObjs) {
                    MimeBodyPart embedPart = new MimeBodyPart();
                    DataSource ds = new FileDataSource(obj.getPath());
                    embedPart.setDataHandler(new DataHandler(ds));
                    embedPart.setHeader("Content-ID", obj.getCid());
                    multipartMsg.addBodyPart(embedPart);
                }
            }

            if(attachment != null){
                for (String filePath : attachment.split(",|;")) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    File file = new File(filePath);
                    attachmentPart.attachFile(file);
                    multipartMsg.addBodyPart(attachmentPart);
                }
            }

            mail.setContent(multipartMsg);

            Transport.send(mail);
            System.out.println("Mail sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
