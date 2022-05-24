import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendMail {
    public SendMail() {
    }

    public String Send(String ids, String mailTo) throws MessagingException {
        String to = mailTo;
        final String from = "mail_from";
        final String password = "pass_mail";
        String subject = "[Evos] Оповещение мониторинга онлайн заказа " + new Date();
        String msg =
                "Sistema monitoringa obnaruzhila problemy v rabotosposobnosti komponenta \"Onlajn zakaza\"" + " " + ids + ".\n" +
                        "Obratites', pozhalujsta, v tekhnicheskuyu podderzhku po nomeru +380(093)177-04-52, dlya resheniya vozniknuvshej problemy.\n" +
                        "\n" +
                        "\n" +
                        "Vy poluchili dannoe pis'mo poskol'ku yavlyaetes' klientom programmnogo kompleksa Taxi Navigator s nalichiem platnoj formy onlajn zakaza. \n" +
                        "\n" +
                        "\n" +
                        "S uvazheniem, tekhnicheskaya podderzhka.";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "mail.uklon.com.ua");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "false");
        props.put("mail.smtp.socketFactory.port", "25");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        Transport transport = session.getTransport();
        InternetAddress addressFrom = new InternetAddress(from);

        MimeMessage message = new MimeMessage(session);
        message.setSender(addressFrom);
        message.setSubject(subject);
        message.setContent(msg, "text/plain");
        message.setHeader("from", from);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        transport.connect();
        Transport.send(message);
        System.out.println("Send mail ➤ " + mailTo + "| |" + "IDS >>> " + ids + "| |" + new Date());
        return null;
    }
}
