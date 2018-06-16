package pl.zzpwjj.restaurant.core.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.zzpwjj.restaurant.core.emailing.EmailSender;

import javax.mail.MessagingException;

@Controller
public class RestaurantEmailEndpoint {
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public RestaurantEmailEndpoint(EmailSender emailSender,
                                   TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @RequestMapping(value = "/sendEmailTo/{userEmail}", method = RequestMethod.GET)
    public ResponseEntity<String> send(@PathVariable String userEmail) {
        Context context = new Context();
        context.setVariable("header", "Witajcie");
        context.setVariable("title", "Stolik zarezerwowany na jutro");
        context.setVariable("description", "Nie zapomnij nas odwiedzić");

        String body = templateEngine.process("template", context);
        try{
            emailSender.sendEmail(userEmail, "Rezerwacja", body);
        }
        catch (MessagingException e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
