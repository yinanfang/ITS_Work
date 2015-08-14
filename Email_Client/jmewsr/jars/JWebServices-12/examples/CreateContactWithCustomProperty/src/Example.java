import com.independentsoft.exchange.Contact;
import com.independentsoft.exchange.ExtendedProperty;
import com.independentsoft.exchange.FileAsMapping;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.MapiPropertyType;
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

            PropertyName myPropertyName = new PropertyName("myfield1", StandardPropertySet.PUBLIC_STRINGS, MapiPropertyType.STRING);
            ExtendedProperty myProperty = new ExtendedProperty(myPropertyName, "test");

            Contact contact = new Contact();
            contact.setGivenName("John");
            contact.setSurname("Smith");
            contact.setFileAsMapping(FileAsMapping.LAST_SPACE_FIRST);
            contact.setCompanyName("Independentsoft");
            contact.setBusinessPhone("555-666-777");
            contact.setEmail1Address("john@independentsoft.de");
            contact.setEmail1DisplayName("John Smith");
            contact.setEmail1DisplayAs("John Smith");
            contact.setEmail1Type("SMTP");
            contact.getExtendedProperties().add(myProperty);

            ItemId itemId = service.createItem(contact);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
