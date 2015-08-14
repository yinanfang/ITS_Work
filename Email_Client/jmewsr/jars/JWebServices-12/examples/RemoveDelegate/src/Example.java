import com.independentsoft.exchange.DelegateUserResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.UserId;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Mailbox mailbox = new Mailbox("username@mydomain.com");
            UserId user = new UserId("John@mydomain.com");

            DelegateUserResponse response = service.removeDelegate(mailbox, user);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
