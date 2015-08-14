import com.independentsoft.exchange.ExtendedProperty;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.MapiPropertyType;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.PropertyName;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardPropertySet;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            PropertyName myPropertyName = new PropertyName("MyCustomProperty", StandardPropertySet.PUBLIC_STRINGS, MapiPropertyType.STRING);
            ExtendedProperty myProperty = new ExtendedProperty(myPropertyName, "value1");

            Message message = new Message();
            message.setSubject("Test");
            message.setBody(new Body("Body text."));
            message.getToRecipients().add(new Mailbox("John@mydomain.com"));
            message.getCcRecipients().add(new Mailbox("Mark@mydomain.com"));
            message.getExtendedProperties().add(myProperty);

            ItemId itemId = service.createItem(message);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
