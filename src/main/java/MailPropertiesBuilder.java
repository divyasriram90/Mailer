import java.util.Properties;


/**
 * Created by DSR on 5/25/2017.
 */
public class MailPropertiesBuilder {


    private final ConfigurationExtractor extractor;

    public MailPropertiesBuilder(ConfigurationExtractor extractor) {
        this.extractor = extractor;
    }

    public Properties build(){
        Properties mailProps = new Properties();
        mailProps.put("mail.host", extractor.getHost());
        mailProps.put("mail.from", "aris.admin.noreply@vodafone.com");

        if (extractor.getUseTLS()) {
            // When we're here, then the server is configured to use a SSL connection
            mailProps.put("mail.transport.protocol", "smtps");
            // Enable STARTTS mode if configured
            if (extractor.getTLSMode().equalsIgnoreCase("STARTTLS")) {
                mailProps.put("mail.smtp.starttls.enable", "true");
            } else {
                mailProps.put("mail.smtp.starttls.enable", "false");
                mailProps.setProperty("mail.smtp.socketFactory.port", extractor.getSmtpPort());
                mailProps.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                mailProps.setProperty("mail.smtp.socketFactory.fallback", "false");
                mailProps.setProperty("mail.smtp.ssl.enable", "true");
            }
            //smtpS port????   FIXME
            //mailProps.put("mail.smtps.port", configuration.getSmtpPort());
            mailProps.put("mail.smtp.port", extractor.getSmtpPort());
        } else {
            // We use a simple connection
            mailProps.put("mail.transport.protocol", "smtp");
            mailProps.put("mail.smtp.port", extractor.getSmtpPort());
        }
        if (extractor.getEmailSenderAddress() != null && !"".equals(extractor.getEmailSenderAddress())) {
            mailProps.put("mail.user", extractor.getEmailSenderAddress());
        }
        mailProps.put("mail.smtp.sendpartial", "true");
        return mailProps;
    }


}
