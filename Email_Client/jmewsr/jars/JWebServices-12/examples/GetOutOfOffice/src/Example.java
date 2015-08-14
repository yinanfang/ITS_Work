import com.independentsoft.exchange.OutOfOffice;
import com.independentsoft.exchange.OutOfOfficeResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            OutOfOfficeResponse response = service.getOutOfOffice("username@mydomain.com");

            OutOfOffice oof = response.getOutOfOffice();

            System.out.println("State = " + oof.getState());

            if (oof.getInternalReply() != null)
            {
                System.out.println("InternalReply = " + oof.getInternalReply().getMessage());
            }

            if (oof.getExternalReply() != null)
            {
                System.out.println("ExternalReply = " + oof.getExternalReply().getMessage());
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
