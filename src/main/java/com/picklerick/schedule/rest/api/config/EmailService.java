package com.picklerick.schedule.rest.api.config;


import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * sending email to the user and properties for sending the email
 * @author Ahsan
 * */

@Service
public class EmailService {
    public boolean sendEmail(String subject, String message, String to){
        boolean rr = false;
        String from = "ahsan.manz99@gmail.com";
        // system properties
        Properties props = System.getProperties();
        // setting properties
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        // get the session object
        Session session = Session.getInstance(props, new Authenticator()
            {
            @Override
            //senders email and app password
             protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("ahsan.manz99@gmail.com", "exxzbffyrnhsmjsy");
            }
        });

        // message for the email
    MimeMessage mm = new MimeMessage(session);
    try{
    mm.setFrom(from);
        mm.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
        //email subject
        mm.setSubject(subject);
        // message in the email
        mm.setText(message);

        Transport.send(mm);
        rr = true;
    } catch (Exception e){
        e.printStackTrace();
    }
        return rr ;

    }
}
