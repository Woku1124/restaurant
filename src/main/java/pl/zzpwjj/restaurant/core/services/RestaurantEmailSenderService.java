package pl.zzpwjj.restaurant.core.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.zzpwjj.restaurant.core.emailing.EmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Service
public class RestaurantEmailSenderService implements EmailSender{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String title, String content) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setReplyTo("restaurant.zaawjav@gmail.com");
        helper.setFrom("restaurant.zaawjav@gmail.com");
        helper.setSubject(title);
        helper.setText(content, true);

        javaMailSender.send(mail);

    }
}
