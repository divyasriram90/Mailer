/**
 * Created by DSR on 5/25/2017.
 */
public class ConfigurationExtractor {

    private String[] args;

    public ConfigurationExtractor(String[] args) {
        this.args = args;
    }

    public String getRecepient() {
        return args[4];
    }

    public String getSmtpUser() {
        return args[2];
    }

    public String getEmailSenderAddress() {
        return args[5];
    }

    public String getSmtpPassword() {
        return args[3];
    }

    public String getHost() {
        return args[0];
    }

    public String getSmtpPort() {
        return args[1];
    }

    public String getTLSMode() {
        return args[7];
    }

    public boolean getUseTLS() {
        return Boolean.parseBoolean(args[6]);
    }

}
