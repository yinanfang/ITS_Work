import com.independentsoft.exchange.GetUserConfigurationResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.StandardFolderId;
import com.independentsoft.exchange.UserConfigurationName;
import com.independentsoft.exchange.UserConfigurationProperty;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            UserConfigurationName configName = new UserConfigurationName("CategoryList", new StandardFolderId(StandardFolder.CALENDAR));

            GetUserConfigurationResponse response = service.getUserConfiguration(configName, UserConfigurationProperty.ALL);

            if (response.getUserConfiguration() != null && response.getUserConfiguration().getXmlData() != null)
            {
                //you have to decode base 64 string
                System.out.println(response.getUserConfiguration().getXmlData());
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
