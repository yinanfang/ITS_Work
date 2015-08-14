import com.independentsoft.exchange.ExtendedProperty;
import com.independentsoft.exchange.MapiPropertyType;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.ContainmentComparison;
import com.independentsoft.exchange.ContainmentMode;
import com.independentsoft.exchange.Contains;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.PropertyName;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.StandardPropertySet;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Contains restriction = new Contains(MessagePropertyPath.SUBJECT, "test", ContainmentMode.PREFIXED, ContainmentComparison.IGNORE_CASE);

            FindItemResponse response = service.findItem(StandardFolder.INBOX, restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Message)
                {
                    PropertyName myPropertyName = new PropertyName("MyCustomProperty", StandardPropertySet.PUBLIC_STRINGS, MapiPropertyType.STRING);
                    ExtendedProperty myProperty = new ExtendedProperty(myPropertyName, "value2");

                    ItemId newItemId = service.updateItem(response.getItems().get(i).getItemId(), myProperty);
                }
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
