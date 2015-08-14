import java.util.ArrayList;
import java.util.List;

import com.independentsoft.exchange.Contact;
import com.independentsoft.exchange.ContactPropertyPath;
import com.independentsoft.exchange.Exists;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.MapiPropertyType;
import com.independentsoft.exchange.PropertyName;
import com.independentsoft.exchange.PropertyPath;
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

            PropertyName myPropertyName = new PropertyName("myfield1", StandardPropertySet.PUBLIC_STRINGS, MapiPropertyType.STRING);
            Exists restriction = new Exists(myPropertyName);

            List<PropertyPath> propertyPaths = new ArrayList<PropertyPath>();
            propertyPaths.add(ContactPropertyPath.GIVEN_NAME);
            propertyPaths.add(ContactPropertyPath.SURNAME);
            propertyPaths.add(ContactPropertyPath.EMAIL1_DISPLAY_NAME);
            propertyPaths.add(ContactPropertyPath.EMAIL1_ADDRESS);
            propertyPaths.add(myPropertyName);

            FindItemResponse response = service.findItem(StandardFolder.CONTACTS, propertyPaths, restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Contact)
                {
                    Contact contact = (Contact) response.getItems().get(i);

                    System.out.println("GivenName = " + contact.getGivenName());
                    System.out.println("Surname = " + contact.getSurname());
                    System.out.println("CompanyName = " + contact.getCompanyName());
                    System.out.println("Email1DisplayName = " + contact.getEmail1DisplayName());
                    System.out.println("Email1Address = " + contact.getEmail1Address());
                    System.out.println("Myfield1 = " + contact.getExtendedProperty(myPropertyName).getValue());
                    System.out.println("----------------------------------------------------------------");
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
