import com.independentsoft.exchange.UnifiedMessagingProperties;
import com.independentsoft.exchange.UnifiedMessagingService;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            UnifiedMessagingService unifiedMessagingService = new UnifiedMessagingService("https://myserver/UnifiedMessaging/service.asmx", "username", "password");

            boolean enabled = unifiedMessagingService.isUnifiedMessagingEnabled();
            UnifiedMessagingProperties properties = unifiedMessagingService.getUnifiedMessagingProperties();

            System.out.println("Enabled = " + enabled);

            if (properties != null)
            {
                System.out.println("OutOfOfficeEnabled = " + properties.isOutOfOfficeEnabled());
                System.out.println("MissedCallNotificationEnabled = " + properties.isMissedCallNotificationEnabled());
                System.out.println("PlayOnPhoneDialString = " + properties.getPlayOnPhoneDialString());
                System.out.println("TelephoneAccessFolderEmail = " + properties.getTelephoneAccessFolderEmail());
                System.out.println("TelephoneAccessNumbers = " + properties.getTelephoneAccessNumbers());
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
