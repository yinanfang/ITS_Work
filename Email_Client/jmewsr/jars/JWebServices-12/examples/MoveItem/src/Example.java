import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Message message = new Message();
            message.setSubject("Test");
            message.setBody(new Body("Body text."));
            message.getToRecipients().add(new Mailbox("John@mydomain.com"));
            message.getCcRecipients().add(new Mailbox("Mark@mydomain.com"));

            ItemId itemId = service.createItem(message);

            ItemInfoResponse response = service.moveItem(itemId, StandardFolder.INBOX);

            ItemId newItemId = response.getItems().get(0).getItemId();
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}
