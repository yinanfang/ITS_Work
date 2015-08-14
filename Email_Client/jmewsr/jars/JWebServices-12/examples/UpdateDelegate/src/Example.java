import com.independentsoft.exchange.DelegateFolderPermissionLevel;
import com.independentsoft.exchange.DelegateUser;
import com.independentsoft.exchange.DelegateUserResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Mailbox mailbox = new Mailbox("username@mydomain.com");

            DelegateUser delegateUser = new DelegateUser("John@mydomain.com");
            delegateUser.setContactsFolderPermissionLevel(DelegateFolderPermissionLevel.AUTHOR);

            DelegateUserResponse response = service.updateDelegate(mailbox, delegateUser);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
