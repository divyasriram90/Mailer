import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Date;
import java.util.Properties;

public class TestMail {
    public static void main(String[] args) {
        TestMail testMail = new TestMail();
        testMail.sendMessage(args);
    }

    private void sendMessage(String[] args) {
        final ConfigurationExtractor extractor = new ConfigurationExtractor(args);

        MailPropertiesBuilder mailPropertiesBuilder = new MailPropertiesBuilder(extractor);
        Properties mailProperties = mailPropertiesBuilder.build();

        Authenticator authenticator = null;
        if (extractor.getSmtpPassword() != null && !"".equals(extractor.getSmtpPassword())) {
            mailProperties.put("mail.smtp.auth", "true");
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    System.out.print(extractor.getSmtpUser() + ", " + extractor.getSmtpPassword());
                    return new PasswordAuthentication(extractor.getSmtpUser(), extractor.getSmtpPassword());
                }
            };
        }
        Session session = Session.getInstance(mailProperties, authenticator);
        session.setDebug(true);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress(extractor.getEmailSenderAddress()));
            msg.setContent(new MimeMultipart("alternative"));
            msg.setSubject(MimeUtility.encodeText("Hello World", "UTF-8", "B"));
            addTextBody(msg);
            msg.setRecipients(MimeMessage.RecipientType.TO, extractor.getRecepient());
            msg.setReplyTo(new Address[] { new InternetAddress(extractor.getRecepient()) });
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Transport.send(msg);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void addTextBody(MimeMessage msg) throws MessagingException, IOException {
        MimeBodyPart textBp = new MimeBodyPart();
        textBp.setText("Hello World text", "UTF-8");
        Multipart container = (Multipart) msg.getContent();
        String contentSubtype = new ContentType(container.getContentType()).getSubType();
        if ("mixed".equals(contentSubtype))
        {
            container = (Multipart) container.getBodyPart(0).getContent();
        }
        container.addBodyPart(textBp);
    }
}
