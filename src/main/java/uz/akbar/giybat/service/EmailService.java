package uz.akbar.giybat.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/** EmailService */
@Service
public class EmailService {

  @Value("${spring.mail.username}")
  private String fromAccount;

  @Autowired JavaMailSender javaMailSender;

  private void sendSimpleEmail(String email, String subject, String body) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(fromAccount);
    msg.setTo(email);
    msg.setSubject(subject);
    msg.setText(body);

    javaMailSender.send(msg);
  }

  public String sendMimeMessage(String email, String subject, String body) {
    try {
      MimeMessage msg = javaMailSender.createMimeMessage();
      msg.setFrom(fromAccount);

      MimeMessageHelper helper = new MimeMessageHelper(msg, true);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText(body, true);
      javaMailSender.send(msg);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    return "Mail was send";
  }

  public void sendRegistrationEmail(String email, Integer profileId) {
    String subject = "Complete registration";

    String body =
        "<!doctype html>\n"
            + "<html lang=\"en\">\n"
            + "  <head>\n"
            + "    <meta charset=\"UTF-8\" />\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n"
            + "    <title>Email Verification</title>\n"
            + "    <style>\n"
            + "      .button-link {\n"
            + "        padding: 10px 30px;\n"
            + "        display: inline-block;\n"
            + "        text-decoration: none;\n"
            + "        color: white;\n"
            + "        background-color: indianred;\n"
            + "      }\n"
            + "\n"
            + "      .button-link:hover {\n"
            + "        background-color: #dd4444;\n"
            + "      }\n"
            + "    </style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <h1>Complete Registration</h1>\n"
            + "    <p>Salom, yaxshimisiz?!</p>\n"
            + "    <p>Please, click the following button to complete registration</p>\n"
            + "    <a\n"
            + "      target=\"_blank\"\n"
            + "      href=\"http://localhost:8080/auth/registration/verification/%d\"\n"
            + "      class=\"button-link\"\n"
            + "    >\n"
            + "      Click Me\n"
            + "    </a>\n"
            + "  </body>\n"
            + "</html>";

    body = String.format(body, profileId);

    // sendSimpleEmail(email, subject, body);
    sendMimeMessage(email, subject, body);
  }
}
