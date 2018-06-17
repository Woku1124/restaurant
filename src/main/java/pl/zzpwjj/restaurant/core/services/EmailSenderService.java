package pl.zzpwjj.restaurant.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.zzpwjj.restaurant.core.emailing.EmailSender;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class EmailSenderService {
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailSenderService(final EmailSender emailSender, final TemplateEngine templateEngine)
    {
        this.emailSender = emailSender;
        this.templateEngine =templateEngine;
    }

    public void send(String userEmail, LocalDateTime reservationDate) throws MessagingException {
        Context context = new Context();
        context.setVariable("header", "Witajcie");
        context.setVariable("title", "Stolik zarezerwowany na: "+reservationDate.toString());
        context.setVariable("description", "Nie zapomnij nas odwiedzić");

        String body = templateEngine.process("template", context);
        emailSender.sendEmail(userEmail, "Rezerwacja", body);

    }

    public void send(String to, String title, String content) throws MessagingException {
        Context context = new Context();
        context.setVariable("header", "");
        context.setVariable("title", title);
        context.setVariable("description", content);

        String body = templateEngine.process("template", context);
        emailSender.sendEmail(to, title, body);

    }
}
