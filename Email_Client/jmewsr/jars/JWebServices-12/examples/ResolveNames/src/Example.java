import com.independentsoft.exchange.Contact;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Resolution;
import com.independentsoft.exchange.ResolveNamesResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            ResolveNamesResponse response = service.resolveNames("John");

            for (int i = 0; i < response.getResolutions().size(); i++)
            {
                Resolution resolution = response.getResolutions().get(i);

                if (resolution != null && resolution.getMailbox() != null)
                {
                    Mailbox mailbox = resolution.getMailbox();

                    System.out.println("Name = " + mailbox.getName());
                    System.out.println("EmailAddress = " + mailbox.getEmailAddress());
                }

                if (resolution != null && resolution.getContact() != null)
                {
                    Contact contact = resolution.getContact();

                    System.out.println("GivenName = " + contact.getGivenName());
                    System.out.println("EmailAddress = " + contact.getEmail1Address());
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
