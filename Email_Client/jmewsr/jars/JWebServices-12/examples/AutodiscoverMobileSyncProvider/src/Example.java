import com.independentsoft.exchange.AutodiscoverException;
import com.independentsoft.exchange.AutodiscoverService;
import com.independentsoft.exchange.MobileSyncProvider;
import com.independentsoft.exchange.MobileSyncServer;
import com.independentsoft.exchange.MobileSyncUser;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            AutodiscoverService autodiscoverService = new AutodiscoverService("https://myserver/autodiscover/autodiscover.xml", "username", "password");

            MobileSyncProvider mobileSyncProvider = autodiscoverService.autodiscoverMobileSyncProvider("John@mydomain.com");

            System.out.println("Culture = " + mobileSyncProvider.getCulture());

            if (mobileSyncProvider.getUser() != null)
            {
                MobileSyncUser user = mobileSyncProvider.getUser();

                System.out.println("DisplayName = " + user.getDisplayName());
                System.out.println("EmailAddress = " + user.getEmailAddress());
            }

            if (mobileSyncProvider.getServer() != null)
            {
                MobileSyncServer server = mobileSyncProvider.getServer();

                System.out.println("Server Name = " + server.getName());
                System.out.println("Server Type = " + server.getType());
                System.out.println("Server Url  = " + server.getUrl());
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (AutodiscoverException e)
        {
            e.printStackTrace();
        }
    }
}
