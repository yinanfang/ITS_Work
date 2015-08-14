import com.independentsoft.exchange.Response;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.StandardFolderId;
import com.independentsoft.exchange.UserConfiguration;
import com.independentsoft.exchange.UserConfigurationDictionaryEntry;
import com.independentsoft.exchange.UserConfigurationDictionaryKey;
import com.independentsoft.exchange.UserConfigurationDictionaryObjectType;
import com.independentsoft.exchange.UserConfigurationDictionaryValue;
import com.independentsoft.exchange.UserConfigurationName;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            UserConfigurationDictionaryKey key1 = new UserConfigurationDictionaryKey(UserConfigurationDictionaryObjectType.STRING, "key1");
            UserConfigurationDictionaryValue value1 = new UserConfigurationDictionaryValue(UserConfigurationDictionaryObjectType.STRING, "value1");

            UserConfigurationDictionaryKey key2 = new UserConfigurationDictionaryKey(UserConfigurationDictionaryObjectType.STRING, "key2");
            UserConfigurationDictionaryValue value2 = new UserConfigurationDictionaryValue(UserConfigurationDictionaryObjectType.STRING, "value2");

            UserConfigurationDictionaryEntry entry1 = new UserConfigurationDictionaryEntry(key1, value1);
            UserConfigurationDictionaryEntry entry2 = new UserConfigurationDictionaryEntry(key2, value2);

            UserConfigurationName configName = new UserConfigurationName("config1", new StandardFolderId(StandardFolder.DRAFTS));

            UserConfiguration userConfiguration = new UserConfiguration();
            userConfiguration.setName(configName);
            userConfiguration.getEntries().add(entry1);
            userConfiguration.getEntries().add(entry2);

            Response response = service.createUserConfiguration(userConfiguration);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
