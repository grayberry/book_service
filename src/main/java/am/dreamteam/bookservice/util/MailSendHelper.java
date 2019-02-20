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

    public void sendVerificationToken(User user){

        String text = String.format("Hi %s! Welcome to Book Barter!\n Your activation link http://localhost:8080/verification?v=%s",
                user.getUsername(),
                user.getActivationCode());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());

        message.setSubject("Book Barter activation link");
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendTransferNotification(String email, String user, String book){

        String text = String.format("User \"%s\" agreed to change the book \"%s\"",
                user,
                book);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setText(text);
        javaMailSender.send(message);
    }
}
