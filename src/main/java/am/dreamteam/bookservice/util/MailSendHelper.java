package am.dreamteam.bookservice.util;

import am.dreamteam.bookservice.entities.users.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSendHelper {
    private JavaMailSender javaMailSender;

    public MailSendHelper(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(User user){

        String text = String.format("Hi %s! Welcome to Book Barter!\n Your activation link http://localhost:8080/verification?v=%s",
                user.getUsername(),
                user.getActivationCode());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());

        message.setSubject("Book Barter activation link");
        message.setText(text);
        javaMailSender.send(message);
    }
}
