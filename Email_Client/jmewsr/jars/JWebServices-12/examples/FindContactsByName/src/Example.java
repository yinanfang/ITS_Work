import com.independentsoft.exchange.Contact;
import com.independentsoft.exchange.ContactPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(ContactPropertyPath.GIVEN_NAME, "John");

            FindItemResponse response = service.findItem(StandardFolder.CONTACTS, ContactPropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Contact)
                {
                    Contact contact = (Contact) response.getItems().get(i);

                    System.out.println("GivenName = " + contact.getGivenName());
                    System.out.println("Surname = " + contact.getSurname());
                    System.out.println("CompanyName = " + contact.getCompanyName());
                    System.out.println("BusinessAddress = " + contact.getBusinessAddress());
                    System.out.println("BusinessPhone = " + contact.getBusinessPhone());
                    System.out.println("Email1DisplayName = " + contact.getEmail1DisplayName());
                    System.out.println("Email1Address = " + contact.getEmail1Address());
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
