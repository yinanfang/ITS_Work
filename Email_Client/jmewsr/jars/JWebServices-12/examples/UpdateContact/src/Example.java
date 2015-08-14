import com.independentsoft.exchange.Contact;
import com.independentsoft.exchange.ContactPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Property;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(ContactPropertyPath.EMAIL1_ADDRESS, "john@mydomain.com");

            FindItemResponse response = service.findItem(StandardFolder.CONTACTS, restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Contact)
                {
                    ItemId itemId = response.getItems().get(i).getItemId();

                    Property businessPhoneProperty = new Property(ContactPropertyPath.BUSINESS_PHONE, "555-666-777");

                    itemId = service.updateItem(itemId, businessPhoneProperty);
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
