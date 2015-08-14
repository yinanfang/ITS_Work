import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceConfigurationResponse;
import com.independentsoft.exchange.ServiceConfigurationType;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Mailbox mailbox = new Mailbox("username@mydomain.com");
            mailbox.setRoutingType("SMTP");

            ServiceConfigurationResponse response1 = service.getServiceConfiguration(mailbox, ServiceConfigurationType.MAIL_TIPS);

            System.out.println("IsMailTipsEnabled=" + response1.getMailTipsConfiguration().isMailTipsEnabled());
            System.out.println("LargeAudienceThreshold=" + response1.getMailTipsConfiguration().getLargeAudienceThreshold());
            System.out.println("MaxMessageSize=" + response1.getMailTipsConfiguration().getMaxMessageSize());
            System.out.println("MaxRecipients=" + response1.getMailTipsConfiguration().getMaxRecipients());
            System.out.println("ShowExternalRecipientCount=" + response1.getMailTipsConfiguration().getShowExternalRecipientCount());

            ServiceConfigurationResponse response2 = service.getServiceConfiguration(ServiceConfigurationType.UNIFIED_MESSAGING_CONFIGURATION);

            System.out.println("IsUnifiedMessagingEnabled=" + response2.getUnifiedMessagingConfiguration().isUnifiedMessagingEnabled());
            System.out.println("IsPlayOnPhoneEnabled=" + response2.getUnifiedMessagingConfiguration().isPlayOnPhoneEnabled());
            System.out.println("PlayOnPhoneDialString=" + response2.getUnifiedMessagingConfiguration().getPlayOnPhoneDialString());
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
