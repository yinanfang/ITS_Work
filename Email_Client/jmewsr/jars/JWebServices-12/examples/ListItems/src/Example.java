import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            FindItemResponse response = service.findItem(StandardFolder.INBOX);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                System.out.println(response.getItems().get(i).getSubject());
                System.out.println(response.getItems().get(i).getItemClass());
                System.out.println(response.getItems().get(i).getItemId());
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
