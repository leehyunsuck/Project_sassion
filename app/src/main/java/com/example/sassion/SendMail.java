package com.example.sassion;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    private static String appPassword = "evxu idqq elfv osno";
    private static String appEmail = "leehyunsuck01@gmail.com";
    static int code;
    static int sendmail(String email) {
        Random rand = new Random();

        code = rand.nextInt(900000) + 100000;

        String htmlContent = "<div style='text-align: center; font-family: Cursive;'>"
                + "<h1 style='font-size: 2em;'>Sassion</h1>"
                + "<br><br><br>"
                + "<h1 style='font-size: 2em;'>인증번호</h1><br>"
                + "<div style='background-color: #D3D3D3; display: inline-block; padding: 10px; border-radius: 25px; width: 50%;'>"
                + "<span style='color: #0000FF; font-size: 20px;'>" + code + "</span>"
                + "</div>"
                + "</div>";
        //이메일 전송 코드
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session sess = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(appEmail, appPassword);
            }
        });
        try {
            MimeMessage mime = new MimeMessage(sess);
            mime.setFrom(new InternetAddress(appEmail, "Sassion 공식 메일", "utf-8"));
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mime.setSubject("회원가입 인증번호");
            mime.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(mime);

            return code;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
